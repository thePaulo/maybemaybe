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
public record inserirServidorParaNomeacaoSEAD(PessoaFisicaService pessoaFisicaService, ServidorService servidorService) {

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
        pessoaFisica.setNome("Maria da Silva");
        pessoaFisica.setCpf("90198641656");
        pessoaFisica.setEmail("maraia@gmail.com");
        pessoaFisica.setGenero(Genero.FEMININO);
        pessoaFisica.setDataNascimento(LocalDate.EPOCH);
        pessoaFisica.setTipoSanguineo(TipoSanguineo.Op);
        pessoaFisica.setNomePai("Maria da Prestação");
        pessoaFisica.setNomeMae("Joao Maria");
        pessoaFisica.setNacionalidadePais("Brasileiro");
        pessoaFisica.setNaturalidadeCidade("Natal");
        pessoaFisica.setEstadoVital(EstadoVital.VIVO);
        pessoaFisica.setEstadoCivil(EstadoCivil.SOLTEIRO);
        pessoaFisica.setDeficiencias(deficiencia);
        pessoaFisica.setEndereco(endereco);

        pessoaFisicaService.insert(pessoaFisica);
    }

    private void inserirServidor(){
        Servidor servidor = new Servidor();
        servidor.setMatricula("12346");
        servidor.setPispasep("54322");
        servidor.setNomeSocial("Mariazinha");
        servidor.setEscolaridade(Escolaridade.SUPERIOR);

        servidorService.insert(servidor, 2L);
    }
}
