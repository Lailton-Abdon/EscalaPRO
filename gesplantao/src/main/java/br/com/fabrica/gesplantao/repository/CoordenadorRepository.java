package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Coordenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoordenadorRepository extends JpaRepository<Coordenador, Long> {

    boolean existsByCrm(String matricula);

    @Query("SELECT c FROM Coordenador c WHERE c.crm LIKE %:matricula%")
    Optional<Coordenador> findByCrm(@Param("matricula") String matricula);

    @Query("SELECT c FROM Coordenador c WHERE c.nome LIKE %:nome%")
    List<Coordenador> findByNome(@Param("nome") String nome);
}
