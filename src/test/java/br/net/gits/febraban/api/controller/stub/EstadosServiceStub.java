package br.net.gits.febraban.api.controller.stub;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
import br.net.gits.febraban.services.exceptions.BusinessException;

@TestConfiguration
public class EstadosServiceStub {

	public IEstadosService stub;

	// @formatter:off
	public static List<EstadoDTO> estadosRepository = new ArrayList<>(Arrays.asList(new EstadoDTO[] {
			EstadoDTO.builder().id(1).codigo("AA").nome("Teste 1").cidades(new ArrayList<>(Arrays.asList(new CidadeDTO[] {CidadeDTO.builder().build()}))).build(),
			EstadoDTO.builder().id(2).codigo("BB").nome("Teste 2").cidades(new ArrayList<>(Arrays.asList(new CidadeDTO[] {CidadeDTO.builder().build()}))).build(),
			EstadoDTO.builder().id(3).codigo("CC").nome("Teste 3").cidades(new ArrayList<>()).build(),
			EstadoDTO.builder().id(4).codigo("DD").nome("Teste 4").cidades(new ArrayList<>()).build()
	}));
	// @formatter:on

	@Bean
	public IEstadosService stubService() {
		if (this.stub == null)
			this.stub = new IEstadosService() {

				@Override
				public EstadoDTO salvar(Integer id, EstadoDTO estado) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));

					estadoEncontrado.setCodigo(estado.getCodigo());
					estadoEncontrado.setNome(estado.getNome());

					return estadoEncontrado;
				}

				@Override
				public List<EstadoDTO> listarTodos() {
					return estadosRepository;
				}

				@Override
				public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) {
					estados.stream().forEach((itemA) -> {
						estadosRepository.stream().filter(item -> item.getId().equals(itemA.getId())).findFirst()
								.ifPresent((item) -> {
									throw new BusinessException("Stub Exception");
								});
					});

					estadosRepository.addAll(estados);

					return estados;
				}

				@Override
				public EstadoDTO adicionar(EstadoDTO estado) {
					estadosRepository.stream().filter(item -> item.getId().equals(estado.getId())).findFirst()
							.ifPresent((item) -> {
								throw new BusinessException("Stub Exception");
							});

					estadosRepository.add(estado);
					return estado;
				}

				@Override
				public EstadoDTO obterPorId(Integer id) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));

					return estadoEncontrado;
				}

				@Override
				public void remover(Integer id) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));

					if (estadoEncontrado.getCidades().size() > 0)
						throw new BusinessException("Stub Exception");

					estadosRepository.remove(estadoEncontrado);
				}
			};
		return this.stub;
	}

}
