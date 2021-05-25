package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;

public interface IEstadosService {

	public List<EstadoDTO> listarTodos();

	public EstadoDTO obterPorId(Integer id) throws NaoEncontradoException;

	public EstadoDTO adicionar(EstadoDTO estado) throws NegocioException;

	public void remover(Integer id) throws NaoEncontradoException, NegocioException;

	public EstadoDTO salvar(Integer id, EstadoDTO estado) throws NaoEncontradoException;

	public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) throws NegocioException;

}
