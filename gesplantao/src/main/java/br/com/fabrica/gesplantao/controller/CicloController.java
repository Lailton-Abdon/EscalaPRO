package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.CicloRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CicloResponseDTO;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.NotFoundException;
import br.com.fabrica.gesplantao.repository.CicloRepository;
import br.com.fabrica.gesplantao.services.CicloService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ciclos")
public class CicloController {
    @Autowired
    private CicloService cicloService;

    @Autowired
    private CicloRepository cicloRepository;

    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cria um novo ciclo com base nos dados fornecidos.")
    public CicloResponseDTO register(@Valid @RequestBody CicloRequestDTO cicloDTO) {
        return cicloService.register(cicloDTO);
    }

    @GetMapping("/buscar/id/{id}")
    @ApiOperation(value = "Retorna um ciclo com base no ID fornecido.")
    public ResponseEntity<CicloResponseDTO> buscarCicloPorId(@PathVariable Long id) {
        CicloResponseDTO responseDTO = cicloService.buscarCicloPorId(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar/codigo/{codigo}")
    @ApiOperation(value = "Retorna um ciclo com base no código fornecido.")
    public ResponseEntity<CicloResponseDTO> buscarCicloPorCodigo(@PathVariable String codigo) {
        CicloResponseDTO responseDTO = cicloService.buscarCicloPorCodigo(codigo);
        if (responseDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos os ciclos.")
    public ResponseEntity<List<CicloResponseDTO>> listarTodosCiclos() {
        List<CicloResponseDTO> responseDTO = cicloService.listarTodosCiclos();
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/id/{id}")
    @ApiOperation(value = "Atualiza um ciclo existente com base no ID fornecido.")
    public ResponseEntity<CicloResponseDTO> atualizarCiclo(@PathVariable Long id, @RequestBody CicloRequestDTO requestDTO) {
        CicloResponseDTO responseDTO = cicloService.atualizarCiclo(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/codigo/{codigo}")
    @ApiOperation(value = "Atualiza um ciclo existente com base no código fornecido.")
    public ResponseEntity<Object> atualizarCicloPorCodigo(@PathVariable String codigo, @RequestBody CicloRequestDTO requestDTO) {
        try {
            CicloResponseDTO responseDTO = cicloService.atualizarCicloPorCodigo(codigo, requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/id/{id}")
    @ApiOperation(value = "Exclui um ciclo com base no ID fornecido.")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(cicloService.delete(id));
    }

    @DeleteMapping("/codigo/{codigo}")
    @ApiOperation(value = "Exclui um ciclo com base no código fornecido.")
    public ResponseEntity<String> deleteByCodigo(@PathVariable(value = "codigo") String codigo) {
        String result = cicloService.deleteByCodigo(codigo);
        return ResponseEntity.ok().body(result);
    }
}