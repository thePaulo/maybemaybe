package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.Cargo;
import br.gov.sead.pagrn.repository.CargoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;

import org.springframework.stereotype.Service;

@Service
public class CargoService extends AbstractService<Cargo, CargoRepository> {
    public CargoService(CargoRepository repository) { super(repository);}

}
