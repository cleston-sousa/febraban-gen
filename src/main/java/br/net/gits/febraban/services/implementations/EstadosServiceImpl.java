package br.net.gits.febraban.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.AdicionarEstadoDTO;
import br.net.gits.febraban.services.dtos.AlterarEstadoDTO;
import br.net.gits.febraban.services.dtos.EstadoCidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
import br.net.gits.febraban.utils.ModelMapperUtils;

@Service
public class EstadosServiceImpl implements IEstadosService {

	private IEstadosRepository estadosRepository;

	public EstadosServiceImpl(IEstadosRepository estadosRepository) {
		this.estadosRepository = estadosRepository;
	}

	@Override
	public List<EstadoDTO> listarTodos() {
		return ModelMapperUtils.toList(this.estadosRepository.findAll(), EstadoDTO.class);
	}

	@Override
	@Transactional
	public EstadoDTO salvar(Integer estadoId, AlterarEstadoDTO estadoDTO) {
		var estadoPersisted = this.estadosRepository.findById(estadoId)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));
		ModelMapperUtils.set(estadoPersisted, estadoDTO);
		this.estadosRepository.save(estadoPersisted);
		return ModelMapperUtils.to(estadoPersisted, EstadoDTO.class);
	}

	@Override
	@Transactional
	public List<EstadoDTO> adicionarLista(List<AdicionarEstadoDTO> listEstadoDTO) {
		var result = listEstadoDTO.stream().map(item -> this.adicionar(item)).collect(Collectors.toList());
		return result;
	}

	@Override
	@Transactional
	public EstadoDTO adicionar(AdicionarEstadoDTO estadoDTO) {
		this.estadosRepository.findById(estadoDTO.getId()).ifPresent(item -> {
			throw new BusinessException("Codigo de estado cadastrado");
		});
		var estadoDetached = ModelMapperUtils.to(estadoDTO, Estado.class);
		var estadoPersisted = this.estadosRepository.save(estadoDetached);
		return ModelMapperUtils.to(estadoPersisted, EstadoDTO.class);
	}

	@Override
	public EstadoCidadeDTO obterPorId(Integer estadoId) {
		var estadoPersisted = this.estadosRepository.findById(estadoId)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));
		return ModelMapperUtils.to(estadoPersisted, EstadoCidadeDTO.class);
	}

	@Override
	public void remover(Integer estadoId) {
		var estadoPersisted = this.estadosRepository.findById(estadoId)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));
		if (estadoPersisted.getCidade().size() > 0)
			throw new BusinessException("Estado nao cadastrado");
		this.estadosRepository.deleteById(estadoId);
	}

	@Override
	public EstadoCidadeDTO obterPorCodigo(String estadoCodigo) {
		return ModelMapperUtils.to(this.estadosRepository.findFirstByCodigoIgnoreCase(estadoCodigo),
				EstadoCidadeDTO.class);
	}

	@Override
	public List<EstadoDTO> obterContendoNome(String estadoNome) {
		return ModelMapperUtils.toList(this.estadosRepository.findByNomeContainingIgnoreCase(estadoNome),
				EstadoDTO.class);
	}

}
