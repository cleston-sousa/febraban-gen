package br.net.gits.febraban.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
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
	public CidadeDTO adicionar(CidadeDTO cidadeDTO) {

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
	public CidadeDTO salvar(Integer cidadeId, CidadeDTO cidadeDTO) {

		var cidadePersisted = this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));

		this.estadosRepository.findById(cidadeDTO.getEstado().getId())
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));

		cidadeDTO.setId(null);

		ModelMapperUtils.set(cidadePersisted, cidadeDTO);

		this.cidadesRepository.save(cidadePersisted);

		return ModelMapperUtils.to(cidadePersisted, CidadeDTO.class);
	}

	@Override
	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId) {

		var cidadePersisted = this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));

		var estadoPersisted = this.estadosRepository.findById(estadoId)
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));

		cidadePersisted.setEstado(estadoPersisted);

		this.cidadesRepository.save(cidadePersisted);

		return ModelMapperUtils.to(cidadePersisted, CidadeDTO.class);
	}

	@Override
	public void remover(Integer cidadeId) {
		this.cidadesRepository.findById(cidadeId)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));

		this.cidadesRepository.deleteById(cidadeId);
	}

	@Override
	public List<CidadeDTO> adicionarLista(List<CidadeDTO> listCidadeDTO) {
		var result = listCidadeDTO.stream().map(item -> this.adicionar(item)).collect(Collectors.toList());
		return result;
	}

}
