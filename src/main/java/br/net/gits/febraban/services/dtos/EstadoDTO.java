package br.net.gits.febraban.services.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoDTO {

	private Integer id;

	private String codigo;

	private String nome;

	private List<CidadeDTO> cidades;

}
