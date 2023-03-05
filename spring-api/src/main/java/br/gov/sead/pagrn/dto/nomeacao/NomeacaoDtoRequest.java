package br.gov.sead.pagrn.dto.nomeacao;

import br.gov.sead.pagrn.domain.type.RegimeJuridico;
import br.gov.sead.pagrn.domain.type.SituacaoVinculo;
import br.gov.sead.pagrn.domain.type.TipoEvento;
import br.gov.sead.pagrn.domain.type.TipoVinculo;
import br.gov.sead.pagrn.dto.UserVinculoDtoRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class NomeacaoDtoRequest extends UserVinculoDtoRequest{

    //nomeação
    private LocalDate dataInicioExercicio;

    private LocalDate dataFinalExercicio;

    private LocalDate dataPosse;

    private LocalDate dataNomeacao;

    private Boolean descontaIRPF;

    private RegimeJuridico regimeJuridico;

    private TipoVinculo tipoVinculo;

    private String processoAdministrativo;

    //evento

    private LocalDate dataVigencia;

    private String descricao;

    // FK
    private Long servidor;

    private Long setor;

    private Long funcao;

    private Long nivelCargo;

    private Long unidadeOrganizacional;

}
