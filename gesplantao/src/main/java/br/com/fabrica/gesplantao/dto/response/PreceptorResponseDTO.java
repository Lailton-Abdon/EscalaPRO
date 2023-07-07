package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.model.Especialidade;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PreceptorResponseDTO {

    private String nome;

    private String email;

    private String crm;

    private String telefone;

//    private LocalDate dataCriacao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EspecialidadeResponseDTO especialidade;

    private PerfilEnum perfil;
}
