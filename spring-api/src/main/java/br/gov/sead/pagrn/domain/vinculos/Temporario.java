package br.gov.sead.pagrn.domain.vinculos;

import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Temporario extends Vinculo {

    @Override
    public Set<SituacaoVinculo> proximasSituacoesPossiveis(SituacaoVinculo situacao) {
        if(situacao.equals(SituacaoVinculo.ATIVO)){
            return Set.of(SituacaoVinculo.ENCERRADO);
        }
        else{
            throw new IllegalStateException(ApiMessages.SITUACAO_NAO_PERTENCE_AO_TIPO_DE_VINCULO);
        }
    }

    @Override
    public Set<String> eventosPermitidos() {
        Set<String> eventosPermitidos = new LinkedHashSet<>();
        switch (this.situacao){
            case ATIVO -> {
                eventosPermitidos.add("encerramento");
                eventosPermitidos.add("ferias");
                eventosPermitidos.add("faltas");
            }
        }
        return eventosPermitidos;
    }

    @Override
    public boolean validar() {
        verificacaoCampo(this.nivelCargo, ApiMessages.NIVEL_CARGO_NAO_PODE_SER_NULO);
        verificacaoCampo(this.dataFinalExercicio, ApiMessages.DATA_FINAL_DE_EXERCICIO_NAO_PODE_SER_NULO);
        validarDatas();
        return true;
    }

    public void validarDatas(){
        super.validarDatas();
        if (this.dataFinalExercicio.isBefore(this.dataInicioExercicio)){
            throw new IllegalArgumentException(ApiMessages.DATA_FINAL_EXERCICIO_INVALIDA);
        }else if(this.dataFinalExercicio.isBefore(LocalDate.now()) || this.dataFinalExercicio.equals(LocalDate.now())){
            throw new IllegalArgumentException(ApiMessages.DATA_FINAL_EXERCICIO_INVALIDA);
        }
    }
}
