package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.events.Demissao;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.repository.DemissaoRepository;
import br.gov.sead.pagrn.repository.VinculoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DemissaoServiceTest {
    DemissaoRepository demissaoRepository = mock(DemissaoRepository.class);

    Vinculo vinculo = mock(Vinculo.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);

    @Test
    void demitirTest(){
        DemissaoService demissaoService = new DemissaoService(demissaoRepository, vinculoService);
        Demissao demissao = new Demissao();

        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(demissaoRepository.save(any())).thenReturn(demissao);

        assertDoesNotThrow(()-> demissaoService.demitir(demissao, 0L));
    }

}