package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.type.Escolaridade;
import br.gov.sead.pagrn.repository.CargoRepository;
import br.gov.sead.pagrn.repository.NivelCargoRepository;
import br.gov.sead.pagrn.repository.PessoaJuridicaRepository;
import br.gov.sead.pagrn.repository.UnidadeOrganizacionalRepository;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NivelCargoServiceTest {

    PessoaJuridicaRepository pessoaJuridicaRepository = mock(PessoaJuridicaRepository.class);

    UnidadeOrganizacionalRepository unidadeOrganizacionalRepository = mock(UnidadeOrganizacionalRepository.class);

    CargoRepository cargoRepository = mock(CargoRepository.class);

    NivelCargoRepository nivelCargoRepository = mock(NivelCargoRepository.class);


    @Test
    void insertTestCargoNonexistent() {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);

        Endereco endereco = new Endereco();

        Set<Telefone> telefones = new HashSet<>();

        when(pessoaJuridicaService.insert(pessoaJuridica, endereco, telefones)).thenReturn(pessoaJuridica);

        PessoaJuridica pessoaJuridicaSalva = pessoaJuridicaService.insert(pessoaJuridica, endereco, telefones);

        UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();

        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);

        when(pessoaJuridicaRepository.findById(pessoaJuridica.getId())).thenReturn(Optional.of(pessoaJuridica));

        when(unidadeOrganizacionalService.insert(unidadeOrganizacional, pessoaJuridicaSalva.getId())).thenReturn(unidadeOrganizacional);

        UnidadeOrganizacional unidadeOrganizacionalSalva = unidadeOrganizacionalService.insert(unidadeOrganizacional, pessoaJuridicaSalva.getId());

        Cargo cargo = new Cargo();

        CargoService cargoService = new CargoService(cargoRepository);

        when(cargoService.create(cargo)).thenReturn(cargo);

        Cargo cargoSalvo = cargoService.create(cargo);

        CargoUO cargoUO = new CargoUO();

        NivelCargo nivelCargo = new NivelCargo();

        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);

        when(unidadeOrganizacionalRepository.findById(unidadeOrganizacional.getId())).thenReturn(Optional.of(unidadeOrganizacional));

        assertThrows(EntityNotFoundException.class, () -> nivelCargoService.insert(nivelCargo, cargoUO, cargoSalvo.getId(), unidadeOrganizacionalSalva.getId()));
    }

    @Test
    void insertTestUONonexistent() {
        PessoaJuridica pessoaJuridica = new PessoaJuridica();

        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);

        Endereco endereco = new Endereco();

        Set<Telefone> telefones = new HashSet<>();

        when(pessoaJuridicaService.insert(pessoaJuridica, endereco, telefones)).thenReturn(pessoaJuridica);

        UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();

        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);

        Cargo cargo = new Cargo();
        cargo.setDenominacao("string");
        cargo.setEscolaridade(Escolaridade.SUPERIOR);
        cargo.setDenominacao("string");
        cargo.setDataCriacao(LocalDate.EPOCH);
        cargo.setDataExtincao(LocalDate.EPOCH);
        cargo.setId(0L);

        CargoService cargoService = new CargoService(cargoRepository);

        when(cargoService.create(cargo)).thenReturn(cargo);

        Cargo cargoSalvo = cargoService.create(cargo);

        CargoUO cargoUO = new CargoUO();

        NivelCargo nivelCargo = new NivelCargo();

        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);

        when(cargoRepository.findById(cargo.getId())).thenReturn(Optional.of(cargo));

        assertThrows(EntityNotFoundException.class, () -> nivelCargoService.insert(nivelCargo, cargoUO, cargoSalvo.getId(), unidadeOrganizacional.getId()));
    }

}