package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.domain.events.Promocao;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.*;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PromocaoServiceTest {

    PromocaoRepository promocaoRepository = mock(PromocaoRepository.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);

    Vinculo vinculo = mock(Vinculo.class);

    NivelCargoService nivelCargoService = mock(NivelCargoService.class);

    @Test
    void promoverTest(){
        PromocaoService promocaoService = new PromocaoService(promocaoRepository, vinculoService, nivelCargoService);
        Promocao promocao = new Promocao();
        NivelCargo nivelCargo = new NivelCargo();

        when(nivelCargoService.findByIdOrThrowException(0L, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO)).thenReturn(nivelCargo);
        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(promocaoRepository.save(any())).thenReturn(promocao);

        assertDoesNotThrow(()-> promocaoService.promover(promocao, 0L, 0L));
    }

    @Test
    void promoverTestNivelCargoNonExistent(){

    PromocaoService promocaoService = new PromocaoService(promocaoRepository, vinculoService, nivelCargoService);
    Promocao promocao = new Promocao();

    assertThrows(EntityNotFoundException.class, () -> promocaoService.promover(promocao, 0L, 0L));
    }

    @Test
    void promoverTestIdVinculoNonExistent(){
        PromocaoService promocaoService = new PromocaoService(promocaoRepository, vinculoService, nivelCargoService);
        Promocao promocao = new Promocao();
        NivelCargo nivelCargo = new NivelCargo();

        when(nivelCargoService.findByIdOrThrowException(0L, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO)).thenReturn(nivelCargo);

        assertThrows(EntityNotFoundException.class, () -> promocaoService.promover(promocao, 0L, 0L));
    }
}