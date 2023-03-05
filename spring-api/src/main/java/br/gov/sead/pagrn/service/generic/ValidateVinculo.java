package br.gov.sead.pagrn.service.generic;

import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import lombok.Getter;

@Getter
public class ValidateVinculo {
    private Boolean isValid;
    private Vinculo vinculo;

    public ValidateVinculo(Boolean isValid, Vinculo vinculo) {
        this.isValid = isValid;
        this.vinculo = vinculo;
    }
}
