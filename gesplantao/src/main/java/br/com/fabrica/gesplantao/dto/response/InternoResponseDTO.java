package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternoResponseDTO {

    private String nome;

    private String telefone;

    private String email;

    private Integer rgm;

    private String curso;

    private Integer quantfalta;

    private Integer quantAtraso;

    private Integer quantPlantao;

    private Integer quantPresenca;

    private PerfilEnum perfil;

}
