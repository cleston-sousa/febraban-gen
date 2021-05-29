package br.net.gits.febraban.services.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EstadoCidadeDTO {

	private Integer id;

	private String codigo;

	private String nome;

	private List<CidadeDTO> cidade;

}