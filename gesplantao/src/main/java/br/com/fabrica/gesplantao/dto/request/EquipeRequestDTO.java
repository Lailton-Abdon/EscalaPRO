package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class EquipeRequestDTO {

    @NotEmpty(message = "O campo de codigo da equipe não deve estar vazio")
    private String codigoEquipe;

    @NotNull(message = "O campo de quantidade de vagas não deve ser nulo")
    private Integer quantidadeVagas;
}
