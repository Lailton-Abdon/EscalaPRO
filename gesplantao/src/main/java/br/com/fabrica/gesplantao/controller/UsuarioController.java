package br.com.fabrica.gesplantao.controller;

import br.com.fabrica.gesplantao.dto.request.LoginRequestDTO;
import br.com.fabrica.gesplantao.dto.response.LoginResponseDTO;
import br.com.fabrica.gesplantao.model.Usuario;
import br.com.fabrica.gesplantao.repository.UsuarioRepository;
import br.com.fabrica.gesplantao.secutiry.JwtService;
import br.com.fabrica.gesplantao.services.impl.UsuarioServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor

public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;

    private final JwtService jwtService;

    private final UsuarioRepository usuarioRepository;


    @PostMapping("/auth")
    public LoginResponseDTO autenticar(@RequestBody LoginRequestDTO loginRequestDTO) {

        Usuario usuario = new Usuario();
        usuario.setEmail(loginRequestDTO.getEmailCrmRgm());
        usuario.setSenha(loginRequestDTO.getSenha());

        String email = usuario.getEmail();
        UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
        String token = jwtService.gerarToken(usuario);
        return new LoginResponseDTO(email, token);

    }

    @GetMapping("/listar-interno")
    public List<Usuario> listarUsuarioInternoIndefinido() {
        List<Usuario> usuarios = usuarioService.listarUsuariosInternosIndefinidos();
        return usuarios;
    }

    @GetMapping("/listar-preceptor")
    public List<Usuario> listarUsuarioPreceptorIndefinido() {
        List<Usuario> usuarios = usuarioService.listarUsuariosPreceptoresIndefinidos();
        return usuarios;
    }

    @PutMapping("/promover-usuario/{id}")
    public void promoverUsuario(@PathVariable Long id) {
        usuarioService.promoverUsuario(id);
    }

}
