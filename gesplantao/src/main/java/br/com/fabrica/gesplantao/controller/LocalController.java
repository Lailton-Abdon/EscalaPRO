package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.LocalRequestDTO;
import br.com.fabrica.gesplantao.dto.response.LocalResponseDTO;
import br.com.fabrica.gesplantao.repository.LocalRepository;
import br.com.fabrica.gesplantao.services.LocalService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/local")
public class LocalController {

    @Autowired
    private LocalService localService;


    @Autowired
    private LocalRepository localRepository;

    @PostMapping("/cadastrar")
    @ApiOperation(value = "Cria um novo local com base nos dados fornecidos.")
    public ResponseEntity<Object> register(@Valid @RequestBody LocalRequestDTO localDTO, UriComponentsBuilder uriBuilder) {
        String siglaLocal = localDTO.getSiglaLocal();
        if (localService.existsBySiglaLocal(siglaLocal)) {
            String mensagemErro = "Local já existe";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
        }
        LocalResponseDTO localResponseDTO = localService.register(localDTO);
        URI uri = uriBuilder.path("/local/{id}").buildAndExpand(localResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(localResponseDTO);
    }

    @GetMapping("/buscar/id/{id}")
    @ApiOperation(value = "Retorna um local com base no ID fornecido.")
    public ResponseEntity<LocalResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(localService.findById(id));
    }

    @GetMapping("/listar")
    @ApiOperation(value = "Obtém uma lista de todos os locais.")
    public ResponseEntity<List<LocalResponseDTO>> findAll() {
        return ResponseEntity.ok().body(localService.findAll());
    }

    @GetMapping("/buscar/siglaLocal/{siglaLocal}")
    @ApiOperation(value = "Retorna um local com base na sigla fornecido.")
    public ResponseEntity<LocalResponseDTO> findLocalBySiglaLocal(@PathVariable String siglaLocal) {
        LocalResponseDTO localResponseDTO = localService.findLocalBySiglaLocal(siglaLocal);
        return ResponseEntity.ok(localResponseDTO);
    }

    @PutMapping("/id/{id}")
    @ApiOperation(value = "Atualiza um local existente com base no ID fornecido.")
    public ResponseEntity<Object> update(@RequestBody LocalRequestDTO localDTO, @PathVariable(name = "id") Long id) {
        if (!localService.existsById(id)) {
            String mensagemErro = "Local não encontrado";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensagemErro);
        }
        String siglaLocal = localDTO.getSiglaLocal();
        LocalResponseDTO existingLocal = localService.findLocalBySiglaLocal(siglaLocal);
        if (existingLocal != null && !existingLocal.getId().equals(id)) {
            String mensagemErro = "Já existe um Local com a mesma sigla";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagemErro);
        }
        LocalResponseDTO localResponseDTO = localService.update(id, localDTO);
        return ResponseEntity.ok().body(localResponseDTO);
    }

    @PutMapping("/siglaLocal/{siglaLocal}")
    @ApiOperation(value = "Atualiza um local existente com base na sigla fornecida.")
    public ResponseEntity<Object> updateBySiglaLocal(@RequestBody LocalRequestDTO localDTO, @PathVariable(name = "siglaLocal") String siglaLocal) {
        LocalResponseDTO localResponseDTO = localService.updateBySiglaLocal(siglaLocal, localDTO);
        return ResponseEntity.ok().body(localResponseDTO);
    }


    @DeleteMapping("/id/{id}")
    @ApiOperation(value = "Exclui um local com base no ID fornecido.")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(localService.delete(id));
    }

    @DeleteMapping("/siglaLocal/{siglaLocal}")
    @ApiOperation(value = "Exclui um local com base na sigla fornecida.")
    public ResponseEntity<String> delete(@PathVariable(value = "siglaLocal") String siglaLocal) {
        return ResponseEntity.ok().body(localService.delete(siglaLocal));
    }
}

/*__________ Falta implementar Listar Locais sem Rodízio,  Adicionar rodizio pelo idLocal e
    idRotina e Adicionar rodízio  a um local por siglaLocal e idRotina __________ */
