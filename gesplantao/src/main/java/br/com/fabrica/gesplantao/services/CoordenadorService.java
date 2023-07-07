package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.CoordenadorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CoordenadorResponseDTO;

import java.util.List;

public interface CoordenadorService {
    CoordenadorResponseDTO cadastrarCoordenador(CoordenadorRequestDTO coordenadorRequestDTO);
    CoordenadorResponseDTO buscarId(Long id);
    List<CoordenadorResponseDTO> buscarNome(String nome);
    CoordenadorResponseDTO buscarMatricula(String matricula);
    List<CoordenadorResponseDTO> listarCoordenador();
    void removerCoordenador(Long id);
}
