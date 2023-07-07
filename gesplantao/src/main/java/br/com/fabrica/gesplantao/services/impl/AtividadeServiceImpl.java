package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.AtividadeRequestDTO;
import br.com.fabrica.gesplantao.dto.response.AtividadeResponseDTO;
import br.com.fabrica.gesplantao.model.Atividade;
import br.com.fabrica.gesplantao.repository.AtividadeRepository;
import br.com.fabrica.gesplantao.services.AtividadeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeServiceImpl implements AtividadeService {

    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AtividadeResponseDTO cadastrarAtividade(AtividadeRequestDTO atividadeRequestDTO) {
        var atividade = new Atividade();
        atividade.setNome(atividadeRequestDTO.getNome());
        atividadeRepository.save(atividade);
        return modelMapper.map(atividade, AtividadeResponseDTO.class);
    }

    public List<AtividadeResponseDTO> listarAtividade(){
        return atividadeRepository.findAll().stream()
                .map(atividade -> modelMapper.map(atividade, AtividadeResponseDTO.class)).collect(Collectors.toList());
    }
}
