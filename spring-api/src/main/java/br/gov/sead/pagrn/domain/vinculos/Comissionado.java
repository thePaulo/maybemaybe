package br.gov.sead.pagrn.domain.vinculos;

import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;

import javax.persistence.Entity;
import java.util.Set;

@Entity
public class Comissionado extends Vinculo{
    @Override
    public Set<SituacaoVinculo> proximasSituacoesPossiveis(SituacaoVinculo situacao) {
        throw new IllegalStateException(ApiMessages.VALIDACAO_NAO_IMPLEMENTADA);
    }

    @Override
    public Set<String> eventosPermitidos() {
        throw new IllegalStateException(ApiMessages.VALIDACAO_NAO_IMPLEMENTADA);
    }

    @Override
    public boolean validar() {
        throw new IllegalStateException(ApiMessages.VALIDACAO_NAO_IMPLEMENTADA);
    }
}
