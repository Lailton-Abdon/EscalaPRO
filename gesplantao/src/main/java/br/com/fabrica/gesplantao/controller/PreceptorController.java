package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.PreceptorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PreceptorResponseDTO;
import br.com.fabrica.gesplantao.services.PreceptorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/preceptor")
public class PreceptorController {

    @Autowired
    private PreceptorService preceptorService;

    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cria um novo preceptor com base nos dados fornecidos.")
    @ResponseStatus(HttpStatus.CREATED)
    public PreceptorResponseDTO cadastrarPreceptor(@Valid @RequestBody PreceptorRequestDTO preceptorRequestDTO){
        PreceptorResponseDTO preceptorResponseDTO = preceptorService.cadastrarPreceptor(preceptorRequestDTO);
        return preceptorResponseDTO;
    }

    @GetMapping("/buscar/id/{id}")
    @ApiOperation(value = "Retorna um preceptor com base no ID fornecido.")
    public PreceptorResponseDTO buscarId(@PathVariable Long id){
        PreceptorResponseDTO preceptorResponseDTO = preceptorService.buscarId(id);
        return preceptorResponseDTO;
    }

    @GetMapping("/buscar/matricula/{matricula}")
    @ApiOperation(value = "Retorna um preceptor com base na matrícula fornecido.")
    public PreceptorResponseDTO buscarMatriculaPreceptor(@PathVariable String matricula){
        PreceptorResponseDTO preceptorResponseDTO = preceptorService.buscarMatricula(matricula);
        return preceptorResponseDTO;
    }

    @GetMapping("/buscar/nome")
    @ApiOperation(value = "Retorna um preceotor com base no nome fornecido.")
    public List<PreceptorResponseDTO> buscarNome(@RequestParam String nome){
        List<PreceptorResponseDTO> preceptorResponseDTOS = preceptorService.buscarNome(nome);
        return preceptorResponseDTOS;
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos os preceptores.")
    public List<PreceptorResponseDTO> listarPreceptor(){
        List<PreceptorResponseDTO> listaPreceptor = preceptorService.listarPreceptor();
        return listaPreceptor;
    }

    @DeleteMapping("/remover/{id}")
    @ApiOperation(value = "Exclui um preceptor com base no ID fornecido.")
    public void removerPreceptor(@PathVariable Long id){
        preceptorService.removerPreceptor(id);
    }

}
