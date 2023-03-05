package br.gov.sead.pagrn.dto.exoneracao;

import br.gov.sead.pagrn.domain.type.TipoEvento;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class ExoneracaoDtoResponse {
    private Long id;

    private String processoAdministrativo;

    //evento

    private LocalDate dataVigencia;

    private String descricao;
}
