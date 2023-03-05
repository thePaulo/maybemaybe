package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "niveis_cargos")
@SQLDelete(sql = "UPDATE niveis_cargos SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@RequiredArgsConstructor
public class NivelCargo extends AbstractEntity {

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private CargoUO cargoUO;

    @NotBlank
    private String sigla;

    @NotNull
    private Double remuneracaoBase;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataExtincao;

    public void preencher(CargoUO cargoUO) {
        this.setCargoUO(cargoUO);
    }
}
