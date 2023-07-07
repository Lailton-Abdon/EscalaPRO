package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.AtividadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.AtividadeResponseDTO;

import java.util.List;

public interface AtividadeService {

    AtividadeResponseDTO cadastrarAtividade(AtividadeRequestDTO atividadeRequestDTO);

    List<AtividadeResponseDTO> listarAtividade();
}
