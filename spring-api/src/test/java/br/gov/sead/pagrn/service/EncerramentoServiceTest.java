package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.events.Encerramento;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.repository.EncerramentoRepository;
import br.gov.sead.pagrn.repository.VinculoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EncerramentoServiceTest {
    EncerramentoRepository encerramentoRepository = mock(EncerramentoRepository.class);

    Vinculo vinculo = mock(Vinculo.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);


    @Test
    void encerramentoTest(){

        EncerramentoService encerramentoService = new EncerramentoService(encerramentoRepository, vinculoService);
        Encerramento encerramento = new Encerramento();

        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(encerramentoRepository.save(any())).thenReturn(encerramento);

        assertDoesNotThrow(()-> encerramentoService.encerrarContrato(encerramento, 0L));
    }

}