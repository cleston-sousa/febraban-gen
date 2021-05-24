package br.net.gits.febraban.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.EstadosRepository;
import br.net.gits.febraban.services.IEstadosService;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;
import br.net.gits.febraban.utils.ModelMapperUtils;

@Service
public class EstadosServiceImpl implements IEstadosService {

	private EstadosRepository estadosRepository;

	public EstadosServiceImpl(EstadosRepository estadosRepository) {
		this.estadosRepository = estadosRepository;
	}

	@Override
	public List<EstadoDTO> listarTodos() {
		return ModelMapperUtils.toList(this.estadosRepository.findAll(), EstadoDTO.class);
	}

	@Override
	@Transactional
	public EstadoDTO salvar(Integer id, EstadoDTO estado) throws NaoEncontradoException {

		var existente = this.estadosRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Estado nao cadastrado"));

		estado.setId(null);

		ModelMapperUtils.set(existente, estado);

		this.estadosRepository.save(existente);

		return ModelMapperUtils.to(existente, EstadoDTO.class);
	}

	@Override
	@Transactional
	public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) throws NegocioException {
		var resultado = estados.stream().map(item -> this.adicionar(item)).collect(Collectors.toList());
		return resultado;
	}

	@Override
	@Transactional
	public EstadoDTO adicionar(EstadoDTO estado) throws NegocioException {

		this.estadosRepository.findById(estado.getId()).ifPresent(item -> {
			throw new NegocioException("Codigo de estado cadastrado");
		});

		var novo = ModelMapperUtils.to(estado, Estado.class);

		var resultado = this.estadosRepository.save(novo);

		return ModelMapperUtils.to(resultado, EstadoDTO.class);
	}

	@Override
	public EstadoDTO obterPorId(Integer id) throws NaoEncontradoException {
		var existente = this.estadosRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Estado nao cadastrado"));

		return ModelMapperUtils.to(existente, EstadoDTO.class);
	}

}
