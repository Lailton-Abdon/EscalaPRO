package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class RodizioRequestDTO {

    @NotEmpty(message = "O campo nome do rodizio não deve estar vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s]+$", message = "O nome do rodizio deve conter apenas letras e espaços.")
    private String nome;

    private List<Long> especialidadeIds;

    @NotNull(message = "O campo local não pode estar vazio.")
    private Long locaisIds;

    private EquipeRequestDTO equipe;
 }
