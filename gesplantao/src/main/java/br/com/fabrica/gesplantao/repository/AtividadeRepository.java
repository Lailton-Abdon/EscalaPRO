package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
}
