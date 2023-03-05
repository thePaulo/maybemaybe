package br.gov.sead.pagrn.controller;

import br.gov.sead.pagrn.domain.concrets.Funcao;
import br.gov.sead.pagrn.domain.vinculos.Vinculo;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoRequest;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoResponse;
import br.gov.sead.pagrn.service.FuncaoService;
import br.gov.sead.pagrn.service.generic.IFindVinculoById;
import br.gov.sead.pagrn.service.generic.ValidateVinculo;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/funcoes")
@CrossOrigin(origins="*")
public class FuncaoController implements IFindVinculoById {

    private final FuncaoService service;

    private final ModelMapper modelMapper;

    public FuncaoController(FuncaoService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();
    }

    /**
     * Método reponsável por listar todas as pessoas físicas do sistema e encaminhar para o DtoResponse
     */
    @GetMapping
    //@PreAuthorize("hasAnyAuthority('role_funcao')")
    public ResponseEntity<Page<FuncaoDtoResponse>> find(
            @RequestParam(value = "q", defaultValue = "") String query,
            Pageable pageable) {
        Page<FuncaoDtoResponse> funcaoDtoResponses = service.find(query, pageable)
                .map(funcao -> modelMapper.map(funcao, FuncaoDtoResponse.class));
        return new ResponseEntity<>(funcaoDtoResponses, HttpStatus.OK);
    }

    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<FuncaoDtoResponse> insert(@Valid @RequestBody FuncaoDtoRequest dto) {
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        //Map<String, Object> obj = principal.getClaims();
        //String cpf = (String) obj.get("preferred_username");
        Long vinculoId = dto.getIdVinculoResponsavel();*/

        final String name = getSecurityContext().getToken().getPreferredUsername();
        Long vinculoId = dto.getIdVinculoResponsavel();

        System.out.println(name);
        System.out.println(vinculoId);

        ValidateVinculo vv = service.validateRequest(name, vinculoId);

        if (!vv.getIsValid()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        System.out.println("PASSSOU NA VALIDAÇAO DE VINCULO");

        // validar se usuario nao e servidor da SEAD
        if (!vv.getVinculo().getUnidadeOrganizacional().getSigla().equals("SEAD")) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        System.out.println("PASSOU NA VALIDAÇAO DE UO");

        try {
            Funcao funcao = modelMapper.map(dto, Funcao.class);
            Funcao funcaoSaved = service.create(funcao);
            FuncaoDtoResponse funcaoDtoResponse = modelMapper.map(funcaoSaved, FuncaoDtoResponse.class);
            return new ResponseEntity<>(funcaoDtoResponse, HttpStatus.CREATED);
        }catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Vinculo find(Long vinculoId) {
        Optional<Vinculo> vinculoOpt = service.findVinculoById(vinculoId);
        if(vinculoOpt.isPresent()){
            return  vinculoOpt.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // helper method to return the KeycloakSecurityContext object to fetch details from access token
    private KeycloakSecurityContext getSecurityContext() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
    }
}
