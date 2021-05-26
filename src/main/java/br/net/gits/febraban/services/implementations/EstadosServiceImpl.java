package br.net.gits.febraban.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.IEstadosService;
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
	public EstadoDTO salvar(Integer id, EstadoDTO estado) {

		var existente = this.estadosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));

		estado.setId(null);

		ModelMapperUtils.set(existente, estado);

		this.estadosRepository.save(existente);

		return ModelMapperUtils.to(existente, EstadoDTO.class);
	}

	@Override
	@Transactional
	public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) {
		var resultado = estados.stream().map(item -> this.adicionar(item)).collect(Collectors.toList());
		return resultado;
	}

	@Override
	@Transactional
	public EstadoDTO adicionar(EstadoDTO estado) {

		this.estadosRepository.findById(estado.getId()).ifPresent(item -> {
			throw new BusinessException("Codigo de estado cadastrado");
		});

		var novo = ModelMapperUtils.to(estado, Estado.class);

		var resultado = this.estadosRepository.save(novo);

		return ModelMapperUtils.to(resultado, EstadoDTO.class);
	}

	@Override
	public EstadoDTO obterPorId(Integer id) {
		var existente = this.estadosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));

		return ModelMapperUtils.to(existente, EstadoDTO.class);
	}

	@Override
	public void remover(Integer id) {

		var existente = this.estadosRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Estado nao cadastrado"));

		if (existente.getCidade().size() > 0)
			throw new BusinessException("Estado nao cadastrado");

		this.estadosRepository.deleteById(id);
	}

}
