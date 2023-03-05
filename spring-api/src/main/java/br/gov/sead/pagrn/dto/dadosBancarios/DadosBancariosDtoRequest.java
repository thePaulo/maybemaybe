package br.gov.sead.pagrn.dto.dadosBancarios;

import br.gov.sead.pagrn.domain.type.TipoConta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DadosBancariosDtoRequest {

    private String codigoBanco;

    private String codigoAgencia;

    private String numeroConta;

    private String numeroContaDV;

    private TipoConta tipoConta;
}
