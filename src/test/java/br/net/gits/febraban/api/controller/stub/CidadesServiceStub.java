package br.net.gits.febraban.api.controller.stub;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.net.gits.FixedLengthReader;
import br.net.gits.febraban.api.controller.utils.TestUtils;
import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.AdicionarCidadeDTO;
import br.net.gits.febraban.services.dtos.AlterarCidadeDTO;
import br.net.gits.febraban.services.dtos.ArquivoCidadeDTO;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.dtos.CidadeEstadoDTO;
import br.net.gits.febraban.services.dtos.EstadoIdDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
import br.net.gits.febraban.utils.ModelMapperUtils;

@TestConfiguration
public class CidadesServiceStub {

	public ICidadesService stub;

	public static List<Cidade> cidadesRepository;

	public static List<Estado> estadosRepository;

	public static String cidadesJson;

	public static String estadosJson;

	public static ObjectMapper mapper;

	public CidadesServiceStub() {
		cidadesJson = TestUtils.getContentFromResource("/json/cidades/cidades_stub.json");
		estadosJson = TestUtils.getContentFromResource("/json/estados/estados_stub.json");
		mapper = new ObjectMapper();
	}

	public static void reset() {
		try {
			TypeReference<List<Cidade>> typeRef = new TypeReference<List<Cidade>>() {
			};
			cidadesRepository = mapper.readValue(cidadesJson, typeRef);
			TypeReference<List<Estado>> typeRef2 = new TypeReference<List<Estado>>() {
			};
			estadosRepository = mapper.readValue(estadosJson, typeRef2);
		} catch (Exception e) {
		}
	}

	@Bean
	public ICidadesService stubService() {

		if (this.stub == null)
			this.stub = new ICidadesService() {

				@Override
				public CidadeDTO salvar(Integer cidadeId, AlterarCidadeDTO cidadeDTO)
						throws EntityNotFoundException, BusinessException {
					var cidadeEncontrada = cidadesRepository.stream().filter(item -> item.getId().equals(cidadeId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Error"));
					ModelMapperUtils.set(cidadeEncontrada, cidadeDTO);
					return ModelMapperUtils.to(cidadeEncontrada, CidadeDTO.class);
				}

				@Override
				public void remover(Integer cidadeId) throws EntityNotFoundException {
					var cidadeEncontrada = cidadesRepository.stream().filter(item -> item.getId().equals(cidadeId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Error"));
					cidadesRepository.remove(cidadeEncontrada);
				}

				@Override
				public CidadeDTO obterPorId(Integer cidadeId) throws EntityNotFoundException {
					var cidadeEncontrada = cidadesRepository.stream().filter(item -> item.getId().equals(cidadeId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Error"));
					return ModelMapperUtils.to(cidadeEncontrada, CidadeDTO.class);
				}

				@Override
				public List<CidadeDTO> listarTodas() {
					return ModelMapperUtils.toList(cidadesRepository, CidadeDTO.class);
				}

				@Override
				public CidadeEstadoDTO alterarEstado(Integer cidadeId, Integer estadoId)
						throws EntityNotFoundException, BusinessException {
					var cidadeEncontrada = cidadesRepository.stream().filter(item -> item.getId().equals(cidadeId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Error"));
					var estadoEncontrado = estadosRepository.stream().filter(item -> item.getId().equals(estadoId))
							.findFirst().orElseThrow(() -> new EntityNotFoundException("Stub Exception"));
					cidadeEncontrada.setEstado(estadoEncontrado);
					return ModelMapperUtils.to(cidadeEncontrada, CidadeEstadoDTO.class);
				}

				@Override
				public List<CidadeDTO> adicionarLista(List<AdicionarCidadeDTO> listCidadeDTO) throws BusinessException {
					listCidadeDTO.stream().forEach((itemA) -> {
						cidadesRepository.stream().filter(item -> item.getId().equals(itemA.getId())).findFirst()
								.ifPresent((item) -> {
									throw new BusinessException("Stub Exception");
								});
					});
					var cidadesNovas = listCidadeDTO.stream().map((cidade) -> {
						var cidadeNova = ModelMapperUtils.to(cidade, Cidade.class);
						cidadesRepository.add(cidadeNova);
						return cidadeNova;
					}).collect(Collectors.toList());
					return ModelMapperUtils.toList(cidadesNovas, CidadeDTO.class);
				}

				@Override
				public CidadeDTO adicionar(AdicionarCidadeDTO cidadeDTO) throws BusinessException {
					cidadesRepository.stream().filter(item -> item.getId().equals(cidadeDTO.getId())).findFirst()
							.ifPresent((item) -> {
								throw new BusinessException("Stub Exception");
							});

					var cidadeNova = ModelMapperUtils.to(cidadeDTO, Cidade.class);
					cidadesRepository.add(cidadeNova);
					return ModelMapperUtils.to(cidadeNova, CidadeDTO.class);
				}

				@Override
				public List<CidadeDTO> obterPorEstado(EstadoIdDTO estadoIdDTO) {
					var cidadesEncontrada = cidadesRepository.stream()
							.filter(item -> item.getEstado().getId().equals(estadoIdDTO.getId()))
							.collect(Collectors.toList());
					return ModelMapperUtils.toList(cidadesEncontrada, CidadeDTO.class);
				}

				@Override
				public List<CidadeDTO> obterContendoNome(String cidadeNome) {
					if (StringUtils.isBlank(cidadeNome))
						return ModelMapperUtils.toList(cidadesRepository, CidadeDTO.class);
					var trimmed = cidadeNome.trim();
					var cidadesEncontrado = cidadesRepository.stream()
							.filter(item -> StringUtils.containsIgnoreCase(item.getNome(), trimmed))
							.collect(Collectors.toList());
					return ModelMapperUtils.toList(cidadesEncontrado, CidadeDTO.class);
				}

				@Override
				public List<CidadeDTO> importarArquivoTamanhoFixo(InputStream arquivoInputStream)
						throws BusinessException {
					List<CidadeDTO> result = new ArrayList<CidadeDTO>();
					int linha = 0;
					try {
						var reader = FixedLengthReader.init().input(arquivoInputStream).mapper(ArquivoCidadeDTO.class)
								.open();
						while (reader.hasNext()) {
							var extraido = (ArquivoCidadeDTO) reader.read();
							linha = reader.getLine();
							var cidade = ModelMapperUtils.to(extraido, AdicionarCidadeDTO.class);
							cidade.setEstado(new EstadoIdDTO());
							cidade.getEstado().setId(extraido.getEstado());
							result.add(this.adicionar(cidade));
						}
					} catch (BusinessException e) {
						throw new BusinessException(String.format("Erro na linha %d", linha), e);
					} catch (Exception e) {
						throw new BusinessException("Erro processando o arquivo", e);
					}
					return result;
				}
			};
		return this.stub;
	}

}
