package br.net.gits.febraban.services.implementations;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.gits.FixedLengthReader;
import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
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

@Service
public class CidadesServiceImpl implements ICidadesService {

	private ICidadesRepository cidadesRepository;

	private IEstadosRepository estadosRepository;

	public CidadesServiceImpl(ICidadesRepository cidadesRepository, IEstadosRepository estadosRepository) {
		this.cidadesRepository = cidadesRepository;
		this.estadosRepository = estadosRepository;
	}

	@Override
	public List<CidadeDTO> listarTodas() {
		return ModelMapperUtils.toList(this.cidadesRepository.findAll(), CidadeDTO.class);
	}

	@Override
	public CidadeDTO obterPorId(Integer cidadeId) {
		var cidadePersisted = this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));
		return ModelMapperUtils.to(cidadePersisted, CidadeDTO.class);
	}

	@Override
	@Transactional
	public CidadeDTO adicionar(AdicionarCidadeDTO cidadeDTO) {
		this.cidadesRepository.findById(cidadeDTO.getId()).ifPresent((item) -> {
			throw new BusinessException("Cidade ja cadastrada");
		});
		this.estadosRepository.findById(cidadeDTO.getEstado().getId())
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));
		var cidadeDetached = ModelMapperUtils.to(cidadeDTO, Cidade.class);
		var cidadePersisted = this.cidadesRepository.save(cidadeDetached);
		return ModelMapperUtils.to(cidadePersisted, CidadeDTO.class);
	}

	@Override
	@Transactional
	public CidadeDTO salvar(Integer cidadeId, AlterarCidadeDTO cidadeDTO) {
		var cidadePersisted = this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));
		this.estadosRepository.findById(cidadeDTO.getEstado().getId())
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));
		ModelMapperUtils.set(cidadePersisted, cidadeDTO);
		this.cidadesRepository.save(cidadePersisted);
		return ModelMapperUtils.to(cidadePersisted, CidadeDTO.class);
	}

	@Override
	@Transactional
	public CidadeEstadoDTO alterarEstado(Integer cidadeId, Integer estadoId) {
		var cidadePersisted = this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));
		var estadoPersisted = this.estadosRepository.findById(estadoId)
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));
		cidadePersisted.setEstado(estadoPersisted);
		this.cidadesRepository.save(cidadePersisted);
		return ModelMapperUtils.to(cidadePersisted, CidadeEstadoDTO.class);
	}

	@Override
	@Transactional
	public void remover(Integer cidadeId) {
		this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));
		this.cidadesRepository.deleteById(cidadeId);
	}

	@Override
	@Transactional
	public List<CidadeDTO> adicionarLista(List<AdicionarCidadeDTO> listCidadeDTO) {
		var result = listCidadeDTO.stream().map(item -> this.adicionar(item)).collect(Collectors.toList());
		return result;
	}

	@Override
	public List<CidadeDTO> obterPorEstado(EstadoIdDTO estadoIdDTO) {
		var estado = ModelMapperUtils.to(estadoIdDTO, Estado.class);
		return ModelMapperUtils.toList(this.cidadesRepository.findByEstado(estado), CidadeDTO.class);
	}

	@Override
	public List<CidadeDTO> obterContendoNome(String cidadeNome) {
		return ModelMapperUtils.toList(this.cidadesRepository.findByNomeContainingIgnoreCase(cidadeNome),
				CidadeDTO.class);
	}

	@Override
	@Transactional
	public List<CidadeDTO> importarArquivoTamanhoFixo(InputStream arquivoInputStream) throws BusinessException {
		List<CidadeDTO> result = new ArrayList<CidadeDTO>();
		int linha = 0;
		try {
			var reader = FixedLengthReader.init().input(arquivoInputStream).mapper(ArquivoCidadeDTO.class).open();
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

}
