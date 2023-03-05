package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "cargos_uo")
@SQLDelete(sql = "UPDATE cargos_uo SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@RequiredArgsConstructor
public class CargoUO extends AbstractEntity implements Serializable {

    @ManyToOne
    private Cargo cargo;

    @ManyToOne
    private UnidadeOrganizacional unidadeOrganizacional;

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String lotacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataExtincao;

    public void preencher(Cargo cargo, UnidadeOrganizacional unidadeOrganizacional) {
        this.setCargo(cargo);
        this.setUnidadeOrganizacional(unidadeOrganizacional);
    }
}
