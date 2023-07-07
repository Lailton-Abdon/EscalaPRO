package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.AtividadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.AtividadeResponseDTO;
import br.com.fabrica.gesplantao.services.AtividadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades")
public class AtividadeController {

    @Autowired
    private AtividadeService atividadeService;

    @PostMapping("/cadastrar")
    public AtividadeResponseDTO cadastrar(@RequestBody AtividadeRequestDTO atividadeRequestDTO){
        return atividadeService.cadastrarAtividade(atividadeRequestDTO);
    }

    @GetMapping("/listar")
    public List<AtividadeResponseDTO> listarAtividade(){
        return atividadeService.listarAtividade();
    }
}
