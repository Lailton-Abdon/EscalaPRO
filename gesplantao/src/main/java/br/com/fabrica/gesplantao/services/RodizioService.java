package br.com.fabrica.gesplantao.services;


import br.com.fabrica.gesplantao.dto.request.RodizioRequestDTO;
import br.com.fabrica.gesplantao.dto.response.RodizioResponseDTO;
import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;

import java.util.List;

public interface RodizioService {

    RodizioResponseDTO findByRodizioId(Long id);

    List<RodizioResponseDTO> getAllRodizios();

    RodizioResponseDTO updateRodizio(Long id, RodizioRequestDTO rodizioRequestDTO);

    void deleteRodizio(Long id);

    List<RodizioResponseDTO> findByDescricao(String descricao);

    void deleteRodizioByDescricao(String descricao);

    List<RodizioResponseDTO> buscarRodizioPreceptor(Preceptor preceptor);

    List<RodizioResponseDTO> buscarRodizioEspecialidade(Especialidade especialidade);

    RodizioResponseDTO addEquipeRodizio(Long idRodizio, Long idEquipe);


}
