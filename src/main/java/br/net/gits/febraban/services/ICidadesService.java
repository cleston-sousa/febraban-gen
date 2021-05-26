package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface ICidadesService {

	public List<CidadeDTO> listarTodas();

	public CidadeDTO obterPorId(Integer id) throws EntityNotFoundException;

	public CidadeDTO adicionar(CidadeDTO cidade) throws BusinessException;

	public CidadeDTO salvar(Integer id, CidadeDTO cidade) throws EntityNotFoundException, BusinessException;

	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId)
			throws EntityNotFoundException, BusinessException;

	public void remover(Integer id) throws EntityNotFoundException;

	public List<CidadeDTO> adicionarLista(List<CidadeDTO> cidades) throws BusinessException;
}
