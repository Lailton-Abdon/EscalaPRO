package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.FeedbackRequestDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackInternoResponseDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackPreceptorResponseDTO;
import br.com.fabrica.gesplantao.services.FeedbackService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/preceptor/cadastrar")
    @ApiOperation(value = "Cria um novo feedback do preceptor com base nos dados fornecidos.")
    public FeedbackPreceptorResponseDTO addFeedbackPreceptor(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return feedbackService.addFeedbackPreceptor(feedbackRequestDTO);
    }

    @PostMapping("/interno/cadastrar")
    @ApiOperation(value = "Cria um novo feedback do interno com base nos dados fornecidos.")
    public FeedbackInternoResponseDTO addFeedbackInterno(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        return feedbackService.addFeedbackInterno(feedbackRequestDTO);
    }

    @GetMapping("/buscar-feedback-preceptor/{idPreceptor}")
    @ApiOperation(value = "Retorna um feedback com base no ID do preceptor fornecido.")
    public List<FeedbackPreceptorResponseDTO> buscarFeedbackPreceptor(@PathVariable Long idPreceptor) {
        return feedbackService.buscarFeedbackPreceptor(idPreceptor);
    }

    @GetMapping("/buscar-feedback-interno/{idInterno}")
    public List<FeedbackInternoResponseDTO> buscarFeedbackInterno(@PathVariable Long idInterno){
        return feedbackService.buscarFeedbackInterno(idInterno);
    }

    @GetMapping("/buscar/autor")
    public List<FeedbackPreceptorResponseDTO> buscarAutor(@RequestParam String autor){
        return feedbackService.buscarAutor(autor);
    }

}
