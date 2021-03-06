package br.net.gits.febraban.api.controller.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstadoResponse {

	private Integer id;

	private String codigo;

	private String nome;

	private List<CidadeResponse> cidade;

}
