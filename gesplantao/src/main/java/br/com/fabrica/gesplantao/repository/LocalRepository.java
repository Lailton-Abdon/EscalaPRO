package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalRepository extends JpaRepository<Local, Long> {

    @Query("SELECT l FROM Local l WHERE l.siglaLocal LIKE %:siglaLocal%")
    Optional<Local> findBySiglaLocal(@Param("siglaLocal") String siglaLocal);

    void deleteBySiglaLocal(String siglaLocal);

    boolean existsBySiglaLocal(String siglaLocal);

    boolean existsById(Long id);
}
