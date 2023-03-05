package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.events.Sancao;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.repository.SancaoRepository;
import br.gov.sead.pagrn.repository.VinculoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SancaoServiceTest {

    SancaoRepository sancaoRepository = mock(SancaoRepository.class);

    Vinculo vinculo = mock(Vinculo.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);


    @Test
    void SancaoTest(){

        SancaoService sancaoService = new SancaoService(sancaoRepository, vinculoService);
        Sancao sancao = new Sancao();

        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(sancaoRepository.save(any())).thenReturn(sancao);

        assertDoesNotThrow(()-> sancaoService.sancionar(sancao, 0L));
    }

}