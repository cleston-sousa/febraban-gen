package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.AdicionarEstadoDTO;
import br.net.gits.febraban.services.dtos.AlterarEstadoDTO;
import br.net.gits.febraban.services.dtos.EstadoCidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface IEstadosService {

	public List<EstadoDTO> listarTodos();

	public EstadoCidadeDTO obterPorId(Integer estadoId) throws EntityNotFoundException;

	public EstadoCidadeDTO obterPorCodigo(String estadoCodigo) throws EntityNotFoundException;

	public List<EstadoDTO> obterContendoNome(String estadoNome);

	public EstadoDTO adicionar(AdicionarEstadoDTO estadoDTO) throws BusinessException;

	public void remover(Integer estadoId) throws EntityNotFoundException, BusinessException;

	public EstadoDTO salvar(Integer estadoId, AlterarEstadoDTO estadoDTO) throws EntityNotFoundException;

	public List<EstadoDTO> adicionarLista(List<AdicionarEstadoDTO> listEstadoDTO) throws BusinessException;

}
