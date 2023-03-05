package br.gov.sead.pagrn.dto.pessoaJuridica;

import br.gov.sead.pagrn.domain.type.ClassificacaoTributaria;
import br.gov.sead.pagrn.dto.endereco.EnderecoDtoResponse;
import br.gov.sead.pagrn.dto.telefone.TelefoneDtoResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PessoaJuridicaDtoResponse {

    private Long id;

    private String nome;

    private String razaoSocial;

    private String cnpj;

    private ClassificacaoTributaria classificacaoTributaria;

    private String email;

    private Set<TelefoneDtoResponse> telefones;

    private EnderecoDtoResponse endereco;

}