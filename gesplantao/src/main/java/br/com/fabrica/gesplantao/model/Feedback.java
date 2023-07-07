package br.com.fabrica.gesplantao.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

    @ManyToOne
    @JoinColumn(name = "preceptor_id")
    private Preceptor preceptor;

    @ManyToOne
    @JoinColumn(name = "interno_id")
    private Interno interno;

    private String autor;

    private LocalDate dataFeedback;

    private boolean anonimo;
}
