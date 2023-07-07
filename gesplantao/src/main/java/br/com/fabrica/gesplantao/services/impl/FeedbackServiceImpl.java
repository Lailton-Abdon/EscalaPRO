package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.FeedbackRequestDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackInternoResponseDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackPreceptorResponseDTO;
import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Feedback;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.repository.FeedbackRepository;
import br.com.fabrica.gesplantao.repository.InternoRepository;
import br.com.fabrica.gesplantao.repository.PreceptorRepository;
import br.com.fabrica.gesplantao.services.FeedbackService;
import br.com.fabrica.gesplantao.services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PreceptorRepository preceptorRepository;

    @Autowired
    private InternoRepository internoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UsuarioService usuarioService;

    public FeedbackPreceptorResponseDTO addFeedbackPreceptor(FeedbackRequestDTO feedbackRequestDTO) {
        Long id = usuarioService.retornaUsuarioAutenticado();
        Feedback feedback = fromRequestFeedback(feedbackRequestDTO);
        Interno interno = internoRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
        Preceptor preceptor = preceptorRepository.findById(feedbackRequestDTO.getIdPreceptorOrInterno())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Preceptor não encontrado"));
        boolean isRodizioCompativel = interno.getEquipe().stream()
                .flatMap(equipe -> equipe.getRodizio().stream())
                .anyMatch(rodizioEquipe ->
                        preceptor.getRodizio().stream()
                                .anyMatch(rodizioPreceptor ->
                                        rodizioPreceptor.getId().equals(rodizioEquipe.getId())
                                                && rodizioPreceptor.getStatusRodizio().equals(StatusRodizioEnum.EM_ANDAMENTO)));
        if (!isRodizioCompativel) {
            throw new BadRequestException("O Preceptor não faz parte do seu rodizio ou o rodizio está concluído");
        }
        if (!feedback.isAnonimo()) {
            feedback.setAutor(interno.getNome());
        } else {
            feedback.setAutor(null);
        }
        preceptor.getFeedback().add(feedback);
        feedback.setPreceptor(preceptor);
        feedback.setInterno(interno);
        feedbackRepository.save(feedback);
        return modelMapper.map(feedback, FeedbackPreceptorResponseDTO.class);
    }

    public FeedbackInternoResponseDTO addFeedbackInterno(FeedbackRequestDTO feedbackRequestDTO) {
        Long id = usuarioService.retornaUsuarioAutenticado();
        Feedback feedback = fromRequestFeedback(feedbackRequestDTO);
        feedback.setAnonimo(false);
        Preceptor preceptor = preceptorRepository.findById(id)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Preceptor não encontrado"));
        Interno interno = internoRepository.findById(feedbackRequestDTO.getIdPreceptorOrInterno())
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
        boolean isRodizioCompativel = interno.getEquipe().stream()
                .flatMap(equipe -> equipe.getRodizio().stream())
                .anyMatch(rodizioEquipe -> preceptor.getRodizio().stream()
                        .anyMatch(rodizioPreceptor -> rodizioPreceptor.getId().equals(rodizioEquipe.getId())
                                && rodizioPreceptor.getStatusRodizio().equals(StatusRodizioEnum.EM_ANDAMENTO)));
        if (!isRodizioCompativel) {
            throw new BadRequestException("Interno não faz parte de seu rodizio");
        }

        interno.getFeedback().add(feedback);
        feedback.setAutor(preceptor.getNome());
        feedback.setInterno(interno);
        feedback.setPreceptor(preceptor);
        feedbackRepository.save(feedback);
        return modelMapper.map(feedback, FeedbackInternoResponseDTO.class);
    }

    public List<FeedbackPreceptorResponseDTO> buscarFeedbackPreceptor(Long idPreceptor) {
        Preceptor preceptorEncontrado = preceptorRepository.findById(idPreceptor)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Preceptor não encontrado"));
        List<FeedbackPreceptorResponseDTO> listFeedback = feedbackRepository.findByPreceptor(preceptorEncontrado).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackPreceptorResponseDTO.class)).collect(Collectors.toList());
        if (listFeedback.isEmpty()) {
            throw new EquipeNaoEncontradaException("Preceptor não possui feedback's");
        }
        return listFeedback;
    }

    public List<FeedbackInternoResponseDTO> buscarFeedbackInterno(Long idInterno) {
        Interno interno = internoRepository.findById(idInterno)
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Interno não encontrado"));
        List<FeedbackInternoResponseDTO> listFeedback = feedbackRepository.findByInterno(interno).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackInternoResponseDTO.class)).collect(Collectors.toList());
        if (listFeedback.isEmpty()) {
            throw new EquipeNaoEncontradaException("Interno não possui feedback's");
        }
        return listFeedback;
    }

    public List<FeedbackPreceptorResponseDTO> buscarAutor(String autor) {
        List<FeedbackPreceptorResponseDTO> listFeedback = feedbackRepository.findByAutor(autor).stream()
                .map(feedback -> modelMapper.map(feedback, FeedbackPreceptorResponseDTO.class)).collect(Collectors.toList());
        if (listFeedback.isEmpty()) {
            throw new EquipeNaoEncontradaException("Não existem feedback's com esse autor");
        }
        return listFeedback;
    }

    private Feedback fromRequestFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        Feedback feedback = new Feedback();
        feedback.setDescricao(feedbackRequestDTO.getDescricao());
        feedback.setAnonimo(feedbackRequestDTO.isAnonimo());
        feedback.setDataFeedback(LocalDate.now());
        return feedback;
    }
}
