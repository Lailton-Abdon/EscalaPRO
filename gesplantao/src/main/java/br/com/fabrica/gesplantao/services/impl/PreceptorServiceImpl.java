package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.dto.request.PreceptorRequestDTO;
import br.com.fabrica.gesplantao.dto.response.PreceptorResponseDTO;
import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.repository.EspecialidadeRepository;
import br.com.fabrica.gesplantao.repository.PreceptorRepository;
import br.com.fabrica.gesplantao.services.PreceptorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreceptorServiceImpl implements PreceptorService {

    @Autowired
    private PreceptorRepository preceptorRepository;

    @Autowired
    private EspecialidadeRepository especialidadeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    public PreceptorResponseDTO cadastrarPreceptor(PreceptorRequestDTO preceptorRequestDTO) {
        String senhaCriptografada = encoder.encode(preceptorRequestDTO.getSenha());
        preceptorRequestDTO.setSenha(senhaCriptografada);
        Preceptor preceptor = fromRequest(preceptorRequestDTO);
        preceptorRepository.save(preceptor);
        return modelMapper.map(preceptor, PreceptorResponseDTO.class);
    }

    public PreceptorResponseDTO buscarId(Long id) {
        PreceptorResponseDTO preceptor = preceptorRepository.findById(id)
                .map(usuario -> modelMapper.map(usuario, PreceptorResponseDTO.class))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Preceptor não encontrado"));
        return preceptor;
    }

    public PreceptorResponseDTO buscarMatricula(String matricula) {
        PreceptorResponseDTO preceptor = preceptorRepository.findByCrm(matricula)
                .map(usuario -> modelMapper.map(usuario, PreceptorResponseDTO.class))
                .orElseThrow(() -> new UsuarioNaoEncontradoException("Preceptor não encontrado"));
        return preceptor;
    }

    public List<PreceptorResponseDTO> buscarNome(String nome) {
        List<PreceptorResponseDTO> preceptor = preceptorRepository.findByNome(nome).stream()
                .map(usuario -> modelMapper.map(usuario, PreceptorResponseDTO.class)).collect(Collectors.toList());
        if (preceptor.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não tem preceptores com esse nome");
        }
        return preceptor;
    }

    public List<PreceptorResponseDTO> listarPreceptor() {
        List<PreceptorResponseDTO> listaPreceptor = preceptorRepository.findAll().stream()
                .filter(usuario -> usuario.equals(PerfilEnum.PRECEPTOR))
                .map(usuario -> modelMapper.map(usuario, PreceptorResponseDTO.class)).collect(Collectors.toList());
        if (listaPreceptor.isEmpty()) {
            throw new UsuarioNaoEncontradoException("Não existe preceptores cadastrados");
        }
        return listaPreceptor;
    }

    public void removerPreceptor(Long id) {
        if (!preceptorRepository.existsById(id)) {
            throw new UsuarioNaoEncontradoException("Usuario não encontrado");
        }
        preceptorRepository.deleteById(id);
    }


    private static Preceptor fromRequest(PreceptorRequestDTO preceptorRequestDTO) {
        Preceptor preceptor = new Preceptor();
        preceptor.setNome(preceptorRequestDTO.getNome());
        preceptor.setTelefone(preceptorRequestDTO.getTelefone());
        preceptor.setEmail(preceptorRequestDTO.getEmail());
        preceptor.setPerfil(PerfilEnum.INDEFINIDO_PRECEPTOR);
        preceptor.setCrm(preceptorRequestDTO.getCrm());
        preceptor.setSenha(preceptorRequestDTO.getSenha());
        preceptor.setDataCriacao(LocalDate.now());
        return preceptor;
    }
}
