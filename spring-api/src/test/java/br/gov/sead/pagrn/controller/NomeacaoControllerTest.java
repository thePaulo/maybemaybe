package br.gov.sead.pagrn.controller;

import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.service.NomeacaoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(NomeacaoController.class)
class NomeacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NomeacaoService service;

    @Test
    void nomeacaoTest() throws Exception {

        Page<Nomeacao> dtoResponses = Page.empty();

        when(service.find(any(), any())).thenReturn(dtoResponses);

        mockMvc.perform(get("/nomeacoes")).andExpect(MockMvcResultMatchers.status().isOk());
    }

}