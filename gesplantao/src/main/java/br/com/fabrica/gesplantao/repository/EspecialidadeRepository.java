package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {

    @Query("SELECT e FROM Especialidade e WHERE e.nome LIKE %:nome%")
    List<Especialidade> findByNomeContaining(@Param("nome") String nome);
}
