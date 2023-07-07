package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PreceptorRepository extends JpaRepository<Preceptor, Long> {

    boolean existsByCrm(String matricula);

    @Query("SELECT p FROM Preceptor p WHERE p.crm LIKE %:matricula%")
    Optional<Preceptor> findByCrm(@Param("matricula") String matricula);

    @Query("SELECT p FROM Preceptor p WHERE p.especialidade = :especialidade")
    List<Preceptor> findByEspecialidade(@Param("especialidade") Especialidade especialidade);

    @Query(value = "SELECT * FROM Usuario u WHERE u.nome like %:nome%", nativeQuery = true)
    List<Preceptor> findByNome(@Param("nome") String nome);
}
