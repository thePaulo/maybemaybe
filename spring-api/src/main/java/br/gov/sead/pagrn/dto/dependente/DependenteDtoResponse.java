package br.gov.sead.pagrn.dto.dependente;

import br.gov.sead.pagrn.domain.type.TipoDependencia;
import br.gov.sead.pagrn.dto.pessoaFisica.PessoaFisicaDtoResponse;
import br.gov.sead.pagrn.dto.servidor.ServidorDtoResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class DependenteDtoResponse {

    private Long id;

    private PessoaFisicaDtoResponse pessoaFisica;

    private ServidorDtoResponse servidor;

    private TipoDependencia tipoDependencia;

    private LocalDate dataVigencia;

    private LocalDate dataExtincao;

}
