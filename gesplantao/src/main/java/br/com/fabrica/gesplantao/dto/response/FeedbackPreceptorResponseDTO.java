package br.com.fabrica.gesplantao.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedbackPreceptorResponseDTO {

    private String descricao;

    private PreceptorResponseDTO preceptor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String autor;

    private LocalDate dataFeedback;

}
