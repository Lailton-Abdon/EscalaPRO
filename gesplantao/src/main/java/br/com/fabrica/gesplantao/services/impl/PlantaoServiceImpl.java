package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.PlantaoRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PlantaoResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusPlantaoEnum;
import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EntidadeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.model.Atividade;
import br.com.fabrica.gesplantao.model.Plantao;
import br.com.fabrica.gesplantao.repository.AtividadeRepository;
import br.com.fabrica.gesplantao.repository.PlantaoRepository;
import br.com.fabrica.gesplantao.repository.RodizioRepository;
import br.com.fabrica.gesplantao.services.PlantaoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantaoServiceImpl implements PlantaoService {

    public static final int ACRESCIMO_PLANTAO = 1;
    public static final int DIA = 1;

    @Autowired
    private PlantaoRepository plantaoRepository;

    @Autowired
    private RodizioRepository rodizioRepository;

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PlantaoResponseDTO addPlantaoNoRodizio(Long idRodizio, PlantaoRequestDTO plantaoRequestDTO) {
        var plantao = fromRequest(plantaoRequestDTO);
        var atividades = obterListaAtividade(plantaoRequestDTO.getIdAtividades());
        var rodizio = rodizioRepository.findById(idRodizio)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Rodizio não encontrado"));
        if (rodizio.getStatusRodizio().equals(StatusRodizioEnum.CONCLUIDO)) {
            throw new BadRequestException("O rodizio selecionado já foi concluido");
        }
        rodizio.getPlantao().add(plantao);
        plantao.setRodizio(rodizio);
        plantao.setAtividades(atividades);
        calcularDataFimPlantao(plantao);
        rodizio.setQuantPlantao(rodizio.getQuantPlantao() + ACRESCIMO_PLANTAO);
        rodizioRepository.save(rodizio);
        plantaoRepository.save(plantao);
        return modelMapper.map(plantao, PlantaoResponseDTO.class);
    }

    public PlantaoResponseDTO buscarId(Long idPlantao) {
        return plantaoRepository.findById(idPlantao).map(plantao -> modelMapper.map(plantao, PlantaoResponseDTO.class))
                .orElseThrow(() -> new EquipeNaoEncontradaException("Plantão não encontrado"));
    }

    public List<PlantaoResponseDTO> buscarData(LocalDate dataInicioPlantao) {
        List<PlantaoResponseDTO> listPlantao = plantaoRepository.findByDataInicioPlantao(dataInicioPlantao).stream()
                .map(plantao -> modelMapper.map(plantao, PlantaoResponseDTO.class)).collect(Collectors.toList());
        if (listPlantao.isEmpty()) {
            throw new EquipeNaoEncontradaException("Não existem plantões para a data informada");
        }
        return listPlantao;
    }

    public List<PlantaoResponseDTO> buscarStatusIniciar() {
        List<PlantaoResponseDTO> listPlantao = plantaoRepository.findAll().stream()
                .filter(plantao -> plantao.getStatus().equals(StatusPlantaoEnum.A_INICIAR))
                .map(plantao -> modelMapper.map(plantao, PlantaoResponseDTO.class)).collect(Collectors.toList());
        if (listPlantao.isEmpty()) {
            throw new EquipeNaoEncontradaException("No momento não tem plantões a iniciar");
        }
        return listPlantao;
    }

    public List<PlantaoResponseDTO> buscarStatusEmAndamento() {
        List<PlantaoResponseDTO> listPlantao = plantaoRepository.findAll().stream()
                .filter(plantao -> plantao.getStatus().equals(StatusPlantaoEnum.EM_ANDAMENTO))
                .map(plantao -> modelMapper.map(plantao, PlantaoResponseDTO.class)).collect(Collectors.toList());
        if (listPlantao.isEmpty()) {
            throw new EquipeNaoEncontradaException("No momento não tem plantões em andamento");
        }
        return listPlantao;
    }

    public List<PlantaoResponseDTO> buscarStatusConcluido() {
        List<PlantaoResponseDTO> listPlantao = plantaoRepository.findAll().stream()
                .filter(plantao -> plantao.getStatus().equals(StatusPlantaoEnum.CONCLUIDO))
                .map(plantao -> modelMapper.map(plantao, PlantaoResponseDTO.class)).collect(Collectors.toList());
        if (listPlantao.isEmpty()) {
            throw new EquipeNaoEncontradaException("No momento não tem plantões concluídos");
        }
        return listPlantao;
    }

    public PlantaoResponseDTO atualizarPlantao(Long idPlantao, PlantaoRequestDTO plantaoRequestDTO) {
        return plantaoRepository.findById(idPlantao).map(plantao -> {
            plantao.setCodigoPlantao(plantaoRequestDTO.getCodigoPlantao());
            plantao.setDataInicioPlantao(plantaoRequestDTO.getDataInicioPlantao());
            plantao.setHoraInicio(plantaoRequestDTO.getHoraInicio());
            plantao.setHoraFim(plantaoRequestDTO.getHoraFim());
            calcularDataFimPlantao(plantao);
            plantaoRepository.save(plantao);
            return modelMapper.map(plantao, PlantaoResponseDTO.class);
        }).orElseThrow(() -> new EquipeNaoEncontradaException("Plantão não encontrado"));
    }

    public void removerPlantao(Long idPlantao) {
        var plantao = plantaoRepository.findById(idPlantao)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Plantão não encontrado"));
        plantaoRepository.delete(plantao);
    }

    public List<Atividade> obterListaAtividade(List<Long> idAtividade) {
        return idAtividade.stream().map(atividade -> atividadeRepository.findById(atividade)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Atividade não encontrada"))).collect(Collectors.toList());
    }

    public static Plantao fromRequest(PlantaoRequestDTO plantaoRequestDTO){
        var plantao = new Plantao();
        plantao.setHoraInicio(plantaoRequestDTO.getHoraInicio());
        plantao.setCodigoPlantao(plantaoRequestDTO.getCodigoPlantao());
        plantao.setHoraFim(plantaoRequestDTO.getHoraFim());
        plantao.setStatus(StatusPlantaoEnum.A_INICIAR);
        plantao.setDataInicioPlantao(plantaoRequestDTO.getDataInicioPlantao());
        plantao.setDataFimPlantao(plantaoRequestDTO.getDataInicioPlantao());
        return plantao;
    }

    public void calcularDataFimPlantao(Plantao plantao) {
        LocalTime horaInicio = plantao.getHoraInicio();
        LocalTime horaFim = plantao.getHoraFim();
        LocalDate dataInicioPlantao = plantao.getDataInicioPlantao();

        if (horaFim.isBefore(horaInicio)) {
            plantao.setDataFimPlantao(dataInicioPlantao.plusDays(DIA));
        } else {
            plantao.setDataFimPlantao(dataInicioPlantao);
        }
    }
}



