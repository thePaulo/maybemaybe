package br.gov.sead.pagrn.dto.funcao;

import br.gov.sead.pagrn.domain.type.TipoGratificacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import br.gov.sead.pagrn.dto.UserVinculoDtoRequest;

@Getter
@Setter
@ToString
public class FuncaoDtoRequest extends UserVinculoDtoRequest{

    private String denominacao;

    private Double valorVencimento;

    private Double valorRepresentacao;

    private TipoGratificacao tipoGratificacao;


}
