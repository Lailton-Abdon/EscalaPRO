package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.request.FeedbackRequestDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackInternoResponseDTO;
import br.com.fabrica.gesplantao.dto.response.FeedbackPreceptorResponseDTO;
import br.com.fabrica.gesplantao.model.Preceptor;

import java.util.List;

public interface FeedbackService {

    FeedbackPreceptorResponseDTO addFeedbackPreceptor(FeedbackRequestDTO feedbackRequestDTO);

    FeedbackInternoResponseDTO addFeedbackInterno(FeedbackRequestDTO feedbackRequestDTO);

    List<FeedbackPreceptorResponseDTO> buscarFeedbackPreceptor(Long idPreceptor);

    List<FeedbackInternoResponseDTO> buscarFeedbackInterno(Long idInterno);

    List<FeedbackPreceptorResponseDTO> buscarAutor(String autor);
}
