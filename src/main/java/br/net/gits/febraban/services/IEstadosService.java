package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface IEstadosService {

	public List<EstadoDTO> listarTodos();

	public EstadoDTO obterPorId(Integer id) throws EntityNotFoundException;

	public EstadoDTO adicionar(EstadoDTO estado) throws BusinessException;

	public void remover(Integer id) throws EntityNotFoundException, BusinessException;

	public EstadoDTO salvar(Integer id, EstadoDTO estado) throws EntityNotFoundException;

	public List<EstadoDTO> adicionarLista(List<EstadoDTO> estados) throws BusinessException;

}
