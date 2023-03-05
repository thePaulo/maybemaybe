package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.repository.*;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NomeacaoServiceTest {

    NomeacaoRepository nomeacaoRepository = mock(NomeacaoRepository.class);

    ServidorRepository servidorRepository = mock(ServidorRepository.class);

    PessoaFisicaRepository pessoaFisicaRepository = mock(PessoaFisicaRepository.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    UnidadeOrganizacionalRepository unidadeOrganizacionalRepository = mock(UnidadeOrganizacionalRepository.class);

    PessoaJuridicaRepository pessoaJuridicaRepository = mock(PessoaJuridicaRepository.class);

    SetorRepository setorRepository = mock(SetorRepository.class);

    CargoRepository cargoRepository = mock(CargoRepository.class);

    NivelCargoRepository nivelCargoRepository = mock(NivelCargoRepository.class);

    FuncaoRepository funcaoRepository = mock(FuncaoRepository.class);

    @Test
    void nomearTestServidorNonExistent() {
        Nomeacao nomeacao = new Nomeacao();

        VinculoService vinculoService = new VinculoService(vinculoRepository);
        PessoaFisicaService pessoaFisicaService = new PessoaFisicaService(pessoaFisicaRepository);
        ServidorService servidorService = new ServidorService(servidorRepository, pessoaFisicaService);
        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);
        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);
        SetorService setorService = new SetorService(setorRepository, unidadeOrganizacionalService);
        CargoService cargoService = new CargoService(cargoRepository);
        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);
        FuncaoService funcaoService = new FuncaoService(funcaoRepository);

        NomeacaoService nomeacaoService = new NomeacaoService(nomeacaoRepository, vinculoService, servidorService, unidadeOrganizacionalService, setorService, nivelCargoService, funcaoService);

        UnidadeOrganizacional UO = new UnidadeOrganizacional();

        when(unidadeOrganizacionalRepository.findById(0L)).thenReturn(Optional.of(UO));

        Setor setor = new Setor();

        when(setorRepository.findById(0L)).thenReturn(Optional.of(setor));

        NivelCargo nivelCargo = new NivelCargo();

        when(nivelCargoRepository.findById(0L)).thenReturn(Optional.of(nivelCargo));

        Funcao funcao = new Funcao();

        when(funcaoRepository.findById(0L)).thenReturn(Optional.of(funcao));

        assertThrows(EntityNotFoundException.class, () ->  nomeacaoService.nomear(nomeacao, 0L, 0L, 0L, 0L, 0L));

    }

    @Test
    void nomearTestUONonExistent() {
        Nomeacao nomeacao = new Nomeacao();

        VinculoService vinculoService = new VinculoService(vinculoRepository);
        PessoaFisicaService pessoaFisicaService = new PessoaFisicaService(pessoaFisicaRepository);
        ServidorService servidorService = new ServidorService(servidorRepository, pessoaFisicaService);
        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);
        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);
        SetorService setorService = new SetorService(setorRepository, unidadeOrganizacionalService);
        CargoService cargoService = new CargoService(cargoRepository);
        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);
        FuncaoService funcaoService = new FuncaoService(funcaoRepository);

        NomeacaoService nomeacaoService = new NomeacaoService(nomeacaoRepository, vinculoService, servidorService, unidadeOrganizacionalService, setorService, nivelCargoService, funcaoService);

        Servidor servidor = new Servidor();

        when(servidorRepository.findById(0L)).thenReturn(Optional.of(servidor));

        Setor setor = new Setor();

        when(setorRepository.findById(0L)).thenReturn(Optional.of(setor));

        NivelCargo nivelCargo = new NivelCargo();

        when(nivelCargoRepository.findById(0L)).thenReturn(Optional.of(nivelCargo));

        Funcao funcao = new Funcao();

        when(funcaoRepository.findById(0L)).thenReturn(Optional.of(funcao));

        assertThrows(EntityNotFoundException.class, () ->  nomeacaoService.nomear(nomeacao, 0L, 0L, 0L, 0L, 0L));

    }

    @Test
    void nomearTestSetorNonExistent() {
        Nomeacao nomeacao = new Nomeacao();

        VinculoService vinculoService = new VinculoService(vinculoRepository);
        PessoaFisicaService pessoaFisicaService = new PessoaFisicaService(pessoaFisicaRepository);
        ServidorService servidorService = new ServidorService(servidorRepository, pessoaFisicaService);
        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);
        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);
        SetorService setorService = new SetorService(setorRepository, unidadeOrganizacionalService);
        CargoService cargoService = new CargoService(cargoRepository);
        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);
        FuncaoService funcaoService = new FuncaoService(funcaoRepository);

        NomeacaoService nomeacaoService = new NomeacaoService(nomeacaoRepository, vinculoService, servidorService, unidadeOrganizacionalService, setorService, nivelCargoService, funcaoService);

        Servidor servidor = new Servidor();

        when(servidorRepository.findById(0L)).thenReturn(Optional.of(servidor));

        UnidadeOrganizacional UO = new UnidadeOrganizacional();

        when(unidadeOrganizacionalRepository.findById(0L)).thenReturn(Optional.of(UO));

        NivelCargo nivelCargo = new NivelCargo();

        when(nivelCargoRepository.findById(0L)).thenReturn(Optional.of(nivelCargo));

        Funcao funcao = new Funcao();

        when(funcaoRepository.findById(0L)).thenReturn(Optional.of(funcao));

        assertThrows(EntityNotFoundException.class, () ->  nomeacaoService.nomear(nomeacao, 0L, 0L, 0L, 0L, 0L));

    }

    @Test
    void nomearTestFuncaoAndNivelCargoNotBeBothNull() {
        Nomeacao nomeacao = new Nomeacao();

        VinculoService vinculoService = new VinculoService(vinculoRepository);
        PessoaFisicaService pessoaFisicaService = new PessoaFisicaService(pessoaFisicaRepository);
        ServidorService servidorService = new ServidorService(servidorRepository, pessoaFisicaService);
        PessoaJuridicaService pessoaJuridicaService = new PessoaJuridicaService(pessoaJuridicaRepository);
        UnidadeOrganizacionalService unidadeOrganizacionalService = new UnidadeOrganizacionalService(unidadeOrganizacionalRepository, pessoaJuridicaService);
        SetorService setorService = new SetorService(setorRepository, unidadeOrganizacionalService);
        CargoService cargoService = new CargoService(cargoRepository);
        NivelCargoService nivelCargoService = new NivelCargoService(nivelCargoRepository, cargoService, unidadeOrganizacionalService);
        FuncaoService funcaoService = new FuncaoService(funcaoRepository);

        NomeacaoService nomeacaoService = new NomeacaoService(nomeacaoRepository, vinculoService, servidorService, unidadeOrganizacionalService, setorService, nivelCargoService, funcaoService);

        Servidor servidor = new Servidor();

        when(servidorRepository.findById(0L)).thenReturn(Optional.of(servidor));

        Setor setor = new Setor();

        when(setorRepository.findById(0L)).thenReturn(Optional.of(setor));

        UnidadeOrganizacional UO = new UnidadeOrganizacional();

        when(unidadeOrganizacionalRepository.findById(0L)).thenReturn(Optional.of(UO));

        assertThrows(RuntimeException.class, () ->  nomeacaoService.nomear(nomeacao, null, 0L, 0L, 0L, null));

    }

}