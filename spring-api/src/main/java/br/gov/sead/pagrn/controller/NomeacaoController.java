package br.gov.sead.pagrn.controller;

import br.gov.sead.pagrn.domain.events.Nomeacao;
import br.gov.sead.pagrn.dto.nomeacao.NomeacaoDtoRequest;
import br.gov.sead.pagrn.dto.nomeacao.NomeacaoDtoResponse;
import br.gov.sead.pagrn.service.NomeacaoService;
import br.gov.sead.pagrn.service.generic.ValidateVinculo;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/nomeacoes")
@CrossOrigin(origins="*")
public class NomeacaoController {

    private final NomeacaoService service;

    private final ModelMapper modelMapper;

    /**
     * Construtor do controller de Cargo
     *
     * @param service
     */
    public NomeacaoController(NomeacaoService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();

        TypeMap<NomeacaoDtoRequest, Nomeacao> propertyMapper = this.modelMapper.createTypeMap(NomeacaoDtoRequest.class, Nomeacao.class);
        propertyMapper.addMappings(mapper -> mapper.skip(Nomeacao::setId));
    }

    /**
     * Método reponsável por listar todos os cargos do sistema e encaminhar para o DtoResponse
     */
    @GetMapping
    public ResponseEntity<Page<NomeacaoDtoResponse>> find(
            @RequestParam(value = "q", defaultValue = "") String query,
            Pageable pageable) {
        Page<NomeacaoDtoResponse> nomeacaoDtoResponses = service.find(query, pageable)
                .map(nomeacao -> modelMapper.map(nomeacao, NomeacaoDtoResponse.class));
        return new ResponseEntity<>(nomeacaoDtoResponses, HttpStatus.OK);
    }


    /**
     * Método reponsável por receber um request de um cargo para inserir no sistema
     *
     * @param dto
     */
    @PostMapping
    public ResponseEntity<NomeacaoDtoResponse> insert(@Valid @RequestBody NomeacaoDtoRequest dto) {

        final String name = getSecurityContext().getToken().getPreferredUsername();
        Long vinculoId = dto.getIdVinculoResponsavel();

        System.out.println(name);
        System.out.println(vinculoId);

        ValidateVinculo vv = service.validateRequest(name, vinculoId);

        if (!vv.getIsValid()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        System.out.println("PASSSOU NA VALIDAÇAO DE VINCULO");

        // validar se usuario nao e servidor da SEAD, se true entao a UO da servidor do dto
        // deve ser igual ao do usuario
        if (!vv.getVinculo().getUnidadeOrganizacional().getSigla().equals("SEAD")) {
            Long idUO = dto.getUnidadeOrganizacional();
            if (!Objects.equals(idUO, vv.getVinculo().getUnidadeOrganizacional().getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
        System.out.println("PASSOU NA VALIDAÇAO DE UO");

        try {
            Nomeacao nomeacao = modelMapper.map(dto, Nomeacao.class);

            Long idServidor = dto.getServidor();
            Long idNivelCargo = dto.getNivelCargo();
            Long idSetor = dto.getSetor();
            Long idFuncao = dto.getFuncao();
            Long idUnidadeOrganizacional = dto.getUnidadeOrganizacional();

            Nomeacao nomeacaoSaved = service.nomear(nomeacao, idNivelCargo, idServidor, idUnidadeOrganizacional, idSetor, idFuncao);

            NomeacaoDtoResponse nomeacaoDtoResponse = modelMapper.map(nomeacaoSaved, NomeacaoDtoResponse.class);

            return new ResponseEntity<>(nomeacaoDtoResponse, HttpStatus.CREATED);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage());
        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
        }
    }

    // helper method to return the KeycloakSecurityContext object to fetch details from access token
    private KeycloakSecurityContext getSecurityContext() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }
}
