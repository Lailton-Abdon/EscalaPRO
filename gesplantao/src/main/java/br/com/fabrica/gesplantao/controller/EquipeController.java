package br.com.fabrica.gesplantao.controller;


import br.com.fabrica.gesplantao.dto.response.EquipeResponseDTO;
import br.com.fabrica.gesplantao.exception.EquipeNaoEncontradaException;
import br.com.fabrica.gesplantao.services.EquipeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/equipe")
public class EquipeController {

    @Autowired
    private EquipeService equipeService;


    @PutMapping("/adicionar-interno/equipe/{idEquipe}")
    public EquipeResponseDTO addInterno(@PathVariable Long idEquipe) {
        EquipeResponseDTO equipeResponseDTO = equipeService.addInternoEquipe(idEquipe);
        return equipeResponseDTO;
    }

    @GetMapping("/buscar/quantidade/{quant}")
    public EquipeResponseDTO buscarQuantidade(Integer quantidade) {
        EquipeResponseDTO equipeResponseDTO = equipeService.buscarQuantidade(quantidade);
        return equipeResponseDTO;
    }

    @GetMapping("/buscar/id/{id}")
    public EquipeResponseDTO buscarId(@PathVariable Long id) {
        EquipeResponseDTO equipeResponseDTO = equipeService.buscarId(id);
        return equipeResponseDTO;
    }

    @GetMapping("/buscar/cod/{codigoEquipe}")
    public EquipeResponseDTO buscarId(@PathVariable String codigoEquipe) {
        EquipeResponseDTO equipeResponseDTO = equipeService.buscarCodigoEquipe(codigoEquipe);
        return equipeResponseDTO;
    }

    @GetMapping("/buscar/data")
    public List<EquipeResponseDTO> buscarDataCriacao(@RequestParam LocalDate data) {
        List<EquipeResponseDTO> equipeResponseDTO = equipeService.buscarDataCriaca(data);
        if (equipeResponseDTO.isEmpty()) {
            throw new EquipeNaoEncontradaException("Não existe equipe cadastradas nessa data");
        }
        return equipeResponseDTO;
    }

    @PutMapping("/remover/interno/equipe/id-equipe/{idEquipe}/id-interno/{idInterno}")
    public void removerInternoEquipe(@PathVariable Long idEquipe, @PathVariable Long idInterno) {
        equipeService.removerInterno(idEquipe, idInterno);
    }

//    @GetMapping("/buscar/equipes-preceptor")
//    public List<EquipeResponseDTO> buscarEquipePreceptor(@RequestParam String matricula) {
//        List<EquipeResponseDTO> equipeResponseDTOS = equipeService.buscarEquipePreceptor(matricula);
//        return equipeResponseDTOS;
//    }

//    @GetMapping("/buscar/vagas-disponivel")
//    public  List<EquipeResponseDTO>  listarEquipeVagaDisponivel() {
//        List<EquipeResponseDTO> equipeResponseDTO = equipeService.listarEquipeQuantidadeDisponivel();
//        return equipeResponseDTO;
//    }

}
