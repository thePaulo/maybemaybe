package br.gov.sead.pagrn.dto.nivelCargo;

import br.gov.sead.pagrn.dto.cargoUO.CargoUODtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class NivelCargoDtoResponse {

    private Long id;

    private CargoUODtoResponse cargoUO;

    private String sigla;

    private Double remuneracaoBase;

    private LocalDate dataCriacao;

    private LocalDate dataExtincao;

}
