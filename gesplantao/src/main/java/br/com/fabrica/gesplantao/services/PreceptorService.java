package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.PreceptorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PreceptorResponseDTO;

import java.util.List;

public interface PreceptorService {

    PreceptorResponseDTO cadastrarPreceptor(PreceptorRequestDTO preceptorRequestDTO);

    PreceptorResponseDTO buscarId(Long id);

    PreceptorResponseDTO buscarMatricula(String matricula);

    List<PreceptorResponseDTO> buscarNome(String nome);

    List<PreceptorResponseDTO> listarPreceptor();

    void removerPreceptor(Long id);
}
