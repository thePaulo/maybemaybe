package br.gov.sead.pagrn.dto.cargoUO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CargoUODtoRequest {

    private Long cargo;

    private Long unidadeOrganizacional;

    private String lotacao;

    private LocalDate dataCriacao;

    private LocalDate dataExtincao;

}
