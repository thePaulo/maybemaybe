package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.NivelCargo;
import br.gov.sead.pagrn.domain.events.Promocao;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.PromocaoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PromocaoService extends AbstractService<Promocao, PromocaoRepository> {

    private final VinculoService vinculoService;

    private final NivelCargoService nivelCargoService;

    public PromocaoService(PromocaoRepository repository,VinculoService vinculoService, NivelCargoService nivelCargoService) {
        super(repository);
        this.vinculoService = vinculoService;
        this.nivelCargoService = nivelCargoService;

    }

    @Transactional
    public Promocao promover(Promocao promocao, Long idNivelCargo, Long idVinculo){
        NivelCargo nivelCargo = nivelCargoService.findByIdOrThrowException(idNivelCargo, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO);

        promocao.preencher(nivelCargo);
        Promocao promocaoSaved = super.create(promocao);

        vinculoService.progredirCargo(promocaoSaved, idVinculo);
        return promocaoSaved;
    }

}
