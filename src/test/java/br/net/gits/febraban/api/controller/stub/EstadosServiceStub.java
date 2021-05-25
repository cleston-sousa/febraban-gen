package br.net.gits.febraban.api.controller.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;

@TestConfiguration
public class EstadosServiceStub {

	public IEstadosService stub;

	// @formatter:off
	public static List<EstadoDTO> estadosRepository = new ArrayList<>(Arrays.asList(new EstadoDTO[] {
			EstadoDTO.builder().id(1).codigo("AA").nome("Teste 1").build(),
			EstadoDTO.builder().id(2).codigo("BB").nome("Teste 2").build(),
			EstadoDTO.builder().id(3).codigo("CC").nome("Teste 3").build(),
			EstadoDTO.builder().id(4).codigo("DD").nome("Teste 4").build()
	}));
	// @formatter:on

	@Bean
	public IEstadosService stubService() {
		if (this.stub == null)
			this.stub = new IEstadosService() {

				@Override
				public EstadoDTO salvar(Integer id, EstadoDTO estado) throws NaoEncontradoException {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new NaoEncontradoException("Stub Exception"));

					estadoEncontrado.setCodigo(estado.getCodigo());
					estadoEncontrado.setNome(estado.getNome());

					return estadoEncontrado;
				}

				@Override
				public List<EstadoDTO> listarTodos() {
					return estadosRepository;
				}

				@Override
				public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) throws NegocioException {
					estados.stream().forEach((itemA) -> {
						estadosRepository.stream().filter(item -> item.getId().equals(itemA.getId())).findFirst()
								.ifPresent((item) -> {
									throw new NegocioException("Stub Exception");
								});
					});

					estadosRepository.addAll(estados);

					return estados;
				}

				@Override
				public EstadoDTO adicionar(EstadoDTO estado) throws NegocioException {
					estadosRepository.stream().filter(item -> item.getId().equals(estado.getId())).findFirst()
							.ifPresent((item) -> {
								throw new NegocioException("Stub Exception");
							});

					estadosRepository.add(estado);
					return estado;
				}

				@Override
				public EstadoDTO obterPorId(Integer id) throws NaoEncontradoException {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new NaoEncontradoException("Stub Exception"));

					return estadoEncontrado;
				}

				@Override
				public void remover(Integer id) throws NaoEncontradoException, NegocioException {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new NaoEncontradoException("Stub Exception"));

					estadosRepository.remove(estadoEncontrado);
				}
			};
		return this.stub;
	}

}
