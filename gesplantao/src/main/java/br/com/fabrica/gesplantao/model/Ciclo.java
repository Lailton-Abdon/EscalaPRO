package br.com.fabrica.gesplantao.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tb_ciclo")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Ciclo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true)
    private String codigo;

    @Column(name = "inicio_ciclo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate inicioCiclo;

    @Column(name = "fim_ciclo")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", locale = "pt-BR", timezone = "Brazil/East")
    private LocalDate fimCiclo;

    private String duracaoCiclo;

    @OneToMany(mappedBy = "ciclo", cascade = CascadeType.ALL)
    private List<Rodizio> rodizios;

    public Ciclo(String codigo, LocalDate inicioCiclo, LocalDate fimCiclo) {
        this.codigo = codigo;
        this.inicioCiclo = inicioCiclo;
        this.fimCiclo = fimCiclo;
    }

}
