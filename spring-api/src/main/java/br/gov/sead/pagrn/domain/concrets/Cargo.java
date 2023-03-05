package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import br.gov.sead.pagrn.domain.type.Escolaridade;
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

@Entity(name = "cargos")
@SQLDelete(sql = "UPDATE cargos SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Cargo extends AbstractEntity implements Serializable {

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String denominacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataExtincao;

    @Enumerated(EnumType.STRING)
    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    private Escolaridade escolaridade;

    @ManyToOne(cascade = CascadeType.ALL)
    private CategoriaFuncional categoriaFuncional;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Cargo that = (Cargo) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
