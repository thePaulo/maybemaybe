package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.events.Reintegracao;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.repository.ReintegracaoRepository;
import br.gov.sead.pagrn.repository.VinculoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReintegracaoServiceTest {

    ReintegracaoRepository reintegracaoRepository = mock(ReintegracaoRepository.class);

    Vinculo vinculo = mock(Vinculo.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);


    @Test
    void ReintegracaoTest(){

        ReintegracaoService reintegracaoService = new ReintegracaoService(reintegracaoRepository, vinculoService);
        Reintegracao reintegracao = new Reintegracao();

        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(reintegracaoRepository.save(any())).thenReturn(reintegracao);

        assertDoesNotThrow(()-> reintegracaoService.reintegrar(reintegracao, 0L));
    }

}