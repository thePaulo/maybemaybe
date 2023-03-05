package br.gov.sead.pagrn.dto.cargoUO;

import br.gov.sead.pagrn.dto.cargo.CargoDtoResponse;
import br.gov.sead.pagrn.dto.unidadeOrganizacional.UnidadeOrganizacionalDtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class CargoUODtoResponse {

    private Long id;

    private CargoDtoResponse cargo;

    private UnidadeOrganizacionalDtoResponse unidadeOrganizacional;

    private String lotacao;

    private LocalDate dataCriacao;

    private LocalDate dataExtincao;

}
