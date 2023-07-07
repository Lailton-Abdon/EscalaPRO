package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.PlantaoRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PlantaoResponseDTO;
import br.com.fabrica.gesplantao.services.PlantaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/plantao")
public class PlantaoController {

    @Autowired
    private PlantaoService plantaoService;

    @PutMapping("/adicionar-plantao-rodizio/id-rodizio/{idRodizio}")
    @ApiOperation(value = "Adicina um plantão no rodizio com base nos dados fornecidos.")
    public PlantaoResponseDTO addPlantaoRodizio(@Valid @PathVariable Long idRodizio, @RequestBody PlantaoRequestDTO plantaoRequestDTO) {
        return plantaoService.addPlantaoNoRodizio(idRodizio, plantaoRequestDTO);
    }

    @PutMapping("/alterar/id-plantao/{idPlantao}")
    public PlantaoResponseDTO atualizarPlantao(@Valid @PathVariable Long idPlantao, @RequestBody PlantaoRequestDTO plantaoRequestDTO) {
        return plantaoService.atualizarPlantao(idPlantao, plantaoRequestDTO);
    }

    @GetMapping("/buscar/id-plantao/{idPlantao}")
    public PlantaoResponseDTO buscarId(@PathVariable Long idPlantao) {
        return plantaoService.buscarId(idPlantao);
    }

    @GetMapping("/buscar/data")
    public List<PlantaoResponseDTO> buscarDataInicio(@RequestParam LocalDate dataInicioPlantao) {
        return plantaoService.buscarData(dataInicioPlantao);
    }

    @GetMapping("/buscar/status/a-iniciar")
    public List<PlantaoResponseDTO> buscarStatusIniciar() {
        return plantaoService.buscarStatusIniciar();
    }

    @GetMapping("/buscar/status/concluido")
    public List<PlantaoResponseDTO> buscarStatusConcluido() {
        return plantaoService.buscarStatusConcluido();
    }

    @GetMapping("/buscar/status/em-andamento")
    public List<PlantaoResponseDTO> buscarStatusEmAndamento() {
        return plantaoService.buscarStatusEmAndamento();
    }

    @DeleteMapping("/remover/id-plantao/{idPlantao}")
    public void removerPlantao(@PathVariable Long idPlantao) {
        plantaoService.removerPlantao(idPlantao);
    }
}
