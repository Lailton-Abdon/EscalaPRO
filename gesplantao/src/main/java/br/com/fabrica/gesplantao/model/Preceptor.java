package br.com.fabrica.gesplantao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public final class Preceptor extends Usuario {

	private String crm;

	@OneToOne
	@JoinColumn(name = "especialidade_id")
	private Especialidade especialidade;

	@ManyToMany(mappedBy = "preceptor")
	private List<Rodizio> rodizio = new ArrayList<>();

	@OneToMany(mappedBy = "preceptor")
	private List<Feedback> feedback = new ArrayList<>();

//	private List<AvaliacaoFinal> avaliacaoFinal = new ArrayList<>();
//
//	private List<Plantao> plantoes = new ArrayList<>();
	
}

