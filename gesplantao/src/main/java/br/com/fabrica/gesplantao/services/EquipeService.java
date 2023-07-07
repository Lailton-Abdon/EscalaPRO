package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.EquipeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.EquipeResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface EquipeService {

    EquipeResponseDTO addInternoEquipe(Long idEquipe);

    EquipeResponseDTO buscarId(Long id);

    EquipeResponseDTO buscarQuantidade(Integer quant);

    EquipeResponseDTO buscarCodigoEquipe(String codigoEquipe);

    List<EquipeResponseDTO> buscarDataCriaca(LocalDate date);

    void removerInterno(Long idEquipe, Long idInterno);
}
