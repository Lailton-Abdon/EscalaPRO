package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.InternoRequestDTO;
import br.com.fabrica.gesplantao.dto.request.InternoUpdateRequest;
import br.com.fabrica.gesplantao.dto.response.InternoResponseDTO;

import java.util.List;

public interface InternoService {

    List<InternoResponseDTO> listarTodos();

    InternoResponseDTO buscarPorId(Long id);

    InternoResponseDTO cadastrarInterno(InternoRequestDTO internoRequestDTO);

    InternoResponseDTO atualizarInterno(Long id, InternoUpdateRequest internoUpdateRequest);

    void excluir(Long internoId);

    void addInterno(String arquivo);

}
