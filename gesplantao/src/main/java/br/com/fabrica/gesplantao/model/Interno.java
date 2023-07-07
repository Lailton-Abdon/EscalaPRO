package br.com.fabrica.gesplantao.model;

import br.com.fabrica.gesplantao.enumeration.StatusInternoEnum;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Interno extends Usuario {

	private String rgm;

	private String curso;

	@ManyToMany(mappedBy = "internos")
	private List<Equipe> equipe = new ArrayList<>();

	@OneToMany(mappedBy = "interno")
	private List<Feedback> feedback = new ArrayList<>();

	@Enumerated(EnumType.STRING)
	private StatusInternoEnum status;

	@OneToMany(mappedBy = "interno")
	private List<CheckinCheckout> checkinCheckouts = new ArrayList<>();

	private Integer quantfalta;

	private Integer quantAtraso;

	private Integer quantPresenca;
}

