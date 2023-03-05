package br.gov.sead.pagrn.dto.designacao;


import br.gov.sead.pagrn.domain.type.TipoEvento;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DesignacaoDtoRequest {

    private Long funcao;

    private String processoAdministrativo;

    private Long vinculo;

    //evento

    private LocalDate dataVigencia;

    private String descricao;
}
