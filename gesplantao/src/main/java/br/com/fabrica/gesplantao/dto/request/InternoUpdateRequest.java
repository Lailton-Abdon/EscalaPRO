package br.com.fabrica.gesplantao.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InternoUpdateRequest {

    private String nome;

    private String telefone;

    private String email;

    private String curso;
}
