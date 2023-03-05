package br.gov.sead.pagrn.dto.deficiencia;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeficienciaDtoResponse {

    private Long id;

    private Boolean fisica;

    private Boolean visual;

    private Boolean auditiva;

    private Boolean mental;

    private Boolean intelectual;

    private String observacao;
}
