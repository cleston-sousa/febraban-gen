package br.net.gits.febraban.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;

@Service
public class CidadesServiceImpl implements ICidadesService {

	private ICidadesRepository cidadesRepository;

	public CidadesServiceImpl(ICidadesRepository cidadesRepository) {
		this.cidadesRepository = cidadesRepository;
	}

	@Override
	public List<CidadeDTO> listarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CidadeDTO obterPorId(Integer id) throws NaoEncontradoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CidadeDTO adicionar(CidadeDTO cidade) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CidadeDTO salvar(Integer id, CidadeDTO cidade) throws NaoEncontradoException, NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId) throws NaoEncontradoException, NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Integer id) throws NaoEncontradoException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CidadeDTO> adicionarLista(List<CidadeDTO> cidades) throws NegocioException {
		// TODO Auto-generated method stub
		return null;
	}

}
