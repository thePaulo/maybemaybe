package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import br.gov.sead.pagrn.domain.type.TipoGratificacao;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "funcoes")
@SQLDelete(sql = "UPDATE funcoes SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Funcao extends AbstractEntity implements Serializable {

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String denominacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    private Double valorVencimento;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    private Double valorRepresentacao;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    private TipoGratificacao tipoGratificacao;
}
