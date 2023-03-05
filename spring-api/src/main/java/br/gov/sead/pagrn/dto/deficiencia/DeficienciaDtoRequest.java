package br.gov.sead.pagrn.dto.deficiencia;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeficienciaDtoRequest {
    private Boolean fisica = false;

    private Boolean visual = false;

    private Boolean auditiva = false;

    private Boolean mental = false;

    private Boolean intelectual = false;

    private String observacao;

}
