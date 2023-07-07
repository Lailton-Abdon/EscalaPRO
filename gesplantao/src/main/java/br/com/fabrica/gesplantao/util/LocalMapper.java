package br.com.fabrica.gesplantao.util;

import br.com.fabrica.gesplantao.dto.request.LocalRequestDTO;
import br.com.fabrica.gesplantao.dto.response.LocalResponseDTO;
import br.com.fabrica.gesplantao.model.Local;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class LocalMapper {

    public Local toLocal(LocalRequestDTO localDTO){
        return Local.builder()
                .nome(localDTO.getNome())
                .siglaLocal(localDTO.getSiglaLocal())
                .endereco(localDTO.getEndereco())
                .build();
    }



    public void updateLocalData(Local local, LocalRequestDTO localDTO){
        local.setNome(localDTO.getNome());
        local.setSiglaLocal(localDTO.getSiglaLocal());
        local.setEndereco(localDTO.getEndereco());
    }


}
