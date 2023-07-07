package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class InternoRequestDTO {

    @Pattern(regexp = "\\([0-9]{2}\\) [0-9]{4,5}-[0-9]{4}", message = "Telefone inválido ((xx) xxxx-xxxx)")
    private String telefone;

    @Email(message = "Endereço de email inválido.")
    @NotEmpty(message = "O campo email não pode estar vazio.")
    private String email;

    @NotEmpty(message = "O campo do RGM não deve estar vazio")
    private String rgm;

    @NotEmpty(message = "O campo curso não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O curso deve conter apenas letras e espaços.")
    private String curso;

    @CPF(message = "CPF inválido")
    @NotEmpty(message = "O campo CPF não pode estar vazio")
    private String cpf;

    private String senha;
}
