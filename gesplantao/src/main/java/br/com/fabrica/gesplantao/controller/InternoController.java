package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.InternoRequestDTO;
import br.com.fabrica.gesplantao.dto.request.InternoUpdateRequest;
import br.com.fabrica.gesplantao.dto.response.InternoResponseDTO;
import br.com.fabrica.gesplantao.exception.EntidadeNaoEncontradaException;
import br.com.fabrica.gesplantao.services.impl.InternoServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/internos")
public class InternoController {

    private final InternoServiceImpl internoService;

    public InternoController(InternoServiceImpl internoService) {
        this.internoService = internoService;
    }

    @GetMapping
    public List<InternoResponseDTO> listarInternos() {
        return internoService.listarTodos();
    }

    @GetMapping("/{internoId}")
    @ApiOperation(value = "Retorna um interno com base no ID fornecido.")
    public ResponseEntity<InternoResponseDTO> buscarPorId(@PathVariable Long internoId) {
        InternoResponseDTO internoEncontrado = internoService.buscarPorId(internoId);
        return ResponseEntity.ok().body(internoEncontrado);
    }

    @PutMapping("/novo")
    @ApiOperation(value = "Cria um novo interno com base nos dados fornecidos.")
    public ResponseEntity<InternoResponseDTO> cadastrarInterno(@Valid @RequestBody InternoRequestDTO interno) {
        InternoResponseDTO internoCriado = internoService.cadastrarInterno(interno);
        return ResponseEntity.status(HttpStatus.CREATED).body(internoCriado);
    }


    @PutMapping("/{internoId}")
    @ApiOperation(value = "Atualiza um interno existente com base no ID fornecido.")
    public ResponseEntity<InternoResponseDTO> atualizarInterno(@PathVariable Long internoId, @RequestBody InternoUpdateRequest interno) {
        InternoResponseDTO internoOptional = internoService.atualizarInterno(internoId, interno);
        return ResponseEntity.ok().body(internoOptional);
    }

    @PostMapping("/adicionar-interno-planilha")
    public void addInternos(@RequestParam String arquivo){
        internoService.addInterno(arquivo);
    }


    @DeleteMapping("/excluir/{internoId}")
    @ApiOperation(value = "Exclui um interno com base no ID fornecido.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> excluirInterno(@PathVariable Long internoId) {
        try {
            internoService.excluir(internoId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
