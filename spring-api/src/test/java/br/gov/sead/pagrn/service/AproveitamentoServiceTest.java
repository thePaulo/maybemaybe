package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.NivelCargo;
import br.gov.sead.pagrn.domain.concrets.Setor;
import br.gov.sead.pagrn.domain.events.Aproveitamento;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import br.gov.sead.pagrn.repository.AproveitamentoRepository;
import br.gov.sead.pagrn.repository.VinculoRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AproveitamentoServiceTest {

    AproveitamentoRepository aproveitamentoRepository = mock(AproveitamentoRepository.class);

    Vinculo vinculo = mock(Vinculo.class);

    VinculoRepository vinculoRepository = mock(VinculoRepository.class);

    VinculoService vinculoService = new VinculoService(vinculoRepository);

    ServidorService servidorService = mock(ServidorService.class);

    UnidadeOrganizacionalService unidadeOrganizacionalService = mock(UnidadeOrganizacionalService.class);

    SetorService setorService = mock(SetorService.class);
    NivelCargoService nivelCargoService = mock(NivelCargoService.class);

    FuncaoService funcaoService = mock(FuncaoService.class);


    @Test
    void aproveitarTest(){
        AproveitamentoService aproveitamentoService = new AproveitamentoService(aproveitamentoRepository, vinculoService, servidorService,
                unidadeOrganizacionalService, setorService, nivelCargoService, funcaoService);

        Aproveitamento aproveitamento = new Aproveitamento();
        NivelCargo nivelCargo = new NivelCargo();
        Setor  setor = new Setor();

        when(nivelCargoService.findByIdOrThrowException(0L, ApiMessages.NIVEL_CARGO_NAO_ENCONTRADO)).thenReturn(nivelCargo);
        when(setorService.findByIdOrThrowException(0L, ApiMessages.SETOR_NAO_ENCONTRADO)).thenReturn(setor);
        when(vinculoRepository.findById(any())).thenReturn(Optional.of(vinculo));
        when(aproveitamentoRepository.save(any())).thenReturn(aproveitamento);

        assertDoesNotThrow(()-> aproveitamentoService.aproveitar(aproveitamento, 0L, 0L, 0L));
    }
}
