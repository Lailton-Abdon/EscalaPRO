package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class PreceptorRequestDTO {

    @NotEmpty(message = "O campo nome do preceptor não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome do preceptor deve conter apenas letras e espaços.")
    private String nome;

    @Email(message = "Endereço de email inválido.")
    @NotEmpty(message = "O campo email não pode estar vazio.")
    private String email;

    @NotEmpty(message = "O campo CRM não deve estar vazio")
    private String crm;

    @Pattern(regexp = "\\([0-9]{2}\\) [0-9]{4,5}-[0-9]{4}", message = "Telefone inválido ((xx) xxxx-xxxx)")
    @NotEmpty(message = "O campo telefone não pode estar vazio.")
    private String telefone;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[@#$%^&+=_-])(?=\\S+$).{8,}$",
            message = "A senha deve ter pelo menos 8 caracteres, um número e um caractere especial " +
                    "e não deve permitir espaços em branco no início ou no final.")
    private String senha;

}
