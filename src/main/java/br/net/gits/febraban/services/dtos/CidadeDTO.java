package br.net.gits.febraban.services.dtos;

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
public class CidadeDTO {

	private Integer id;

	private String nome;

	private EstadoDTO estado;

}
