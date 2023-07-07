package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.response.CheckinResponseDTO;
import br.com.fabrica.gesplantao.services.CheckinService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
@Api(value = "Checkin")
public class CheckinController {

    @Autowired
    private CheckinService checkinService;

    @PostMapping("/realizar-check-in/{idPlantao}")
    public CheckinResponseDTO realizarCheckin(@PathVariable Long idPlantao){
        return checkinService.realizarCheckin(idPlantao);
    }

    @PutMapping("realizar-check-out/{idPlantao}")
    public CheckinResponseDTO realizarCheckout(@PathVariable Long idPlantao){
        return checkinService.realizarCheckout(idPlantao);
    }
}
