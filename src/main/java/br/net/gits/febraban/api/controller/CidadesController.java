package br.net.gits.febraban.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.net.gits.febraban.api.controller.model.CidadeResponse;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.EstadoIdDTO;
import br.net.gits.febraban.utils.ModelMapperUtils;

@RestController
@RequestMapping("/cidades")
public class CidadesController {

	private ICidadesService cidadesService;

	public CidadesController(ICidadesService cidadesService) {
		this.cidadesService = cidadesService;
	}

	@GetMapping("/estado/{estadoId}")
	public List<CidadeResponse> listarPorEstadoId(@PathVariable Integer estadoId) {
		var resultado = this.cidadesService.obterPorEstado(EstadoIdDTO.builder().id(estadoId).build());
		return ModelMapperUtils.toList(resultado, CidadeResponse.class);
	}

	@GetMapping("/{cidadeId}")
	public CidadeResponse obterPorId(@PathVariable Integer cidadeId) {
		var resultado = this.cidadesService.obterPorId(cidadeId);
		return ModelMapperUtils.to(resultado, CidadeResponse.class);
	}

}
