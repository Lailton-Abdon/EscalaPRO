package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.LocalRequestDTO;
import br.com.fabrica.gesplantao.dto.response.LocalResponseDTO;

import java.util.List;

public interface LocalService {
    LocalResponseDTO findById(Long Id);

    LocalResponseDTO findBySigla(String siglaLocal);

    List<LocalResponseDTO> findAll();


    LocalResponseDTO register(LocalRequestDTO localDTO);

    LocalResponseDTO update(Long id, LocalRequestDTO localDTO);

    LocalResponseDTO updateBySiglaLocal(String siglaLocal, LocalRequestDTO localDTO);

    String delete(Long id);

    String delete(String siglaLocal);

    LocalResponseDTO findLocalBySiglaLocal(String siglaLocal);

    boolean existsBySiglaLocal(String siglaLocal);

    boolean existsById(Long id);

}
