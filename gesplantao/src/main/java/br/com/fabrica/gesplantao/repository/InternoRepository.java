package br.com.fabrica.gesplantao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.fabrica.gesplantao.model.Interno;

import java.util.Optional;

@Repository
@Transactional
public interface InternoRepository extends JpaRepository<Interno, Long> {

    boolean existsByRgm(String rgm);

    @Query("SELECT i FROM Interno i WHERE i.rgm LIKE %:rgm%")
    Optional<Interno> findByRgm(@Param("rgm") String rmg);

    boolean existsByEmail(String email);
}
