package br.gov.sead.pagrn.dto.nivelCargo;

import br.gov.sead.pagrn.dto.cargoUO.CargoUODtoRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class NivelCargoDtoRequest {

    private CargoUODtoRequest cargoUO;

    private String sigla;

    private Double remuneracaoBase;

    private LocalDate dataCriacao;

    private LocalDate dataExtincao;

}
