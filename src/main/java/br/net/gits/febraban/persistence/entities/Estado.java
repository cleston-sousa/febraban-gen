package br.net.gits.febraban.persistence.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Estado {

	@EqualsAndHashCode.Include
	@Id
	private Integer id;

	private String codigo;

	private String nome;

	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidade;

}
