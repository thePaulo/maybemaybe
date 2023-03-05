package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;

@Entity(name = "deficiencias")
@SQLDelete(sql = "UPDATE deficiencias SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Deficiencia extends AbstractEntity{

    private boolean fisica;

    private boolean visual;

    private boolean auditiva;

    private boolean mental;

    private boolean intelectual;

    private String observacao;
}
