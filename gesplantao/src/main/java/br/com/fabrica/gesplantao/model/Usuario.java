package br.com.fabrica.gesplantao.model;

import br.com.fabrica.gesplantao.enumeration.PerfilEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String telefone;

	private String email;

	private String senha;

	@Enumerated(EnumType.STRING)
	private PerfilEnum perfil;

	@Column
	private LocalDate dataCriacao;

	private String cpf;
}
