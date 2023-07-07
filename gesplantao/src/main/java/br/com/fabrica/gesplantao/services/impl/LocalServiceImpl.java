package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.LocalRequestDTO;
import br.com.fabrica.gesplantao.dto.response.LocalResponseDTO;
import br.com.fabrica.gesplantao.exception.NotFoundException;
import br.com.fabrica.gesplantao.model.Local;
import br.com.fabrica.gesplantao.repository.LocalRepository;
import br.com.fabrica.gesplantao.services.LocalService;
import br.com.fabrica.gesplantao.util.LocalMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@AllArgsConstructor
@RequiredArgsConstructor
public class LocalServiceImpl implements LocalService {

    @Autowired
    private LocalRepository localRepository;

    public LocalServiceImpl(LocalRepository localRepository) {
        this.localRepository = localRepository;
    }

    @Autowired
    private LocalMapper localMapper;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public LocalResponseDTO findById(Long id) {
        return localRepository.findById(id).map(local -> modelMapper.map(local, LocalResponseDTO.class)
        ).orElseThrow(()-> new RuntimeException(""));
    }

    @Override
    public LocalResponseDTO findBySigla(String siglaLocal) {
        Optional<Local> optionalLocal = localRepository.findBySiglaLocal(siglaLocal);
        Local local = optionalLocal.orElseThrow(() -> new RuntimeException("Local não encontrado"));
        LocalResponseDTO localResponseDTO = modelMapper.map(local, LocalResponseDTO.class);
        return localResponseDTO;
    }


    @Override
    public List<LocalResponseDTO> findAll() {
        return localRepository.findAll().stream().map(local -> modelMapper.map(local, LocalResponseDTO.class)).collect(Collectors.toList());
    }


    @Override
    public LocalResponseDTO register(LocalRequestDTO localDTO) {
        Local local = localMapper.toLocal(localDTO);
        localRepository.save(local);
        return modelMapper.map(local, LocalResponseDTO.class);
    }

    @Override
    public LocalResponseDTO update(Long id, LocalRequestDTO localDTO) {
        Local local = returnLocalId(id);
        localMapper.updateLocalData(local, localDTO);
        localRepository.save(local);
        return modelMapper.map(local, LocalResponseDTO.class);

    }

    public LocalResponseDTO updateBySiglaLocal(String siglaLocal, LocalRequestDTO localDTO) {
            Optional<Local> optionalLocal = localRepository.findBySiglaLocal(siglaLocal);
            if (optionalLocal.isEmpty()) {
                throw new NotFoundException("Local não encontrado");
            }

            Local local = optionalLocal.get();
            local.setNome(localDTO.getNome());

            Local updatedLocal = localRepository.save(local);
            return modelMapper.map(updatedLocal, LocalResponseDTO.class);
        }



    @Override
    public String delete(Long id) {
        localRepository.deleteById(id);
        return "Local id: "+ id +" deletado com sucesso";
    }

    @Override
    @Transactional
    public String delete(String siglaLocal) {
        localRepository.deleteBySiglaLocal(siglaLocal);
        return "Local sigla: "+ siglaLocal +" deletado com sucesso";

    }

    @Override
    public LocalResponseDTO findLocalBySiglaLocal(String siglaLocal) {
        Local local = returnSiglaLocal(siglaLocal);
        return modelMapper.map(local, LocalResponseDTO.class);
    }

    @Override
    public boolean existsBySiglaLocal(String siglaLocal) {
        return localRepository.existsBySiglaLocal(siglaLocal);
    }

    @Override
    public boolean existsById(Long id) {
        return localRepository.existsById(id);
    }


    private Local returnSiglaLocal(String siglaLocal) {
        return localRepository.findBySiglaLocal(siglaLocal)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
    }

    private Local returnLocalId(Long id){
        return localRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Local não encontrado"));
    }


}
