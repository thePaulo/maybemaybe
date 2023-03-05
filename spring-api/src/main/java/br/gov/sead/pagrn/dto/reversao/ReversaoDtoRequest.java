package br.gov.sead.pagrn.dto.reversao;

import br.gov.sead.pagrn.errorhandling.ApiMessages;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class ReversaoDtoRequest {

    private String dadosLaudo;

    private String processoAdministrativo;

    //evento

    private LocalDate dataVigencia;

    private String descricao;

    // FK
    private Long vinculo;
}
