package br.gov.sead.pagrn.controller;

import br.gov.sead.pagrn.domain.concrets.CargoUO;
import br.gov.sead.pagrn.domain.concrets.NivelCargo;
import br.gov.sead.pagrn.dto.cargoUO.CargoUODtoRequest;
import br.gov.sead.pagrn.dto.nivelCargo.NivelCargoDtoRequest;
import br.gov.sead.pagrn.dto.nivelCargo.NivelCargoDtoResponse;
import br.gov.sead.pagrn.service.NivelCargoService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/nivelcargo")
@CrossOrigin(origins="*")
public class NivelCargoController {

    private final NivelCargoService service;

    private final ModelMapper modelMapper;

    public NivelCargoController(NivelCargoService service) {
        this.service = service;
        this.modelMapper = new ModelMapper();

        TypeMap<CargoUODtoRequest, CargoUO> propertyMapper = modelMapper.createTypeMap(CargoUODtoRequest.class, CargoUO.class);
        propertyMapper.addMappings(mapper -> mapper.skip(CargoUO::setId));
    }

    @GetMapping
    public ResponseEntity<Page<NivelCargoDtoResponse>> find(
            @RequestParam(value = "q", defaultValue = "") String query,
            Pageable pageable) {
        Page<NivelCargoDtoResponse> nivelCargoDtoResponses = service.find(query, pageable)
                .map(NivelCargo -> modelMapper.map(NivelCargo, NivelCargoDtoResponse.class));
        return new ResponseEntity<>(nivelCargoDtoResponses, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NivelCargoDtoResponse> insert(@Valid @RequestBody NivelCargoDtoRequest dto) {
        try {
            NivelCargo NivelCargo = modelMapper.map(dto, NivelCargo.class);
            CargoUO cargoUO = modelMapper.map(dto.getCargoUO(), CargoUO.class);
            NivelCargo NivelCargoSaved = service.insert(NivelCargo, cargoUO,dto.getCargoUO().getCargo(), dto.getCargoUO().getUnidadeOrganizacional());
            NivelCargoDtoResponse NivelCargoDtoResponse = modelMapper.map(NivelCargoSaved, NivelCargoDtoResponse.class);
            return new ResponseEntity<>(NivelCargoDtoResponse, HttpStatus.CREATED);
        }catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
