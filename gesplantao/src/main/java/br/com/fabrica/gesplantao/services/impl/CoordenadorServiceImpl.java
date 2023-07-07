package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.CoordenadorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.CoordenadorResponseDTO;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.repository.CoordenadorRepository;
import br.com.fabrica.gesplantao.model.Coordenador;
import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.services.CoordenadorService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CoordenadorServiceImpl implements CoordenadorService {
    @Autowired
    CoordenadorRepository coordenadorRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    public CoordenadorResponseDTO cadastrarCoordenador(CoordenadorRequestDTO coordenadorRequestDTO) {
        String senhaCriptografada = encoder.encode(coordenadorRequestDTO.getSenha());
        coordenadorRequestDTO.setSenha(senhaCriptografada);
        Coordenador coordenador  = fromRequest(coordenadorRequestDTO);
        coordenador.setPerfil(PerfilEnum.COORDENADOR);
        coordenadorRepository.save(coordenador);
        return modelMapper.map(coordenador, CoordenadorResponseDTO.class);
    }

    public CoordenadorResponseDTO buscarId(Long id){
        CoordenadorResponseDTO coordenador = coordenadorRepository.findById(id)
                .map(usuario -> modelMapper.map(usuario, CoordenadorResponseDTO.class))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Coordenador não encontrado"));
        return coordenador;
    }

    public CoordenadorResponseDTO buscarMatricula(String matricula){
        CoordenadorResponseDTO coordenador = coordenadorRepository.findByCrm(matricula)
                .map(usuario -> modelMapper.map(usuario, CoordenadorResponseDTO.class))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Coordenador não encontrado"));
        return coordenador;
    }

    public List<CoordenadorResponseDTO> buscarNome(String nome){
        List<CoordenadorResponseDTO> coordenador = coordenadorRepository.findByNome(nome).stream()
                .map(usuario -> modelMapper.map(usuario, CoordenadorResponseDTO.class)).collect(Collectors.toList());
        if(coordenador.isEmpty()){
            throw new UsuarioNaoEncontradoException("Não existe coordenador com esse nome");
        }
        return coordenador;
    }

    public List<CoordenadorResponseDTO> listarCoordenador(){
        List<CoordenadorResponseDTO> coordenador = coordenadorRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, CoordenadorResponseDTO.class)).collect(Collectors.toList());
        if(coordenador.isEmpty()){
            throw new UsuarioNaoEncontradoException("Não existe coordenador com esse nome");
        }
        return coordenador;
    }

    public void removerCoordenador(Long id){
        if(!coordenadorRepository.existsById(id)){
            throw new UsuarioNaoEncontradoException("Coordenador não encontrado");
        }
        coordenadorRepository.deleteById(id);
    }

    private static Coordenador fromRequest(CoordenadorRequestDTO coordenadorRequestDTO) {
        Coordenador coordenador = new Coordenador();
        coordenador.setNome(coordenadorRequestDTO.getNome());
        coordenador.setTelefone(coordenadorRequestDTO.getTelefone());
        coordenador.setEmail(coordenadorRequestDTO.getEmail());
        coordenador.setCrm(coordenadorRequestDTO.getCrm());
        coordenador.setSenha(coordenadorRequestDTO.getSenha());
        coordenador.setDataCriacao(LocalDate.now());
        return coordenador;
    }
}
