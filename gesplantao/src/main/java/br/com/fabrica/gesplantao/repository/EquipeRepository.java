package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Equipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {

    Optional<Equipe> findByCodigoEquipe(String codigo);

    Optional<Equipe> findByQuantidadeVagas(Integer quantidade);

    List<Equipe> findByDataCriacao(LocalDate dataCriacao);
}
