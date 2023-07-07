package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.RodizioRequestDTO;
import br.com.fabrica.gesplantao.dto.response.RodizioResponseDTO;
import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.services.RodizioService;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rodizios")
public class RodizioController {
    @Autowired
    private RodizioService rodizioService;


    @GetMapping("/listar/id/{id}")
    @ApiOperation(value = "Retorna um rodízio com base no ID fornecido.")
    public ResponseEntity<RodizioResponseDTO> findByRodizioId(@PathVariable Long id) {
        RodizioResponseDTO rodizio = rodizioService.findByRodizioId(id);
        return ResponseEntity.ok(rodizio);
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos os rodízios.")
    public ResponseEntity<List<RodizioResponseDTO>> getAllRodizios() {
        List<RodizioResponseDTO> rodizios = rodizioService.getAllRodizios();
        return ResponseEntity.ok(rodizios);
    }

    @PutMapping("/editar/{id}")
    @ApiOperation(value = "Atualiza um rodízio existente com base no ID fornecido.")
    public ResponseEntity<RodizioResponseDTO> updateRodizio(
            @PathVariable Long id,
            @RequestBody RodizioRequestDTO rodizioRequestDTO) {
        RodizioResponseDTO updatedRodizio = rodizioService.updateRodizio(id, rodizioRequestDTO);
        return ResponseEntity.ok(updatedRodizio);
    }

    @DeleteMapping("/deletar/id/{id}")
    @ApiOperation(value = "Exclui um rodízio com base no ID fornecido.")
    public ResponseEntity<Void> deleteRodizio(@PathVariable Long id) {
        rodizioService.deleteRodizio(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/listar/nome/{nome}")
    @ApiOperation(value = "Obtém uma lista de rodízios com base no nome fornecido.")
    public ResponseEntity<List<RodizioResponseDTO>> findByNome(@PathVariable String nome) {
        List<RodizioResponseDTO> rodizios = rodizioService.findByDescricao(nome);
        return ResponseEntity.ok(rodizios);
    }

    @GetMapping("/listar-rodizio-preceptor/{preceptor}")
    @ApiOperation(value = "Obtém uma lista de rodízio com base no preceptor fornecido.")
    public List<RodizioResponseDTO> buscarRodizioPreceptor(@PathVariable Preceptor preceptor) {
        return rodizioService.buscarRodizioPreceptor(preceptor);
    }

    @GetMapping("/listar-rodizio-especialidade/{especialidade}")
    @ApiOperation(value = "Obtém uma lista de rodízio com base na especialidade fornecida.")
    public List<RodizioResponseDTO> buscarRodizioPreceptor(@PathVariable Especialidade especialidade) {
        return rodizioService.buscarRodizioEspecialidade(especialidade);
    }

    @DeleteMapping("/deletar/nome/{nome}")
    @ApiOperation(value = "Exclui um rodízio com base no nome fornecido.")
    public ResponseEntity<Void> deleteRodizioByDescricao(@PathVariable String nome) {
        rodizioService.deleteRodizioByDescricao(nome);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/adicionar-equipe-rodizio/rodizio/{idRodizio}/equipe/{idEquipe}")
    @ApiOperation(value = "Atualiza uma equipe a um rodízio existente com base nos dados fornecidos.")
    public RodizioResponseDTO addEquipeRodizio(@PathVariable Long idRodizio, @PathVariable Long idEquipe){
        return rodizioService.addEquipeRodizio(idRodizio, idEquipe);
    }


}