package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.EspecialidadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.EspecialidadeResponseDTO;
import br.com.fabrica.gesplantao.services.EspecialidadeService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadeController {

    @Autowired
    private EspecialidadeService especialidadeService;

    //rota listar todas as especialidades
    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos as especialidades.")
    public List<EspecialidadeResponseDTO> buscarTodasEspecialidades() {
        return especialidadeService.buscarTodasEspecialidades();
    }

    //rota listar por id
    @GetMapping("/listar/id/{id}")
    @ApiOperation(value = "Obtém uma lista de especialidade com base no ID fornecido.")
    public EspecialidadeResponseDTO buscarPorId(@PathVariable Long id) {
        return especialidadeService.buscarPorId(id);
    }

    //rota listar por nome
    @GetMapping("/listar/nome/{nome}")
    @ApiOperation(value = "Obtém uma lista de especialidade com base no nome fornecido.")
    public List<EspecialidadeResponseDTO> buscarPorNome(@PathVariable String nome) {
        return especialidadeService.buscarPorNome(nome);
    }

    //rota cadastrar
    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cria uma nova especialidade com base nos dados fornecidos.")
    @ResponseStatus(HttpStatus.CREATED)
    public EspecialidadeResponseDTO criarEspecialidade(@Valid @RequestBody EspecialidadeRequestDTO especialidadeRequestDTO) {
        return especialidadeService.criarEspecialidade(especialidadeRequestDTO);
    }

    //rota editar por id
    @PutMapping("/editar/id/{id}")
    @ApiOperation(value = "Atualiza uma especialidade existente com base no ID fornecido.")
    public EspecialidadeResponseDTO atualizarEspecialidade(@PathVariable Long id, @RequestBody EspecialidadeRequestDTO especialidadeRequestDTO) {
        return especialidadeService.atualizarEspecialidade(id, especialidadeRequestDTO);
    }

    @PutMapping("/add-preceptor/idEspecialidade/{idEspecialidade}/idPreceptor/{idPreceptor}")
    @ApiOperation(value = "Adicinar um preceptor a uma especialidade com base nos dados fornecidos.")
    public void addPreceptor(@PathVariable Long idEspecialidade, @PathVariable Long idPreceptor) {
        especialidadeService.addPreceptor(idEspecialidade, idPreceptor);
    }


    //rota deletar por id
    @DeleteMapping("/deletar/{id}")
    @ApiOperation(value = "Exclui uma especialidade com base no ID fornecido.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEspecialidade(@PathVariable Long id) {
        especialidadeService.excluirEspecialidade(id);
    }
}