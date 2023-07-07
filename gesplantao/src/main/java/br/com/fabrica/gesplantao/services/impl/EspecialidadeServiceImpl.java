package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.EspecialidadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.EspecialidadeResponseDTO;
import br.com.fabrica.gesplantao.dto.response.PreceptorResponseDTO;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.repository.EspecialidadeRepository;
import br.com.fabrica.gesplantao.repository.PreceptorRepository;
import br.com.fabrica.gesplantao.services.EspecialidadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialidadeServiceImpl implements EspecialidadeService {

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private PreceptorRepository preceptorRepository;

    @Autowired
    private ModelMapper modelMapper;

    //método para converter uma lista de especialidades em uma lista de especialidades response
    private List<EspecialidadeResponseDTO> mapToListResponseDTO(List<Especialidade> especialidades) {
        return especialidades.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    //método para converter especialidade em  response
    private EspecialidadeResponseDTO mapToResponseDTO(Especialidade especialidade) {
        return modelMapper.map(especialidade, EspecialidadeResponseDTO.class);
    }

    //método para converter request em especialidade
    private Especialidade mapToEntity(EspecialidadeRequestDTO especialidadeRequestDTO) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeRequestDTO.getNome());
        return especialidade;
    }

    @Override
    public List<EspecialidadeResponseDTO> buscarTodasEspecialidades() {
        List<Especialidade> especialidades = especialidadeRepository.findAll();
        return mapToListResponseDTO(especialidades);
    }

    @Override
    public List<EspecialidadeResponseDTO> buscarPorNome(String nome) {
        List<Especialidade> especialidades = especialidadeRepository.findByNomeContaining(nome);
        return mapToListResponseDTO(especialidades);
    }

    @Override
    public EspecialidadeResponseDTO buscarPorId(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada!"));
        return mapToResponseDTO(especialidade);
    }

    @Override
    public EspecialidadeResponseDTO criarEspecialidade(EspecialidadeRequestDTO especialidadeRequestDTO) {
        Especialidade especialidade = mapToEntity(especialidadeRequestDTO);
        especialidadeRepository.save(especialidade);
        return mapToResponseDTO(especialidade);
    }

    @Override
    public EspecialidadeResponseDTO atualizarEspecialidade(Long id, EspecialidadeRequestDTO especialidadeRequestDTO) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Especialidade não encontrada!"));

        especialidade.setNome(especialidadeRequestDTO.getNome());
        especialidade = especialidadeRepository.save(especialidade);

        return mapToResponseDTO(especialidade);
    }

    @Override
    public void excluirEspecialidade(Long id) {
        especialidadeRepository.deleteById(id);
    }

    public void addPreceptor(Long idEspecialidade, Long idPreceptor) {
        Especialidade especialidade = especialidadeRepository.findById(idEspecialidade).orElseThrow(() -> new RuntimeException("Não encontrado"));
        Preceptor preceptor = preceptorRepository.findById(idPreceptor).orElseThrow(() -> new RuntimeException(""));
        if (preceptor.getEspecialidade() != null) {
            throw new BadRequestException("Preceptor já possui especialidade");
        }
        if(especialidade.getPreceptor() != null){
            throw new BadRequestException("Especialidade já possui um Preceptor");
        }
        preceptor.setEspecialidade(especialidade);
        especialidadeRepository.save(especialidade);
    }

}

