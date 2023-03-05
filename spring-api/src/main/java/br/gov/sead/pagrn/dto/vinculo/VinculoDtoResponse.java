package br.gov.sead.pagrn.dto.vinculo;

import br.gov.sead.pagrn.domain.type.RegimeJuridico;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoResponse;
import br.gov.sead.pagrn.dto.nivelCargo.NivelCargoDtoResponse;
import br.gov.sead.pagrn.dto.servidor.ServidorDtoResponse;
import br.gov.sead.pagrn.dto.setor.SetorDtoResponse;
import br.gov.sead.pagrn.dto.unidadeOrganizacional.UnidadeOrganizacionalDtoResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class VinculoDtoResponse {
    private Long id;

    private LocalDate dataInicioExercicio;

    private LocalDate dataFinalExercicio;

    private LocalDate dataPosse;

    private LocalDate dataNomeacao;

    private SituacaoVinculo situacao;

    private RegimeJuridico regimeJuridico;

    private TipoVinculo tipoVinculo;

    private ServidorDtoResponse servidor;

    private NivelCargoDtoResponse nivelCargo;

    private SetorDtoResponse setor;

    private UnidadeOrganizacionalDtoResponse unidadeOrganizacional;

    private FuncaoDtoResponse funcao;

}
