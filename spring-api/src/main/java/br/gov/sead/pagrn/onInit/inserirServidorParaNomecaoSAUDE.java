package br.gov.sead.pagrn.onInit;

import br.gov.sead.pagrn.domain.concrets.Deficiencia;
import br.gov.sead.pagrn.domain.concrets.Endereco;
import br.gov.sead.pagrn.domain.concrets.PessoaFisica;
import br.gov.sead.pagrn.domain.concrets.Servidor;
import br.gov.sead.pagrn.domain.type.*;
import br.gov.sead.pagrn.service.PessoaFisicaService;
import br.gov.sead.pagrn.service.ServidorService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Component
@Profile("dev")
public record inserirServidorParaNomecaoSAUDE(PessoaFisicaService pessoaFisicaService, ServidorService servidorService) {

    @PostConstruct
    public void init() {
        inserirPessoaFisica();
        inserirServidor();
    }

    private void inserirPessoaFisica(){
        Deficiencia deficiencia = new Deficiencia();
        Endereco endereco = new Endereco();
        endereco.setBairro("Potengi");
        endereco.setCep("12334-132");
        endereco.setCidade("Natal");
        endereco.setLogradouro("Avareza");
        endereco.setNumero("10");
        endereco.setTipoLogradouro(TipoLogradouro.RUA);
        endereco.setPais(Pais.BRASIL);
        endereco.setUnidadeFederativa(UnidadeFederativa.RIO_GRANDE_DO_NORTE);

        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setNome("Jose Pereira");
        pessoaFisica.setCpf("02434283080");
        pessoaFisica.setEmail("josepereira@gmail.com");
        pessoaFisica.setGenero(Genero.MASCULINO);
        pessoaFisica.setDataNascimento(LocalDate.EPOCH);
        pessoaFisica.setTipoSanguineo(TipoSanguineo.Op);
        pessoaFisica.setNomePai("Cicrano");
        pessoaFisica.setNomeMae("Fulano");
        pessoaFisica.setNacionalidadePais("Brasileiro");
        pessoaFisica.setNaturalidadeCidade("Natal");
        pessoaFisica.setEstadoVital(EstadoVital.VIVO);
        pessoaFisica.setEstadoCivil(EstadoCivil.CASADO);
        pessoaFisica.setDeficiencias(deficiencia);
        pessoaFisica.setEndereco(endereco);

        pessoaFisicaService.insert(pessoaFisica);
    }

    private void inserirServidor(){
        Servidor servidor = new Servidor();
        servidor.setMatricula("12347");
        servidor.setPispasep("54323");
        servidor.setNomeSocial("Ze");
        servidor.setEscolaridade(Escolaridade.ENSINO_MEDIO);

        servidorService.insert(servidor, 3L);
    }
}
