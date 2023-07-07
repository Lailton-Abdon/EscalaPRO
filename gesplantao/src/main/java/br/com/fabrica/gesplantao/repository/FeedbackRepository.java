package br.com.fabrica.gesplantao.repository;

import br.com.fabrica.gesplantao.model.Feedback;
import br.com.fabrica.gesplantao.model.Interno;
import br.com.fabrica.gesplantao.model.Preceptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    List<Feedback> findByPreceptor(Preceptor preceptor);

    List<Feedback> findByInterno(Interno interno);

    @Query(value = "SELECT * FROM Feedback f WHERE f.autor LIKE %:autor%", nativeQuery = true)
    List<Feedback> findByAutor(@Param("autor") String auto);
}
