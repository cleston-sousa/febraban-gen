package br.net.gits.febraban.api.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResponse {

	private Integer id;

	private String nome;

	private EstadoResponse estado;
}
