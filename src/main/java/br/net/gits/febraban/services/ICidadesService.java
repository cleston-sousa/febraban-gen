package br.net.gits.febraban.services;

import java.util.List;

import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;

public interface ICidadesService {

	public List<CidadeDTO> listarTodos();

	public CidadeDTO obterPorId(Integer id) throws NaoEncontradoException;

	public CidadeDTO adicionar(CidadeDTO cidade) throws NegocioException;

	public CidadeDTO salvar(Integer id, CidadeDTO cidade) throws NaoEncontradoException, NegocioException;

	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId) throws NaoEncontradoException, NegocioException;

	public void remover(Integer id) throws NaoEncontradoException;

	public List<CidadeDTO> adicionarLista(List<CidadeDTO> cidades) throws NegocioException;
}
