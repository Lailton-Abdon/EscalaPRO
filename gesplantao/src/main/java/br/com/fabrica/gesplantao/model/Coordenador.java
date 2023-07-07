package br.com.fabrica.gesplantao.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class Coordenador extends Usuario {

	
	private String crm; // No diagrama, constava como int, mas acho melhor usar String, já que não será PK.

}
