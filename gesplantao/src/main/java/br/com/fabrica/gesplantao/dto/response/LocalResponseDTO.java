package br.com.fabrica.gesplantao.dto.response;

import br.com.fabrica.gesplantao.model.Local;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocalResponseDTO {
    private Long id;
    private String nome;
    private String siglaLocal;
    private String endereco;

}

