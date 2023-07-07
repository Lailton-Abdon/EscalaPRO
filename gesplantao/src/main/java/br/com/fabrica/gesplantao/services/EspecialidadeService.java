package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.EspecialidadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.EspecialidadeResponseDTO;

import java.util.List;

public interface EspecialidadeService {

    List<EspecialidadeResponseDTO> buscarTodasEspecialidades();
    List<EspecialidadeResponseDTO> buscarPorNome(String nome);
    EspecialidadeResponseDTO buscarPorId(Long id);
    EspecialidadeResponseDTO criarEspecialidade(EspecialidadeRequestDTO especialidadeRequestDTO);
    EspecialidadeResponseDTO atualizarEspecialidade(Long id, EspecialidadeRequestDTO especialidadeRequestDTO);
    void excluirEspecialidade(Long id);
    void addPreceptor(Long idEspecialidade, Long idPreceptor);
}
