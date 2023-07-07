package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RodizioResponseDTO {

    private Long id;

    private String descricao;

    private Integer quantPlantao;

    private List<EspecialidadeResponseDTO> especialidade;

    private List<PreceptorResponseDTO> preceptor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EquipeResponseDTO equipe;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<PlantaoResponseDTO> plantao;

    private LocalResponseDTO local;

    private StatusRodizioEnum statusRodizio;
}