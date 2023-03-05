package br.gov.sead.pagrn.domain.vinculos;

import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;

import javax.persistence.Entity;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class EfetivoMilitar extends Vinculo {
    @Override
    public Set<SituacaoVinculo> proximasSituacoesPossiveis(SituacaoVinculo situacao) {
        if (situacao.equals(SituacaoVinculo.ATIVO)) {
            return Set.of(SituacaoVinculo.APOSENTADO, SituacaoVinculo.AFASTADO, SituacaoVinculo.EXONERADO,
                    SituacaoVinculo.SUSPENSO, SituacaoVinculo.EM_DISPOSICAO);
        } else if (situacao.equals(SituacaoVinculo.APOSENTADO) ||
                situacao.equals(SituacaoVinculo.EM_DISPOSICAO) ||
                situacao.equals(SituacaoVinculo.AFASTADO)) {
            return Set.of(SituacaoVinculo.ATIVO);
        } else if (situacao.equals(SituacaoVinculo.SUSPENSO)) {
            return Set.of(SituacaoVinculo.ATIVO, SituacaoVinculo.EXONERADO);
        } else {
            throw new IllegalStateException(ApiMessages.SITUACAO_NAO_PERTENCE_AO_TIPO_DE_VINCULO);
        }
    }

    @Override
    public Set<String> eventosPermitidos() {
        Set<String> eventosPermitidos = new LinkedHashSet<>();
        switch (this.situacao){
            case ATIVO -> {
                eventosPermitidos.add("afastamento");
                eventosPermitidos.add("aposentadoria");
                eventosPermitidos.add("disponibilidade");
                eventosPermitidos.add("vacancia");
                eventosPermitidos.add("exoneracao");
                eventosPermitidos.add("ferias");
                eventosPermitidos.add("faltas");
            }
            case AFASTADO -> eventosPermitidos.add("retorno");
            case APOSENTADO -> eventosPermitidos.add("reversao");
            case SUSPENSO -> eventosPermitidos.add("reconducao");
            case EM_DISPOSICAO -> eventosPermitidos.add("aproveitamento");
        }
        return eventosPermitidos;
    }

    @Override
    public boolean validar() {
        verificacaoCampo(this.nivelCargo, ApiMessages.NIVEL_CARGO_NAO_PODE_SER_NULO);
        validarDatas();
        return true;
    }
}
