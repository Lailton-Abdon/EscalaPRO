package br.com.fabrica.gesplantao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoEquipe;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate dataCriacao;

    private Integer quantidadeVagas;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "equipe_interno",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "interno_id"))
    private List<Interno> internos = new ArrayList<>();

    @OneToMany(mappedBy = "equipe")
    private List<Rodizio> rodizio = new ArrayList<>();
}
