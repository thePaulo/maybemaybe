package br.gov.sead.pagrn.domain.concrets;

import br.gov.sead.pagrn.domain.generic.AbstractEntity;
import br.gov.sead.pagrn.domain.type.TipoConta;
import br.gov.sead.pagrn.errorhandling.ApiMessages;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity(name = "dados_bancarios")
@SQLDelete(sql = "UPDATE dados_bancarios SET removed = CURRENT_DATE WHERE id=?")
@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
public class DadosBancarios extends AbstractEntity implements Serializable {

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String codigoBanco;

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String codigoAgencia;

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String numeroConta;

    @NotBlank(message = ApiMessages.BRANCO_NAO_PERMITIDO)
    private String numeroContaDV;

    @NotNull(message = ApiMessages.NULO_NAO_PERMITIDO)
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;
}
