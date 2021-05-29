package br.net.gits.febraban.api.controller.stub;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.net.gits.febraban.api.controller.utils.TestUtils;
import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.AdicionarEstadoDTO;
import br.net.gits.febraban.services.dtos.AlterarEstadoDTO;
import br.net.gits.febraban.services.dtos.EstadoCidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
import br.net.gits.febraban.utils.ModelMapperUtils;

@TestConfiguration
public class EstadosServiceStub {

	public IEstadosService stub;

	public static List<Estado> estadosRepository;

	public static String estadosJson;

	public static String cidadesJson;

	public static ObjectMapper mapper;

	public EstadosServiceStub() {
		estadosJson = TestUtils.getContentFromResource("/json/estados/estados_stub.json");
		cidadesJson = TestUtils.getContentFromResource("/json/cidades/cidades_stub.json");
		mapper = new ObjectMapper();
	}

	public static void reset() {
		try {
			TypeReference<List<Estado>> typeRef = new TypeReference<List<Estado>>() {
			};
			estadosRepository = mapper.readValue(estadosJson, typeRef);

			estadosRepository.forEach(estado -> {
				if (estado.getId() == 35) {
					try {
						TypeReference<List<Cidade>> typeRef2 = new TypeReference<List<Cidade>>() {
						};
						estado.setCidade(mapper.readValue(cidadesJson, typeRef2));
					} catch (Exception e) {
					}
				}
			});
		} catch (Exception e) {
		}
	}

	@Bean
	public IEstadosService stubService() {
		if (this.stub == null)
			this.stub = new IEstadosService() {

				@Override
				public EstadoDTO salvar(Integer id, AlterarEstadoDTO estadoDTO) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(id))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));
					ModelMapperUtils.set(estadoEncontrado, estadoDTO);
					return ModelMapperUtils.to(estadoEncontrado, EstadoDTO.class);
				}

				@Override
				public List<EstadoDTO> listarTodos() {
					return ModelMapperUtils.toList(estadosRepository, EstadoDTO.class);
				}

				@Override
				public List<EstadoDTO> adicionarLista(List<AdicionarEstadoDTO> estados) {
					estados.stream().forEach((itemA) -> {
						estadosRepository.stream().filter(item -> item.getId().equals(itemA.getId())).findFirst()
								.ifPresent((item) -> {
									throw new BusinessException("Stub Exception");
								});
					});

					var estadosNovos = estados.stream().map((estado) -> {
						var estadoNovo = ModelMapperUtils.to(estado, Estado.class);
						estadosRepository.add(estadoNovo);
						return estadoNovo;
					}).collect(Collectors.toList());

					return ModelMapperUtils.toList(estadosNovos, EstadoDTO.class);

				}

				@Override
				public EstadoDTO adicionar(AdicionarEstadoDTO estadoDTO) {
					estadosRepository.stream().filter(item -> item.getId().equals(estadoDTO.getId())).findFirst()
							.ifPresent((item) -> {
								throw new BusinessException("Stub Exception");
							});

					var estadoNovo = ModelMapperUtils.to(estadoDTO, Estado.class);
					estadosRepository.add(estadoNovo);
					return ModelMapperUtils.to(estadoNovo, EstadoDTO.class);
				}

				@Override
				public EstadoCidadeDTO obterPorId(Integer estadoId) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(estadoId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));
					return ModelMapperUtils.to(estadoEncontrado, EstadoCidadeDTO.class);
				}

				@Override
				public void remover(Integer estadoId) {
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(estadoId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));
					if (estadoEncontrado.getCidade().size() > 0)
						throw new BusinessException("Stub Exception");
					estadosRepository.remove(estadoEncontrado);
				}

				@Override
				public EstadoCidadeDTO obterPorCodigo(String estadoCodigo) throws EntityNotFoundException {
					if (StringUtils.isBlank(estadoCodigo))
						throw new BusinessException("Stub Exception");

					var estadoEncontrado = estadosRepository.stream()
							.filter(item -> item.getCodigo().equals(estadoCodigo)).findFirst()
							.orElseThrow(() -> new EntityNotFoundException("Stub Exception"));

					return ModelMapperUtils.to(estadoEncontrado, EstadoCidadeDTO.class);
				}

				@Override
				public List<EstadoDTO> obterContendoNome(String estadoNome) {
					if (StringUtils.isBlank(estadoNome))
						return ModelMapperUtils.toList(estadosRepository, EstadoDTO.class);
					var trimmed = estadoNome.trim();
					var estadosEncontrado = estadosRepository.stream()
							.filter(item -> StringUtils.containsIgnoreCase(item.getNome(), trimmed))
							.collect(Collectors.toList());
					return ModelMapperUtils.toList(estadosEncontrado, EstadoDTO.class);
				}
			};
		return this.stub;
	}

}
