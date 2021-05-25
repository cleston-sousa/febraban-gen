package br.net.gits.febraban.persistence.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cidade {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private String nome;

	@ManyToOne
	private Estado estado;
}
