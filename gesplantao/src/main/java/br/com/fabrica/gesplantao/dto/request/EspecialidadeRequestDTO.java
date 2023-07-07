package br.com.fabrica.gesplantao.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeRequestDTO {

    @NotEmpty(message = "O campo nome da especialidade não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome da especialidade deve conter apenas letras e espaços.")
    private String nome;

}
