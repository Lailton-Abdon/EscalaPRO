package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.PlantaoRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PlantaoResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface PlantaoService {

    PlantaoResponseDTO addPlantaoNoRodizio(Long idRodizio, PlantaoRequestDTO plantaoRequestDTO);

    PlantaoResponseDTO atualizarPlantao(Long idPlantao, PlantaoRequestDTO plantaoRequestDTO);

    void removerPlantao(Long idPlantao);

    PlantaoResponseDTO buscarId(Long idPlantao);

    List<PlantaoResponseDTO> buscarData(LocalDate dataInicioPlantao);

    List<PlantaoResponseDTO> buscarStatusIniciar();

    List<PlantaoResponseDTO> buscarStatusEmAndamento();

    List<PlantaoResponseDTO> buscarStatusConcluido();
}
