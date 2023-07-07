package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PlantaoRepository extends JpaRepository<Plantao, Long> {

    @Query("SELECT p FROM Plantao p WHERE p.dataInicioPlantao = :dataInicioPlantao")
    List<Plantao> findByDataInicioPlantao(LocalDate dataInicioPlantao);
}
