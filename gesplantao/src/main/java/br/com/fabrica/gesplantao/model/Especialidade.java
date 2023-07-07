package br.com.fabrica.gesplantao.model;


import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Especialidade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", unique = true)
    private String nome;

    @OneToOne(mappedBy = "especialidade")
    private Preceptor preceptor;

    @ManyToMany(mappedBy = "especialidade")
    private List<Rodizio> rodizio = new ArrayList<>();


//    @ManyToOne
////    @JoinColumn(name = "ciclo_id")
////    private Ciclo ciclo;


    //parte de carol:
//    @OneToMany(mappedBy = "ciclo")
//    private List<Rodizio> rodizios = new ArrayList<>();


    //Local:
//    @OneToMany(mappedBy = "local")
//    private List<Ciclo> ciclos = new ArrayList<>();

    //Ciclo:
//    @ManyToOne
//    @JoinColumn(name = "local_id")
//    private Local local;

}
