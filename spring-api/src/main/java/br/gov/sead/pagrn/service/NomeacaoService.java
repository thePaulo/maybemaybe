package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.NomeacaoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import br.gov.sead.pagrn.service.generic.ValidateVinculo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class NomeacaoService extends AbstractService<Nomeacao, NomeacaoRepository> {
    private final VinculoService vinculoService;
    private final ServidorService servidorService;
    private final UnidadeOrganizacionalService unidadeOrganizacionalService;
    private final SetorService setorService;
    private final NivelCargoService nivelCargoService;
    private final FuncaoService funcaoService;

    public NomeacaoService(NomeacaoRepository repository, VinculoService vinculoService, ServidorService servidorService,
                           UnidadeOrganizacionalService unidadeOrganizacionalService, SetorService setorService,
                           NivelCargoService nivelCargoService, FuncaoService funcaoService) {
        super(repository);
        this.vinculoService = vinculoService;
        this.servidorService = servidorService;
        this.unidadeOrganizacionalService = unidadeOrganizacionalService;
        this.setorService = setorService;
        this.nivelCargoService = nivelCargoService;
        this.funcaoService = funcaoService;
    }

    @Transactional
    public Nomeacao nomear(Nomeacao nomeacao, Long idNivelCargo, Long idServidor,
                           Long idUnidadeOrganizacional, Long idSetor, Long idFuncao) {

        buscarEntidades(nomeacao, idNivelCargo, idServidor, idUnidadeOrganizacional, idSetor, idFuncao);

        vinculoService.criarVinculo(nomeacao);
        return super.create(nomeacao);
    }

    private void buscarEntidades(Nomeacao nomeacao, Long idNivelCargo, Long idServidor, Long idUnidadeOrganizacional, Long idSetor, Long idFuncao) {
        Servidor servidor = servidorService.findByIdOrThrowException(idServidor, ApiMessages.SERVIDOR_NAO_ENCONTRADO);
        UnidadeOrganizacional unidadeOrganizacional = unidadeOrganizacionalService
                .findByIdOrThrowException(idUnidadeOrganizacional, ApiMessages.UNIDADE_ORGANIZACIONAL_NAO_ENCONTRADO);
        Setor setor = setorService.findByIdOrThrowException(idSetor, ApiMessages.SETOR_NAO_ENCONTRADO);

        NivelCargo nivelCargo = null;
        if (nonNull(idNivelCargo)) {
            nivelCargo = nivelCargoService.findByIdOrThrowException(idNivelCargo, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO);
        }
        Funcao funcao = null;
        if (nonNull(idFuncao)) {
            funcao = funcaoService.findByIdOrThrowException(idFuncao, ApiMessages.FUNCAO_NAO_ENCONTRADA);
        }
        if (isNull(idNivelCargo) && isNull(idFuncao)) {
            throw new RuntimeException(ApiMessages.VERIFICACAO_NIVEL_CARGO_E_FUNCAO);
        }
        nomeacao.preencher(servidor, unidadeOrganizacional, setor, nivelCargo, funcao);
    }

    public ValidateVinculo validateRequest(String cpf, Long idVinculo){
        // verifica se o vinculo existe
        Optional<Vinculo> v = vinculoService.findById(idVinculo);
        if (v.isEmpty()) {
            return new ValidateVinculo(false, null);
        }

        // verifica se o vinculo e ativo
        if (v.get().getSituacao() != SituacaoVinculo.ATIVO) {
            return new ValidateVinculo(false, null);
        }

        // vefifica se o vinculo pertence ao cpf
        List<Long> vinculosId = vinculoService.findByCPFdoServidor(cpf);
        if (!vinculosId.contains(idVinculo)) {
            return new ValidateVinculo(false, null);
        }

        return new ValidateVinculo(true, v.get());
    }
}