package br.com.fabrica.gesplantao.model;

import br.com.fabrica.gesplantao.enumeration.StatusCheckinEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CheckinCheckout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Interno interno;

    private LocalDate dataEntrada;

    private LocalTime horaEntrada;

    private LocalDate dataSaida;

    private LocalTime horaSaida;

    @Enumerated(EnumType.STRING)
    private StatusCheckinEnum statusCheckin;

    @ManyToOne
    @JoinColumn(name = "plantao_id")
    private Plantao plantao;

}
