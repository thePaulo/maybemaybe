package br.gov.sead.pagrn.service;

import br.gov.sead.pagrn.domain.concrets.Funcao;
import br.gov.sead.pagrn.domain.concrets.PessoaFisica;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.repository.FuncaoRepository;
import br.gov.sead.pagrn.service.generic.AbstractService;
import br.gov.sead.pagrn.service.generic.ValidateVinculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FuncaoService extends AbstractService<Funcao, FuncaoRepository> {

    @Autowired
    private VinculoService vinculoService;


    @Autowired
    private ServidorService servidorService;

    public FuncaoService(FuncaoRepository repository){
        super(repository);
    }

    public Optional<Vinculo> findVinculoById(Long vinculoId) {
        return vinculoService.findById(vinculoId);
    }

    public ValidateVinculo validateRequest(String cpf, Long idVinculo){
        // verifica se o vinculo existe
        Optional<Vinculo> v = vinculoService.findById(idVinculo);
        if (v.isEmpty()) {
            return new ValidateVinculo(false, null);
        }

        // verifica se o vinculo e ativo
        if (v.get().getSituacao() != SituacaoVinculo.ATIVO) {
            return new ValidateVinculo(false, null);
        }

        // vefifica se o vinculo pertence ao cpf
        List<Long> vinculosId = vinculoService.findByCPFdoServidor(cpf);
        if (!vinculosId.contains(idVinculo)) {
            return new ValidateVinculo(false, null);
        }

        return new ValidateVinculo(true, v.get());
    }
}
