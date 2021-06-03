package br.net.gits.febraban.services;

import java.io.InputStream;
import java.util.List;

import br.net.gits.febraban.services.dtos.AdicionarCidadeDTO;
import br.net.gits.febraban.services.dtos.AlterarCidadeDTO;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.dtos.CidadeEstadoDTO;
import br.net.gits.febraban.services.dtos.EstadoIdDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

public interface ICidadesService {

	public List<CidadeDTO> listarTodas();

	public List<CidadeDTO> obterPorEstado(EstadoIdDTO estadoIdDTO);

	public List<CidadeDTO> obterContendoNome(String cidadeNome);

	public CidadeDTO obterPorId(Integer cidadeId) throws EntityNotFoundException;

	public CidadeDTO adicionar(AdicionarCidadeDTO cidadeDTO) throws BusinessException;

	public CidadeDTO salvar(Integer cidadeId, AlterarCidadeDTO cidadeDTO)
			throws EntityNotFoundException, BusinessException;

	public CidadeEstadoDTO alterarEstado(Integer cidadeId, Integer estadoId)
			throws EntityNotFoundException, BusinessException;

	public void remover(Integer cidadeId) throws EntityNotFoundException;

	public List<CidadeDTO> adicionarLista(List<AdicionarCidadeDTO> listCidadeDTO) throws BusinessException;

	public List<CidadeDTO> importarArquivoTamanhoFixo(InputStream arquivoInputStream) throws BusinessException;
}
