package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class LocalRequestDTO {

    @NotEmpty(message = "O campo nome do local não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome do local deve conter apenas letras e espaços.")
    private String nome;

    @NotEmpty(message = "O campo sigla do local não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ]{2,}$", message = "O campo deve conter apenas letras e ter no mínimo duas letras.")
    private String siglaLocal;

    @NotEmpty(message = "O campo endereço não deve estar vazio")
    @Pattern(regexp = "^[A-Za-z0-9À-ÿ\\s.,\\-/]+$", message = "O endereço deve conter apenas letras, " +
            "números e caracteres especiais válidos.")
    private String endereco;


//    private Rodizio rodizio;
//
//    private List<Especialidade> especialidade;

}
