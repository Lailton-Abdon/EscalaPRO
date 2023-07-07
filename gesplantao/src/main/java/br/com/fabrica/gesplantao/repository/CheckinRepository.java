package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.CheckinCheckout;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.model.Plantao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckinRepository extends JpaRepository<CheckinCheckout, Long> {

    boolean existsByInternoAndPlantao(Interno interno, Plantao plantao);
}
