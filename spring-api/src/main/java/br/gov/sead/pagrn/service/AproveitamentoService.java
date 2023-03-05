package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Aproveitamento;
import br.gov.sead.pagrn.domain.events.Designacao;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.AproveitamentoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AproveitamentoService extends AbstractService<Aproveitamento, AproveitamentoRepository> {

    private final VinculoService vinculoService;
    private final ServidorService servidorService;
    private final UnidadeOrganizacionalService unidadeOrganizacionalService;
    private final SetorService setorService;
    private final NivelCargoService nivelCargoService;
    private final FuncaoService funcaoService;

    public AproveitamentoService(AproveitamentoRepository repository, VinculoService vinculoService, ServidorService servidorService,
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
    public Aproveitamento aproveitar(Aproveitamento aproveitamento, Long idVinculo, Long idNivelCargo, Long idSetor) {
        NivelCargo nivelCargo = nivelCargoService.findByIdOrThrowException(idNivelCargo, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO);

        Setor setor = setorService.findByIdOrThrowException(idSetor, ApiMessages.SERVIDOR_NAO_ENCONTRADO);

        aproveitamento.preencher(nivelCargo, setor);
        Aproveitamento aproveitamentoSaved = super.create(aproveitamento);

        vinculoService.aproveitarServidorEmOutroCargo(aproveitamentoSaved, idVinculo);
        return aproveitamentoSaved;
    }

}
