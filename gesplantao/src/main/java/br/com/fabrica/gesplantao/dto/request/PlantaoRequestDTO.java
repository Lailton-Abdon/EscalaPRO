package br.com.fabrica.gesplantao.dto.request;

import br.com.fabrica.gesplantao.model.Atividade;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class PlantaoRequestDTO {

    @NotEmpty(message = "O campo codigo do plantão não deve estar vazio")
    private String codigoPlantao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @FutureOrPresent(message = "A data do plantão deve ser no futuro ou no presente.")
    @NotNull(message = "O campo data do plantão não deve ser nulo")
    private LocalDate dataInicioPlantao;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    @FutureOrPresent(message = "A data de inicio do plantão deve ser no futuro ou no presente.")
    @NotNull(message = "O campo inicio do plantão não deve ser nulo")
    private LocalTime horaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", locale = "pt-BR", timezone = "Brazil/East")
    @Future(message = "A data de fim do plantão deve ser no futuro.")
    @NotNull(message = "O campo fim do plantão não deve ser nulo")
    private LocalTime horaFim;

    @NotEmpty(message = "O campo atividades não deve estar vazio")
    private List<Long> idAtividades;
}
