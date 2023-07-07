package br.com.fabrica.gesplantao.services.impl;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import br.com.fabrica.gesplantao.exception.BadRequestException;
import br.com.fabrica.gesplantao.exception.UsuarioException;
import br.com.fabrica.gesplantao.exception.UsuarioNaoEncontradoException;
import br.com.fabrica.gesplantao.model.Usuario;
import br.com.fabrica.gesplantao.repository.UsuarioRepository;
import br.com.fabrica.gesplantao.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UserDetailsService, UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails loadUserByUsername(String emailCrmRgm) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmailOrRgmOrCrm(emailCrmRgm, emailCrmRgm, emailCrmRgm)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (usuario.getPerfil().equals(PerfilEnum.INTERNO)) {
            authorities.add(new SimpleGrantedAuthority(PerfilEnum.INTERNO.toString()));
        }
        if (usuario.getPerfil().equals(PerfilEnum.PRECEPTOR)) {
            authorities.add(new SimpleGrantedAuthority(PerfilEnum.PRECEPTOR.toString()));
        }
        if (usuario.getPerfil().equals(PerfilEnum.COORDENADOR)) {
            authorities.add(new SimpleGrantedAuthority(PerfilEnum.COORDENADOR.toString()));
        }
        if (usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_INTERNO) || usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_PRECEPTOR)) {
            throw new BadRequestException("Usuário com perfil indefinido");
        }

        return new User(usuario.getEmail(), usuario.getSenha(), authorities);
    }

    public UserDetails autenticar(Usuario usuario) {
        UserDetails user = loadUserByUsername(usuario.getEmail());
        boolean senhasBatem = passwordEncoder.matches(usuario.getSenha(), user.getPassword());
        if (!senhasBatem) {
            throw new BadRequestException("Senha inválida");
        }
        return user;
    }

    public List<Usuario> listarUsuariosInternosIndefinidos() {
        List<Usuario> listaUsuario = usuarioRepository.findAll().stream().filter(usuario -> usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_INTERNO))
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());
        if (listaUsuario.isEmpty()) {
            throw new RuntimeException("Não tem solicitações pendentes");
        }
        return listaUsuario;
    }

    public List<Usuario> listarUsuariosPreceptoresIndefinidos() {
        List<Usuario> listaUsuario = usuarioRepository.findAll().stream().filter(usuario -> usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_INTERNO))
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());
        if (listaUsuario.isEmpty()) {
            throw new RuntimeException("Não tem solicitações pendentes");
        }
        return listaUsuario;
    }

    public void promoverUsuario(Long id) {
        usuarioRepository.findById(id).map(usuario -> {
            if (usuario.getPerfil().equals(PerfilEnum.INTERNO) || usuario.getPerfil().equals(PerfilEnum.PRECEPTOR)) {
                throw new UsuarioException("Usuário já tem um perfil designado!");
            }
            if (usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_INTERNO)) {
                usuario.setPerfil(PerfilEnum.INTERNO);
            }
            if (usuario.getPerfil().equals(PerfilEnum.INDEFINIDO_PRECEPTOR)) {
                usuario.setPerfil(PerfilEnum.PRECEPTOR);
            }
            usuarioRepository.save(usuario);
            return usuario;
        }).orElseThrow(() -> new RuntimeException("Mátricula não econtrada!"));
    }

    public Long retornaUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("Cliente não autenticado.");
        }
        String email = authentication.getName();
        var usuario = usuarioRepository.findByEmail(email).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
        return usuario.getId();
    }
}
