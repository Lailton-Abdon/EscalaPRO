package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.CicloRequestDTO;
import br.com.fabrica.gesplantao.dto.request.EquipeRequestDTO;
import br.com.fabrica.gesplantao.dto.request.RodizioRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CicloResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import br.com.fabrica.gesplantao.exception.NotFoundException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.*;
import br.com.fabrica.gesplantao.repository.*;
import br.com.fabrica.gesplantao.services.CicloService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
@RequiredArgsConstructor
public class CicloServiceImpl implements CicloService {

    @Autowired
    private CicloRepository cicloRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private RodizioRepository rodizioRepository;

    @Autowired
    private PreceptorRepository preceptorRepository;

    @Autowired
    private LocalRepository localRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EquipeRepository equipeRepository;

    @Override
    public CicloResponseDTO register(CicloRequestDTO requestDTO) {
        Ciclo ciclo = modelMapper.map(requestDTO, Ciclo.class);
        List<Rodizio> rodizios = converterRodizioRequest(ciclo, requestDTO.getRodizio());
        calculaDuracaoCiclo(ciclo);
        ciclo.setRodizios(rodizios);
        cicloRepository.save(ciclo);
        rodizioRepository.saveAll(rodizios);
        return modelMapper.map(ciclo, CicloResponseDTO.class);
    }

    @Override
    public CicloResponseDTO buscarCicloPorId(Long id) {
        Ciclo ciclo = cicloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciclo não encontrado"));
        return modelMapper.map(ciclo, CicloResponseDTO.class);
    }

    @Override
    public CicloResponseDTO buscarCicloPorCodigo(String codigo) {
        Ciclo ciclo = cicloRepository.findByCodigo(codigo);
        if (ciclo == null) {
            throw new NotFoundException("Ciclo não encontrado");
        }
        return convertToResponseDTO(ciclo);
    }

    private CicloResponseDTO convertToResponseDTO(Ciclo ciclo) {
        return new CicloResponseDTO(ciclo.getId(), ciclo.getCodigo(), ciclo.getInicioCiclo(), ciclo.getFimCiclo());
    }

    @Override
    public List<CicloResponseDTO> listarTodosCiclos() {
        List<Ciclo> ciclos = cicloRepository.findAll();
        return ciclos.stream()
                .map(ciclo -> modelMapper.map(ciclo, CicloResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CicloResponseDTO atualizarCiclo(Long id, CicloRequestDTO requestDTO) {
        Ciclo ciclo = cicloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciclo não encontrado"));
        ciclo.setCodigo(requestDTO.getCodigo());
        ciclo.setInicioCiclo(requestDTO.getInicioCiclo());
        ciclo.setFimCiclo(requestDTO.getFimCiclo());
        Ciclo cicloAtualizado = cicloRepository.save(ciclo);
        return modelMapper.map(cicloAtualizado, CicloResponseDTO.class);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return cicloRepository.existsByCodigo(codigo);
    }

    @Override
    public String delete(Long id) {
        cicloRepository.deleteById(id);
        return "Ciclo id: " + id + " deletado com sucesso";
    }

    @Override
    public String deleteByCodigo(String codigo) {
        Ciclo ciclo = cicloRepository.findByCodigo(codigo);
        if (ciclo == null) {
            throw new NotFoundException("Ciclo não encontrado");
        }
        cicloRepository.delete(ciclo);
        return "Ciclo excluído com sucesso";
    }

    @Override
    public CicloResponseDTO atualizarCicloPorCodigo(String codigo, CicloRequestDTO requestDTO) {
        Ciclo cicloExistente = cicloRepository.findByCodigo(codigo);
        if (cicloExistente == null) {
            throw new NotFoundException("Ciclo não encontrado");
        }
        cicloExistente.setCodigo(requestDTO.getCodigo());
        cicloExistente.setInicioCiclo(requestDTO.getInicioCiclo());
        cicloExistente.setFimCiclo(requestDTO.getFimCiclo());
        Ciclo cicloAtualizado = cicloRepository.save(cicloExistente);

        CicloResponseDTO responseDTO = new CicloResponseDTO();
        responseDTO.setCodigo(cicloAtualizado.getCodigo());
        responseDTO.setInicioCiclo(cicloAtualizado.getInicioCiclo());
        responseDTO.setFimCiclo(cicloAtualizado.getFimCiclo());
        return responseDTO;
    }

    private List<Rodizio> converterRodizioRequest(Ciclo ciclo, List<RodizioRequestDTO> rodizios) {
        return rodizios.stream().map(rodizioRequestDTO -> {
            List<Especialidade> especialidades = obterListaEspecialidade(rodizioRequestDTO.getEspecialidadeIds());
            List<Preceptor> preceptores = obterPreceptorPorEspecialidade(especialidades);
            Local local = localRepository.findById(rodizioRequestDTO.getLocaisIds()).
                    orElseThrow(()-> new RuntimeException("Local não encontrado"));

            Rodizio rodizio = new Rodizio();
            rodizio.setDescricao(rodizioRequestDTO.getNome());
            rodizio.setEspecialidade(especialidades);
            rodizio.setLocal(local);
            rodizio.setPreceptor(preceptores);
            rodizio.setCiclo(ciclo);
            rodizio.setStatusRodizio(StatusRodizioEnum.EM_ANDAMENTO);

            Equipe equipe = criarEquipe(rodizioRequestDTO.getEquipe());
            rodizio.setEquipe(equipe);
            equipe.getRodizio().add(rodizio);
            equipeRepository.save(equipe);
            return rodizio;
        }).collect(Collectors.toList());
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

    private void calculaDuracaoCiclo(Ciclo ciclo) {
        Period period = Period.between(ciclo.getInicioCiclo().withDayOfMonth(1), ciclo.getFimCiclo().withDayOfMonth(1));
        Integer duracao = period.getYears() * 12 + period.getMonths();
        ciclo.setDuracaoCiclo(duracao + " meses");
    }

    private Equipe criarEquipe(EquipeRequestDTO equipeDTO) {
        Equipe equipe = modelMapper.map(equipeDTO, Equipe.class);
        equipe.setCodigoEquipe(equipeDTO.getCodigoEquipe());
        equipe.setDataCriacao(LocalDate.now());
        equipe.setQuantidadeVagas(equipeDTO.getQuantidadeVagas());
        return equipe;
    }
}


