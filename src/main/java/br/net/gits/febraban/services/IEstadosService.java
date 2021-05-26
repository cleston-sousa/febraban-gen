package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface IEstadosService {

	public List<EstadoDTO> listarTodos();

	public EstadoDTO obterPorId(Integer estadoId) throws EntityNotFoundException;

	public EstadoDTO adicionar(EstadoDTO estadoDTO) throws BusinessException;

	public void remover(Integer estadoId) throws EntityNotFoundException, BusinessException;

	public EstadoDTO salvar(Integer estadoId, EstadoDTO estadoDTO) throws EntityNotFoundException;

	public List<EstadoDTO> adicionarLista(List<EstadoDTO> listEstadoDTO) throws BusinessException;

}
