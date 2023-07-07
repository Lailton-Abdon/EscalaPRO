package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FeedbackRequestDTO {

    @NotEmpty(message = "O campo descrição não deve estar vazio")
    private String descricao;

    @NotNull(message = "O campo de usuário não deve estar vazio.")
    private Long idPreceptorOrInterno;

    private boolean anonimo;
}

