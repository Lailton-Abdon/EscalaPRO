package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.email LIKE %:email%")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query("SELECT u FROM Usuario u WHERE u.email = :email OR u.rgm = :rgm OR u.crm = :crm")
    Optional<Usuario> findByEmailOrRgmOrCrm(@Param("email") String email, @Param("rgm") String rgm, @Param("crm") String crm);
}
