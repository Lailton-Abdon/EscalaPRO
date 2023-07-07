package br.com.fabrica.gesplantao.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FeedbackInternoResponseDTO {

    private String descricao;

    private InternoResponseDTO interno;

    private String autor;

    private LocalDate dataFeedback;
}
