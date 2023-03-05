package br.gov.sead.pagrn.service;


import br.gov.sead.pagrn.domain.concrets.Cargo;
import br.gov.sead.pagrn.domain.concrets.CargoUO;
import br.gov.sead.pagrn.domain.concrets.NivelCargo;
import br.gov.sead.pagrn.domain.concrets.UnidadeOrganizacional;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.NivelCargoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class NivelCargoService extends AbstractService<NivelCargo, NivelCargoRepository> {
    private CargoService cargoService;
    private UnidadeOrganizacionalService unidadeOrganizacionalService;

    public NivelCargoService(NivelCargoRepository repository, CargoService cargoService,
                             UnidadeOrganizacionalService unidadeOrganizacionalService){
        super(repository);
        this.cargoService = cargoService;
        this.unidadeOrganizacionalService = unidadeOrganizacionalService;
    }

    @Transactional
    public NivelCargo insert(NivelCargo nivelCargo, CargoUO cargoUO, Long idCargo, Long idUnidadeOrganizacional) {
        Cargo cargo = cargoService.findByIdOrThrowException(idCargo, ApiMessages.CARGO_NAO_ENCONTRADO);
        UnidadeOrganizacional unidadeOrganizacional = unidadeOrganizacionalService.findByIdOrThrowException(idUnidadeOrganizacional, ApiMessages.UNIDADE_ORGANIZACIONAL_NAO_ENCONTRADO);

        cargoUO.preencher(cargo, unidadeOrganizacional);
        nivelCargo.preencher(cargoUO);

        return super.create(nivelCargo);
    }
}
