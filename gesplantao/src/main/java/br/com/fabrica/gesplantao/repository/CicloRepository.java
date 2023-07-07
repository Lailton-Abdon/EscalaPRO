package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Ciclo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Long> {

    @Query("SELECT c FROM Ciclo c WHERE c.codigo LIKE %:codigo%")
    Ciclo findByCodigo(String codigo);

    boolean existsByCodigo(String codigo);
}
