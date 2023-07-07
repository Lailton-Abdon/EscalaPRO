package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.RodizioRequestDTO;
import br.com.fabrica.gesplantao.dto.response.RodizioResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Equipe;
import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.model.Rodizio;
import br.com.fabrica.gesplantao.repository.EquipeRepository;
import br.com.fabrica.gesplantao.repository.EspecialidadeRepository;
import br.com.fabrica.gesplantao.repository.PreceptorRepository;
import br.com.fabrica.gesplantao.repository.RodizioRepository;
import br.com.fabrica.gesplantao.services.RodizioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RodizioServiceImpl implements RodizioService {

    @Autowired
    private final RodizioRepository rodizioRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private PreceptorRepository preceptorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public RodizioServiceImpl(RodizioRepository rodizioRepository) {
        this.rodizioRepository = rodizioRepository;
    }

    @Override
    public RodizioResponseDTO findByRodizioId(Long id) {
        Rodizio rodizio = rodizioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rodizio not found with id: " + id));
        return modelMapper.map(rodizio, RodizioResponseDTO.class);
    }

    @Override
    public List<RodizioResponseDTO> getAllRodizios() {
        List<Rodizio> rodizios = rodizioRepository.findAll();
        return rodizios.stream()
                .map(rodizio -> modelMapper.map(rodizio, RodizioResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public RodizioResponseDTO updateRodizio(Long id, RodizioRequestDTO rodizioRequestDTO) {
        Rodizio existingRodizio = rodizioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Rodizio not found with id: " + id));
        BeanUtils.copyProperties(rodizioRequestDTO, existingRodizio);
        Rodizio updatedRodizio = rodizioRepository.save(existingRodizio);
        return modelMapper.map(updatedRodizio, RodizioResponseDTO.class);
    }

    @Override
    public void deleteRodizio(Long id) {
        rodizioRepository.deleteById(id);
    }

    @Override
    public List<RodizioResponseDTO> findByDescricao(String descricao) {
        List<Rodizio> rodizios = rodizioRepository.findByDescricao(descricao); //criar no repositório
        return rodizios.stream()
                .map(rodizio -> modelMapper.map(rodizio, RodizioResponseDTO.class))
                .collect(Collectors.toList());

    }

    public List<RodizioResponseDTO> buscarRodizioPreceptor(Preceptor preceptor) {
        if (preceptor == null) {
            throw new UsuarioNaoEncontradoException("Preceptor não encontrado");
        }
        List<RodizioResponseDTO> rodizioResponseDTOS = rodizioRepository.findByPreceptor(preceptor).stream()
                .map(rodizio -> modelMapper.map(rodizio, RodizioResponseDTO.class)).collect(Collectors.toList());
        if (rodizioResponseDTOS.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Preceptor sem rodizios");
        }
        return rodizioResponseDTOS;
    }

    public List<RodizioResponseDTO> buscarRodizioEspecialidade(Especialidade especialidade) {
        if (especialidade == null) {
            throw new UsuarioNaoEncontradoException("Especialidade não encontrada");
        }
        List<RodizioResponseDTO> rodizioResponseDTOS = rodizioRepository.findByEspecialidade(especialidade).stream()
                .map(rodizio -> modelMapper.map(rodizio, RodizioResponseDTO.class)).collect(Collectors.toList());
        if (rodizioResponseDTOS.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Preceptor sem rodizios");
        }
        return rodizioResponseDTOS;
    }

    public RodizioResponseDTO addEquipeRodizio(Long idRodizio, Long idEquipe){
        Rodizio rodizio = rodizioRepository.findById(idRodizio)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Rodizio não encontrado"));
        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Equipe não encontrada"));
        if(equipe.getRodizio().stream().anyMatch(rodizio1 -> rodizio1.getStatusRodizio().equals(StatusRodizioEnum.EM_ANDAMENTO))){
            throw new BadRequestException("Equipe já pertence a um rodizio em andamento");
        }
        if(rodizio.getEquipe() != null){
            throw new BadRequestException("Rodizio com equipe já atribuida");
        }
        rodizio.setEquipe(equipe);
        equipe.getRodizio().add(rodizio);
        rodizioRepository.save(rodizio);
        return modelMapper.map(rodizio, RodizioResponseDTO.class);
    }

    @Override
    public void deleteRodizioByDescricao(String descricao) {
        List<Rodizio> rodizios = rodizioRepository.findByDescricao(descricao);
        rodizioRepository.deleteAll(rodizios);
    }

    private RodizioResponseDTO convertToResponseDTO(Rodizio rodizio) {
        RodizioResponseDTO responseDTO = new RodizioResponseDTO();
        BeanUtils.copyProperties(rodizio, responseDTO);
        return responseDTO;
    }

    private List<Especialidade> obterListaEspecialidade(List<Long> idEspecialidade) {
        return idEspecialidade.stream().map(id ->
                especialidadeRepository.findById(id).orElseThrow(() -> new RuntimeException(""))).collect(Collectors.toList());
    }

    private List<Preceptor> obterPreceptorPorEspecialidade(List<Especialidade> especialidades) {
        List<Preceptor> preceptores = new ArrayList<>();
        for (Especialidade especialidade : especialidades) {
            List<Preceptor> preceptoresPorEspecialidade = preceptorRepository.findByEspecialidade(especialidade);
            if (preceptoresPorEspecialidade.isEmpty()) {
                throw new UsuarioNaoEncontradoException("Não existe preceptor com especialidade em " + especialidade.getNome());
            }
            preceptores.addAll(preceptoresPorEspecialidade);
        }
        return preceptores;
    }
}
