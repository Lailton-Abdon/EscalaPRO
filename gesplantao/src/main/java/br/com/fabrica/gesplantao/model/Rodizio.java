package br.com.fabrica.gesplantao.model;

import br.com.fabrica.gesplantao.enumeration.StatusRodizioEnum;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "rodizio")
public class Rodizio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String descricao;

    private Integer quantPlantao;

    @ManyToOne
    @JoinColumn(name = "ciclo_id")
    private Ciclo ciclo;


    @ManyToMany
    @JoinTable(name = "rodizio_especialidade",
            joinColumns = @JoinColumn(name = "rodizio_id"),
            inverseJoinColumns = @JoinColumn(name = "especialidade_id"))
    private List<Especialidade> especialidade = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "rodizio_preceptor",
            joinColumns = @JoinColumn(name = "rodizio_id"),
            inverseJoinColumns = @JoinColumn(name = "preceptor_id"))
    private List<Preceptor> preceptor = new ArrayList<>();

    @OneToMany(mappedBy = "rodizio")
    private List<Plantao> plantao = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "equipe_id")
    private Equipe equipe;

    @ManyToOne
    @JoinColumn (name = "local_id")
    private Local local;


    @Enumerated(EnumType.STRING)
    private StatusRodizioEnum statusRodizio;
}
