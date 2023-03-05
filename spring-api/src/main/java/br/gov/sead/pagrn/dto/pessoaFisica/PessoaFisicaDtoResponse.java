package br.gov.sead.pagrn.dto.pessoaFisica;

import br.gov.sead.pagrn.domain.concrets.Deficiencia;
import br.gov.sead.pagrn.domain.type.*;
import br.gov.sead.pagrn.dto.dadosBancarios.DadosBancariosDtoResponse;
import br.gov.sead.pagrn.dto.endereco.EnderecoDtoResponse;
import br.gov.sead.pagrn.dto.telefone.TelefoneDtoResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class PessoaFisicaDtoResponse{

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private Genero genero;

    private LocalDate dataNascimento;

    private TipoSanguineo tipoSanguineo;

    private String nomeMae;

    private String nomePai;

    private EstadoCivil estadoCivil;

    private String nacionalidadePais;

    private String naturalidadeCidade;

    private Deficiencia deficiencias;

    private TelefoneDtoResponse telefone;

    private EnderecoDtoResponse endereco;

    private DadosBancariosDtoResponse dadosBancarios;

    private EstadoVital estadoVital;

    /**
     * Construtor de pessoaFisicaDtoResponse, transfere os dados enviando respostas do controller ao front
     *
     * @param pessoaFisica
     */

}
