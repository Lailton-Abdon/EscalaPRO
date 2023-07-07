package br.com.fabrica.gesplantao.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CicloRequestDTO {

    @NotEmpty(message = "O campo codigo do ciclo não deve estar vazio")
    private String codigo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @FutureOrPresent(message = "A data de inicio do ciclo deve ser no futuro ou no presente.")
    @NotNull(message = "O campo inicio do ciclo não deve ser nulo")
    private LocalDate inicioCiclo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    @Future(message = "A data de fim do ciclo deve ser no futuro.")
    @NotNull(message = "O campo fim do ciclo não deve ser nulo")
    private LocalDate fimCiclo;

    private List<RodizioRequestDTO> rodizio;
}
