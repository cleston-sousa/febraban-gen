package br.net.gits.febraban.services.dtos;

import br.net.gits.annotation.Field;
import br.net.gits.annotation.Line;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Line
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ArquivoCidadeDTO {

	@Field(min = 1, max = 7)
	private Integer id;

	@Field(min = 8, max = 107)
	private String nome;

	@Field(min = 108, max = 109)
	private Integer estado;

}
