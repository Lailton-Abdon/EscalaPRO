package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class CoordenadorResponseDTO {

    private String nome;
    private String email;
    private String crm;
    private String telefone;
    private LocalDate dataCriacao;
    private PerfilEnum perfil;
}
