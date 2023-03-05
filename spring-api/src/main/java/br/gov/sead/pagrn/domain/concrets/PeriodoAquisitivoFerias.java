package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import java.io.Serializable;
import java.time.LocalDate;

@Entity(name = "periodos_aquisitivos_ferias")
@SQLDelete(sql = "UPDATE periodos_aquisitivos_ferias SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PeriodoAquisitivoFerias extends AbstractEntity implements Serializable {

    private LocalDate dataInicio;

    private LocalDate dataFim;
}
