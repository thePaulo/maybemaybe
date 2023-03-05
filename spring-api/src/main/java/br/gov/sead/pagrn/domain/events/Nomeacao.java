package br.gov.sead.pagrn.domain.events;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.type.RegimeJuridico;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "nomeacoes")
@SQLDelete(sql = "UPDATE nomeacoes SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
public class Nomeacao extends Evento implements Serializable {

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicioExercicio;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFinalExercicio;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataPosse;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNomeacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    private Boolean descontaIRPF;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    private RegimeJuridico regimeJuridico;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    private TipoVinculo tipoVinculo;

    @NotBlank
    private String processoAdministrativo;

    @NotNull
    @ManyToOne
    private Servidor servidor;

    @ManyToOne
    @JoinColumn(name = "nivel_cargo_id")
    private NivelCargo nivelCargo;

    @ManyToOne
    @JoinColumn(name = "funcao_id")
    private Funcao funcao;

    @ManyToOne
    @JoinColumn(name = "setor_id")
    @NotNull
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "unidade_organizacional_id")
    @NotNull
    private UnidadeOrganizacional unidadeOrganizacional;


    /**
     * metodo reponsavel por comparar objetos de Nomeação
     *
     * @param o objeto qualquer
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Nomeacao that = (Nomeacao) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void preencher(Servidor servidor, UnidadeOrganizacional unidadeOrganizacional, Setor setor, NivelCargo nivelCargo, Funcao funcao) {
        this.setServidor(servidor);
        this.setUnidadeOrganizacional(unidadeOrganizacional);
        this.setSetor(setor);
        this.setNivelCargo(nivelCargo);
        this.setFuncao(funcao);
        this.setDataRegistro(LocalDate.now());
        this.setDataVigencia(this.dataInicioExercicio);
    }
}