package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.EquipeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.EquipeResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusInternoEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Equipe;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.repository.EquipeRepository;
import br.com.fabrica.gesplantao.repository.InternoRepository;
import br.com.fabrica.gesplantao.repository.RodizioRepository;
import br.com.fabrica.gesplantao.services.EquipeService;
import br.com.fabrica.gesplantao.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipeServiceImpl implements EquipeService {

    public static final int QUANTIDADE_VAGAS = 0;
    public static final int ABATIMENTO_ACRESCIMO_VAGA = 1;

    @Autowired
    private EquipeRepository equipeRepository;

    @Autowired
    private InternoRepository internoRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RodizioRepository rodizioRepository;

    @Autowired
    private UsuarioService usuarioService;


    public EquipeResponseDTO addInternoEquipe(Long idEquipe) {
        Long idInterno = usuarioService.retornaUsuarioAutenticado();
        Equipe equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Equipe não encontrada"));
        Interno interno = internoRepository.findById(idInterno)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        if (interno.getStatus().equals(StatusInternoEnum.INDISPONIVEL)) {
            throw new BadRequestException("Interno já possui uma equipe em atividade");
        }
        if (equipe.getQuantidadeVagas() == QUANTIDADE_VAGAS) {
            throw new BadRequestException("Não há mais vagas disponiveis");
        }
        equipe.getInternos().add(interno);
        Integer vagaAtualizada = equipe.getQuantidadeVagas() - ABATIMENTO_ACRESCIMO_VAGA;
        equipe.setQuantidadeVagas(vagaAtualizada);
        interno.getEquipe().add(equipe);
        interno.setStatus(StatusInternoEnum.INDISPONIVEL);
        equipeRepository.save(equipe);
        return modelMapper.map(equipe, EquipeResponseDTO.class);
    }

    public EquipeResponseDTO buscarId(Long id) {
        EquipeResponseDTO equipe = equipeRepository.findById(id)
                .map(equipe1 -> modelMapper.map(equipe1, EquipeResponseDTO.class))
                .orElseThrow(() -> new EquipeNaoEncontradaException("Equipe não encontrada"));
        return equipe;
    }

    public EquipeResponseDTO buscarQuantidade(Integer quant) {
        EquipeResponseDTO equipe = equipeRepository.findByQuantidadeVagas(quant)
                .map(equipe1 -> modelMapper.map(equipe1, EquipeResponseDTO.class))
                .orElseThrow(() -> new EquipeNaoEncontradaException("Equipe com quantidade " + quant + " não existe"));
        return equipe;
    }

    @Override
    public EquipeResponseDTO buscarCodigoEquipe(String codigoEquipe) {
        return equipeRepository.findByCodigoEquipe(codigoEquipe)
                .map(equipe -> modelMapper.map(equipe, EquipeResponseDTO.class))
                .orElseThrow(() -> new EquipeNaoEncontradaException("Equipe não encontrada"));
    }

    public List<EquipeResponseDTO> buscarDataCriaca(LocalDate date) {
        List<EquipeResponseDTO> equipeResponseDTOS = equipeRepository.findByDataCriacao(date)
                .stream().map(equipe -> modelMapper.map(equipe, EquipeResponseDTO.class)).collect(Collectors.toList());
        if (equipeResponseDTOS.isEmpty()) {
            throw new EquipeNaoEncontradaException("Não foram cadastras equipes nessa data");
        }
        return equipeResponseDTOS;
    }
    
    public void removerInterno(Long idEquipe, Long idInterno) {
        var equipe = equipeRepository.findById(idEquipe)
                .orElseThrow(() -> new EquipeNaoEncontradaException("Equipe não encontrada"));
        var interno = internoRepository.findById(idInterno)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));

        Integer vagaAtualizada = equipe.getQuantidadeVagas() + ABATIMENTO_ACRESCIMO_VAGA;
        equipe.setQuantidadeVagas(vagaAtualizada);
        interno.setStatus(StatusInternoEnum.DISPONIVEL);
        equipe.getInternos().remove(interno);
        equipeRepository.save(equipe);
    }

    private Equipe fromRequest(EquipeRequestDTO equipeRequestDTO) {
        Equipe equipe = new Equipe();
        equipe.setCodigoEquipe(equipeRequestDTO.getCodigoEquipe());
        equipe.setDataCriacao(LocalDate.now());
        equipe.setQuantidadeVagas(equipeRequestDTO.getQuantidadeVagas());
        return equipe;
    }

}
