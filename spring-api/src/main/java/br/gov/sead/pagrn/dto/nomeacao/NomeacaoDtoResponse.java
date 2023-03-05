package br.gov.sead.pagrn.dto.nomeacao;

import br.gov.sead.pagrn.domain.type.RegimeJuridico;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.type.TipoEvento;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.dto.funcao.FuncaoDtoResponse;
import br.gov.sead.pagrn.dto.nivelCargo.NivelCargoDtoResponse;
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
public class NomeacaoDtoResponse{

    private Long id;

    //nomeação
    private LocalDate dataInicioExercicio;

    private LocalDate dataFinalExercicio;

    private LocalDate dataPosse;

    private LocalDate dataNomeacao;

    private Boolean descontaIRPF;

    private RegimeJuridico regimeJuridico;

    private TipoVinculo tipoVinculo;

    private SetorDtoResponse setor;

    private FuncaoDtoResponse funcao;

    private NivelCargoDtoResponse nivelCargo;

    private UnidadeOrganizacionalDtoResponse unidadeOrganizacional;

    private String processoAdministrativo;

    //evento

    private LocalDate dataVigencia;

    private String descricao;

}
