package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.CicloRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CicloResponseDTO;

import java.util.List;

public interface CicloService {
    CicloResponseDTO register(CicloRequestDTO cicloDTO);

    CicloResponseDTO buscarCicloPorId(Long id);

    CicloResponseDTO buscarCicloPorCodigo(String codigo);

    List<CicloResponseDTO> listarTodosCiclos();

    CicloResponseDTO atualizarCiclo(Long id, CicloRequestDTO requestDTO);
    CicloResponseDTO atualizarCicloPorCodigo(String codigo, CicloRequestDTO requestDTO);


    boolean existsByCodigo(String codigo);

    String delete(Long id);

    String deleteByCodigo(String codigo);

}
