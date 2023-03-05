package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.domain.events.*;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.VinculoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static br.gov.sead.pagrn.domain.vinculos.Vinculo.fabricarVinculo;

@Service
public class VinculoService extends AbstractService<Vinculo, VinculoRepository> {

    public VinculoService(VinculoRepository repository) {
        super(repository);
    }


    public Page<Vinculo> findByCPFdoServidor(String cpf, Pageable pageable) {
        return repository.findByCPFdoServidor(cpf, pageable);
    }

    public List<Long> findByCPFdoServidor(String cpf) {
        return repository.findByCPFdoServidor(cpf);
    }

    public Page<Vinculo> findByNomeDoServidor(String nome, Pageable pageable) {
        return repository.findByNomeDoServidor(nome, pageable);
    }

    public Page<Vinculo> findByMatriculaDoServidor(String matricula, Pageable pageable) {
        return repository.findByMatriculaDoServidor(matricula, pageable);
    }

    @Transactional
    public Vinculo criarVinculo(Nomeacao nomeacao) {
        TipoVinculo tipoVinculo = nomeacao.getTipoVinculo();
        Vinculo vinculo = fabricarVinculo(tipoVinculo, nomeacao);
        vinculo.registrarEvento(nomeacao);
        if (vinculo.validar()) {
            super.create(vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo atribuirFuncao(Designacao designacao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        designacao.atualizarVinculo(vinculo);
        vinculo.registrarEvento(designacao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo destituirFuncaoOuCargo(Exoneracao exoneracao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        exoneracao.atualizarVinculo(vinculo);
        vinculo.mudarSituacao(SituacaoVinculo.EXONERADO);
        vinculo.registrarEvento(exoneracao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo colocarEmDisponibilidade(Sancao sancao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.EM_DISPOSICAO);
        vinculo.registrarEvento(sancao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo aproveitarServidorEmOutroCargo(Aproveitamento aproveitamento, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        aproveitamento.atualizarVinculo(vinculo);
        vinculo.mudarSituacao(SituacaoVinculo.ATIVO);
        vinculo.registrarEvento(aproveitamento);
        if (vinculo.validar()) {
            super.create(vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo registrarFalecimento(Falecimento falecimento, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.INATIVO);
        vinculo.registrarEvento(falecimento);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo progredirCargo(Promocao promocao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
//        promocao.atualizarVinculo(vinculo);
        vinculo.registrarEvento(promocao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo registrarAposentadoria(Aposentadoria aposentadoria, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.APOSENTADO);
        vinculo.registrarEvento(aposentadoria);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo registrarReversao(Reversao reversao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.ATIVO);
        vinculo.registrarEvento(reversao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo registrarDemissao(Demissao demissao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.DEMITIDO);
        vinculo.registrarEvento(demissao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo registrarReintegracao(Reintegracao reintegracao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.ATIVO);
        vinculo.registrarEvento(reintegracao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo encerrarContratoTemporario(Encerramento encerramento, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.ENCERRADO);
        vinculo.registrarEvento(encerramento);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo registrarReconducao(Reconducao reconducao, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.ATIVO);
        vinculo.registrarEvento(reconducao);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Vinculo registrarVacancia(Vacancia vacancia, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.SUSPENSO);
        vinculo.registrarEvento(vacancia);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    @Transactional
    public Vinculo registrarAfastamento(Afastamento afastamento, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.AFASTADO);
        vinculo.registrarEvento(afastamento);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }


    @Transactional
    public Vinculo registrarRetorno(Retorno retorno, Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        vinculo.mudarSituacao(SituacaoVinculo.ATIVO);
        vinculo.registrarEvento(retorno);
        if (vinculo.validar()) {
            super.update(idVinculo, vinculo);
        }
        return vinculo;
    }

    public Set<String> buscarEventosPermitidos(Long idVinculo) {
        Vinculo vinculo = this.findByIdOrThrowException(idVinculo, ApiMessages.VINCULO_NAO_ENCONTRADO);
        return vinculo.eventosPermitidos();
    }
}




