package br.gov.sead.pagrn.service.generic;

import br.gov.sead.pagrn.domain.vinculos.Vinculo;

public interface IFindVinculoById {

    Vinculo find(Long vinculoId);
}
