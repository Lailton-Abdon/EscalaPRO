package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.CoordenadorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CoordenadorResponseDTO;
import br.com.fabrica.gesplantao.services.impl.CoordenadorServiceImpl;
import io.swagger.annotations.Tag;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/coordenador")
public class CoordenadorController {

    @Autowired
    private CoordenadorServiceImpl coordenadorService;

    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cria um novo coordenador com base nos dados fornecidos.")
    @ResponseStatus(HttpStatus.CREATED)
    public CoordenadorResponseDTO cadastrarCoordenador(@Valid @RequestBody CoordenadorRequestDTO coordenadorRequestDTO){
        CoordenadorResponseDTO coordenadorResponseDTO = coordenadorService.cadastrarCoordenador(coordenadorRequestDTO);
        return coordenadorResponseDTO;
    }

    @GetMapping("/buscar/id/{id}")
    @ApiOperation(value = "Retorna um coordenador com base no ID fornecido.")
    public CoordenadorResponseDTO buscarId(@PathVariable Long id){
        CoordenadorResponseDTO coordenadorResponseDTO = coordenadorService.buscarId(id);
        return coordenadorResponseDTO;
    }

    @GetMapping("/buscar/matricula/{matricula}")
    @ApiOperation(value = "Retorna um coordenador com base na matrícula fornecido.")
    public CoordenadorResponseDTO buscarMatriculaCoordenador(@PathVariable String matricula){
        CoordenadorResponseDTO coordenadorResponseDTO = coordenadorService.buscarMatricula(matricula);
        return coordenadorResponseDTO;
    }

    @GetMapping("/buscar/nome")
    @ApiOperation(value = "Retorna um coordenador com base no nome fornecido.")
    public List<CoordenadorResponseDTO> buscarNome(@Valid @RequestParam String nome){
        List<CoordenadorResponseDTO> coordenadorResponseDTO = coordenadorService.buscarNome(nome);
        return coordenadorResponseDTO;
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos os coordenadores.")
    public List<CoordenadorResponseDTO> listarCoordenador(){
        List<CoordenadorResponseDTO> listaCoordenador = coordenadorService.listarCoordenador();
        return listaCoordenador;
    }

    @DeleteMapping("/remover/{id}")
    @ApiOperation(value = "Exclui um coordenador com base no ID fornecido.")
    public void removerCoordenador(@PathVariable Long id){
        coordenadorService.removerCoordenador(id);
    }
}
