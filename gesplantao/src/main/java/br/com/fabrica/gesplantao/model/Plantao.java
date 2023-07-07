package br.com.fabrica.gesplantao.model;

import br.com.fabrica.gesplantao.enumeration.StatusPlantaoEnum;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Plantao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoPlantao;

    private LocalDate dataInicioPlantao;

    private LocalTime horaInicio;

    private LocalTime horaFim;

    private LocalDate dataFimPlantao;

    @Enumerated(EnumType.STRING)
    private StatusPlantaoEnum status;

    @ManyToOne
    @JoinColumn(name = "rodizio_id")
    private Rodizio rodizio;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "plantao_atividade",
            joinColumns = @JoinColumn(name = "plantao_id"),
            inverseJoinColumns = @JoinColumn(name = "atividade_id"))
    private List<Atividade> atividades = new ArrayList<>();

    @OneToMany(mappedBy = "plantao", cascade = CascadeType.ALL)
    private List<CheckinCheckout> checkins = new ArrayList<>();


}
