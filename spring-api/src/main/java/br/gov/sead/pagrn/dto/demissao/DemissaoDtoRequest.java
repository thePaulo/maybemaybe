package br.gov.sead.pagrn.dto.demissao;

import br.gov.sead.pagrn.domain.type.TipoAposentadoria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DemissaoDtoRequest {

    //Demissao
    private String motivo;

    //TODO esclarecer se esse attr continua
    //private String processoAdministrativo;

    private String dadosPublicacao;

    //evento

    private LocalDate dataVigencia;

    private LocalDate dataRegistro;

    private String descricao;

    // FK
    private Long vinculo;

}
