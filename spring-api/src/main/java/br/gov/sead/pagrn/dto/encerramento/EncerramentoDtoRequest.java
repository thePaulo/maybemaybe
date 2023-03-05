package br.gov.sead.pagrn.dto.encerramento;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EncerramentoDtoRequest {

    private Long vinculo;

    //evento
    private LocalDate dataVigencia;

    private String descricao;
}
