package br.gov.sead.pagrn.onInit;

import br.gov.sead.pagrn.domain.concrets.*;
import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.domain.type.*;
import br.gov.sead.pagrn.service.*;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
@Profile("dev")
public record Insercoes(PessoaFisicaService pessoaFisicaService, ServidorService servidorService,
                        PessoaJuridicaService pessoaJuridicaService, UnidadeOrganizacionalService unidadeOrganizacionalService,
                        SetorService setorService, CargoService cargoService, NivelCargoService nivelCargoService,
                        NomeacaoService nomeacaoService) {

    @PostConstruct
    public void init() {
        inserirPessoaFisica();
        inserirServidor();
        inserirPessoaJuridica();
        inserirUnidadeOrganizacional();
        inserirUnidadeOrganizacional2();
        inserirSetor();
        inserirCargo();
        inserirNivelCargo();
        inserirNomeacao();
        inserirNomeacao2();
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
        pessoaFisica.setNome("João Grilo");
        pessoaFisica.setCpf("84833783452");
        pessoaFisica.setEmail("joaogrilo@gmail.com");
        pessoaFisica.setGenero(Genero.MASCULINO);
        pessoaFisica.setDataNascimento(LocalDate.EPOCH);
        pessoaFisica.setTipoSanguineo(TipoSanguineo.Op);
        pessoaFisica.setNomePai("João da Prestação");
        pessoaFisica.setNomeMae("Maria Joana");
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
        servidor.setMatricula("12345");
        servidor.setPispasep("54321");
        servidor.setNomeSocial("Joãozinho");
        servidor.setEscolaridade(Escolaridade.SUPERIOR);

        servidorService.insert(servidor, 1L);
    }

    private void inserirPessoaJuridica(){
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setNome("Governo do Estado");
        pessoaJuridica.setEmail("governoDoEstado@gmail.com");

        Set<Telefone> telefones = new LinkedHashSet<>();
        Telefone telefone = new Telefone("(84)98765-1234", "");
        telefones.add(telefone);

        Endereco endereco = new Endereco();
        endereco.setBairro("Igapó");
        endereco.setCep("12334-142");
        endereco.setCidade("Natal");
        endereco.setLogradouro("Varzea");
        endereco.setNumero("SN");
        endereco.setTipoLogradouro(TipoLogradouro.AVENIDA);
        endereco.setPais(Pais.BRASIL);
        endereco.setUnidadeFederativa(UnidadeFederativa.RIO_GRANDE_DO_NORTE);

        pessoaJuridica.setRazaoSocial("govrn");
        pessoaJuridica.setCnpj("88450606000170");
        pessoaJuridica.setClassificacaoTributaria(ClassificacaoTributaria.TIPO_1);

        pessoaJuridicaService.insert(pessoaJuridica, endereco, telefones);
    }

    private void inserirUnidadeOrganizacional(){
        UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
        unidadeOrganizacional.setDataInicioOperacao(LocalDate.now());
        unidadeOrganizacional.setCodIbgeCnae("31231");
        unidadeOrganizacional.setCodigoLegado("1");
        unidadeOrganizacional.setSigla("SEAD");

        unidadeOrganizacionalService.insert(unidadeOrganizacional, 1L);
    }

    private void inserirUnidadeOrganizacional2(){
        UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
        unidadeOrganizacional.setDataInicioOperacao(LocalDate.now());
        unidadeOrganizacional.setCodIbgeCnae("31232");
        unidadeOrganizacional.setCodigoLegado("2");
        unidadeOrganizacional.setSigla("SAUDE");

        unidadeOrganizacionalService.insert(unidadeOrganizacional, 1L);
    }

    private void inserirSetor(){
        Endereco endereco = new Endereco();
        endereco.setBairro("Igapó");
        endereco.setCep("12334-142");
        endereco.setCidade("Natal");
        endereco.setLogradouro("Varzea 2");
        endereco.setNumero("SN");
        endereco.setTipoLogradouro(TipoLogradouro.AVENIDA);
        endereco.setPais(Pais.BRASIL);
        endereco.setUnidadeFederativa(UnidadeFederativa.RIO_GRANDE_DO_NORTE);

        Setor setor = new Setor();
        setor.setDenominacao("Setor de RH");
        setor.setSigla("RH");
        setorService.insert(setor, null,1L, endereco);
    }

    private void inserirCargo(){
        Cargo cargo = new Cargo();
        cargo.setDenominacao("Programador");
        cargo.setDataCriacao(LocalDate.now());
        cargo.setEscolaridade(Escolaridade.SUPERIOR);

        cargoService.create(cargo);
    }

    private void inserirNivelCargo(){
        NivelCargo nivelCargo = new NivelCargo();
        nivelCargo.setSigla("Jr");
        nivelCargo.setRemuneracaoBase(4000.0);
        nivelCargo.setDataCriacao(LocalDate.now());

        CargoUO cargoUO = new CargoUO();
        cargoUO.setDataCriacao(LocalDate.now());
        cargoUO.setLotacao("20");

        nivelCargoService.insert(nivelCargo, cargoUO,1L, 1L);
    }

    private void inserirNomeacao(){
        Nomeacao nomeacao = new Nomeacao();
        nomeacao.setDataNomeacao(LocalDate.now());
        nomeacao.setDataPosse(LocalDate.now());
        nomeacao.setDataInicioExercicio(LocalDate.now());
//        nomeacao.setDataFinalExercicio(LocalDate.of(2022, 9, 13));
        nomeacao.setDescontaIRPF(Boolean.TRUE);
        nomeacao.setProcessoAdministrativo("31287631231");
        nomeacao.setRegimeJuridico(RegimeJuridico.RJU);
        nomeacao.setTipoVinculo(TipoVinculo.EFETIVO_CIVIL);
        nomeacao.setDataVigencia(LocalDate.now());
        nomeacao.setDescricao("Nomeação de servidor efetivo civil");
        nomeacaoService.nomear(nomeacao, 1L , 1L, 1L, 1L, null);
    }

    private void inserirNomeacao2(){
        Nomeacao nomeacao = new Nomeacao();
        nomeacao.setDataNomeacao(LocalDate.now());
        nomeacao.setDataPosse(LocalDate.now());
        nomeacao.setDataInicioExercicio(LocalDate.now());
//        nomeacao.setDataFinalExercicio(LocalDate.of(2022, 9, 13));
        nomeacao.setDescontaIRPF(Boolean.TRUE);
        nomeacao.setProcessoAdministrativo("31287631232");
        nomeacao.setRegimeJuridico(RegimeJuridico.RJU);
        nomeacao.setTipoVinculo(TipoVinculo.EFETIVO_CIVIL);
        nomeacao.setDataVigencia(LocalDate.now());
        nomeacao.setDescricao("Nomeação de servidor efetivo civil");
        nomeacaoService.nomear(nomeacao, 1L , 1L, 2L, 1L, null);
    }

}
