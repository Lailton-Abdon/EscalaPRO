package br.com.fabrica.gesplantao.services;

import br.com.fabrica.gesplantao.dto.response.CheckinResponseDTO;

public interface CheckinService {

    CheckinResponseDTO realizarCheckin(Long idPlantao);

    CheckinResponseDTO realizarCheckout(Long idPlantao);
}
