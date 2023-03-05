package br.gov.sead.pagrn.domain.vinculos;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Evento;
import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import br.gov.sead.pagrn.domain.type.RegimeJuridico;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;

import static java.util.Objects.isNull;

@Entity(name = "vinculos")
@SQLDelete(sql = "UPDATE vinculos SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Vinculo extends AbstractEntity implements Serializable {

    public Vinculo() {
        this.eventos = new LinkedHashSet<>();
    }

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate dataInicioExercicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate dataFinalExercicio;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate dataPosse;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    protected LocalDate dataNomeacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    protected SituacaoVinculo situacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    protected RegimeJuridico regimeJuridico;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    protected TipoVinculo tipoVinculo;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @ManyToOne
    @JoinColumn(name = "servidor_id")
    protected Servidor servidor;

    @ManyToOne
    protected NivelCargo nivelCargo;

    @ManyToOne
    protected Funcao funcao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @OneToOne
    protected Setor setor;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @OneToOne
    protected UnidadeOrganizacional unidadeOrganizacional;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @JoinColumn(name = "vinculo_id")
    protected Set<Evento> eventos;

    protected static void verificacaoCampo(Object o, String mensagem) {
        if (isNull(o)) {
            throw new IllegalArgumentException(mensagem);
        }
    }

    public void registrarEvento(Evento evento) {
        this.eventos.add(evento);
    }

    public void mudarSituacao(SituacaoVinculo novaSituacao) {
        Set<SituacaoVinculo> situacoesPossiveis = proximasSituacoesPossiveis(this.getSituacao());
        if(!situacoesPossiveis.contains(novaSituacao)){
            throw new IllegalStateException();
        }

        this.setSituacao(novaSituacao);
    }

    public abstract Set<SituacaoVinculo> proximasSituacoesPossiveis(SituacaoVinculo situacao);

    public abstract Set<String> eventosPermitidos();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Vinculo that = (Vinculo) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static Vinculo fabricarVinculo(TipoVinculo tipoVinculo, Nomeacao nomeacao){
        Vinculo vinculo = null;
        switch (tipoVinculo){
            case EFETIVO_CIVIL -> {
                vinculo = new EfetivoCivil();
                vinculo.setNivelCargo(nomeacao.getNivelCargo());
            }
            case EFETIVO_MILITAR -> {
                vinculo = new EfetivoMilitar();
                vinculo.setNivelCargo(nomeacao.getNivelCargo());
            }
            case ESTATUTARIO -> {return new Estatutario();}
            case CELETISTA -> {return new Celetista();}
            case COMISSIONADO -> {return new Comissionado();}
            case TEMPORARIO -> {
                vinculo = new Temporario();
                vinculo.setNivelCargo(nomeacao.getNivelCargo());
                vinculo.setDataFinalExercicio(nomeacao.getDataFinalExercicio());
            }
            default -> {throw new IllegalArgumentException(ApiMessages.TIPO_VINCULO_INEXISTENTE);}
        }
        vinculo.setSetor(nomeacao.getSetor());
        vinculo.setUnidadeOrganizacional(nomeacao.getUnidadeOrganizacional());
        vinculo.setRegimeJuridico(RegimeJuridico.RJU);
        vinculo.setTipoVinculo(nomeacao.getTipoVinculo());
        vinculo.setSituacao(SituacaoVinculo.ATIVO);
        vinculo.setServidor(nomeacao.getServidor());
        vinculo.setDataNomeacao(nomeacao.getDataNomeacao());
        vinculo.setDataPosse(nomeacao.getDataPosse());
        vinculo.setDataInicioExercicio(nomeacao.getDataInicioExercicio());
        return vinculo;
    }

    public abstract boolean validar();

    public void validarDatas() {
        if (this.dataNomeacao.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(ApiMessages.DATA_NOMEACAO_INVALIDA);
        }
        if (this.dataPosse.isBefore(this.dataNomeacao) || this.dataPosse.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ApiMessages.DATA_POSSE_INVALIDA);
        }
        if (this.dataInicioExercicio.isBefore(this.dataNomeacao) || dataInicioExercicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(ApiMessages.DATA_INICIO_EXERCICIO_INVALIDA);
        }
    }
}
