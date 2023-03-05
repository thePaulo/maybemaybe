package br.gov.sead.pagrn.domain.vinculos;

import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;

import javax.persistence.Entity;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Celetista extends Vinculo{
    @Override
    public Set<SituacaoVinculo> proximasSituacoesPossiveis(SituacaoVinculo situacao) {
        throw new IllegalStateException(ApiMessages.VALIDACAO_NAO_IMPLEMENTADA);
    }

    @Override
    public Set<String> eventosPermitidos() {
        Set<String> eventosPermitidos = new LinkedHashSet<>();
        switch (this.situacao){
            case ATIVO -> {
                eventosPermitidos.add("afastamento");
                eventosPermitidos.add("aposentadoria");
                eventosPermitidos.add("disponibilidade");
                eventosPermitidos.add("demissao");
                eventosPermitidos.add("ferias");
                eventosPermitidos.add("faltas");
            }
            case AFASTADO -> eventosPermitidos.add("retorno");
            case APOSENTADO -> eventosPermitidos.add("reversao");
            case EM_DISPOSICAO -> eventosPermitidos.add("aproveitamento");
            case DEMITIDO -> eventosPermitidos.add("reintegracao");
        }
        return eventosPermitidos;
    }

    @Override
    public boolean validar() {
        throw new IllegalStateException(ApiMessages.VALIDACAO_NAO_IMPLEMENTADA);
    }
}
