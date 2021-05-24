package br.net.gits.febraban.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.net.gits.febraban.api.controller.model.EstadoRequest;
import br.net.gits.febraban.api.controller.model.EstadoResponse;
import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.utils.ModelMapperUtils;

@RestController
@RequestMapping("/estados")
public class EstadosController {

	private IEstadosService estadosService;

	public EstadosController(IEstadosService estadosService) {
		this.estadosService = estadosService;
	}

	@GetMapping
	public List<EstadoResponse> listarTodos() {

		var resultado = this.estadosService.listarTodos();

		return ModelMapperUtils.toList(resultado, EstadoResponse.class);
	}

	@GetMapping("/{estadoId}")
	public EstadoResponse obterPorId(@PathVariable Integer estadoId) {

		var resultado = this.estadosService.obterPorId(estadoId);

		return ModelMapperUtils.to(resultado, EstadoResponse.class);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoResponse adicionar(@RequestBody EstadoRequest estadoRequest) {

		var add = ModelMapperUtils.to(estadoRequest, EstadoDTO.class);

		var resultado = this.estadosService.adicionar(add);

		return ModelMapperUtils.to(resultado, EstadoResponse.class);
	}

	@PutMapping("/{estadoId}")
	public EstadoResponse alterar(@PathVariable Integer estadoId, @RequestBody EstadoRequest estadoRequest) {

		var edit = ModelMapperUtils.to(estadoRequest, EstadoDTO.class);

		var resultado = this.estadosService.salvar(estadoId, edit);

		return ModelMapperUtils.to(resultado, EstadoResponse.class);
	}

}
