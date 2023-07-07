package br.com.fabrica.gesplantao.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class EquipeResponseDTO {

    private String codigoEquipe;

    private Integer quantidadeVagas;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate dataCriacao;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<InternoResponseDTO> internos;
}
