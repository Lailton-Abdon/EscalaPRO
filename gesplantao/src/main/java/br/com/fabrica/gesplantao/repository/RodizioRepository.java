package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Especialidade;
import br.com.fabrica.gesplantao.model.Preceptor;
import br.com.fabrica.gesplantao.model.Rodizio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RodizioRepository extends JpaRepository<Rodizio,Long> {

    @Query("SELECT r FROM Rodizio r WHERE r.descricao = :descricao")
    List<Rodizio> findByDescricao(@Param("descricao") String descricao);

    List<Rodizio> findByPreceptor(Preceptor preceptor);

    List<Rodizio> findByEspecialidade(Especialidade especialidade);

}
