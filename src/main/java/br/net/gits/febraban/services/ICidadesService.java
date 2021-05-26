package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface ICidadesService {

	public List<CidadeDTO> listarTodas();

	public CidadeDTO obterPorId(Integer cidadeId) throws EntityNotFoundException;

	public CidadeDTO adicionar(CidadeDTO cidadeDTO) throws BusinessException;

	public CidadeDTO salvar(Integer cidadeId, CidadeDTO cidadeDTO) throws EntityNotFoundException, BusinessException;

	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId)
			throws EntityNotFoundException, BusinessException;

	public void remover(Integer cidadeId) throws EntityNotFoundException;

	public List<CidadeDTO> adicionarLista(List<CidadeDTO> listCidadeDTO) throws BusinessException;
}
