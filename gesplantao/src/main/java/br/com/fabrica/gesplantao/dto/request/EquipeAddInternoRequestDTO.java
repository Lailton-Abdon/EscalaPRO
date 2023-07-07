package br.com.fabrica.gesplantao.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EquipeAddInternoRequestDTO {

    private List<Long> idInterno;
}
