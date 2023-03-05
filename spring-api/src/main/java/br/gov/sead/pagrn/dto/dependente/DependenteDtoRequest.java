package br.gov.sead.pagrn.dto.dependente;

import br.gov.sead.pagrn.domain.type.TipoDependencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DependenteDtoRequest {

    private Long pessoaFisica;

    private Long servidor;

    private TipoDependencia tipoDependencia;

    private LocalDate dataVigencia;

    private LocalDate dataExtincao;
}
