package br.gov.sead.pagrn.dto.promocao;

import br.gov.sead.pagrn.domain.type.TipoEvento;
import br.gov.sead.pagrn.domain.type.TipoSancao;
import br.gov.sead.pagrn.dto.nivelCargo.NivelCargoDtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PromocaoDtoResponse {

    private Long id;

    private NivelCargoDtoResponse nivelCargo;

    private String processoAdministrativo;

    private LocalDate dataPromocao;

    //evento

    private LocalDate dataVigencia;

    private String descricao;

}

