package br.net.gits.febraban.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.gits.febraban.api.controller.model.CidadeResponse;
import br.net.gits.febraban.api.controller.model.ImportarArquivoCidadeRequest;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.EstadoIdDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
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

	@PostMapping("/importar")
	@ResponseStatus(HttpStatus.CREATED)
	public List<CidadeResponse> importarArquivo(ImportarArquivoCidadeRequest importarArquivo) {
		try {
			var resultado = this.cidadesService
					.importarArquivoTamanhoFixo(importarArquivo.getArquivo().getInputStream());
			return ModelMapperUtils.toList(resultado, CidadeResponse.class);
		} catch (IOException e) {
			throw new BusinessException("Erro durante upload do arquivo");
		}
	}

}
