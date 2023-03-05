package br.gov.sead.pagrn.dto.encerramento;

import br.gov.sead.pagrn.domain.type.TipoEvento;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class EncerramentoDtoResponse {

    private Long id;

    //evento
    private LocalDate dataRegistro;

    private LocalDate dataVigencia;

    private String descricao;
}
