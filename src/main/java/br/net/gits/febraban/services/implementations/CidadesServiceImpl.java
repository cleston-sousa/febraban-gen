package br.net.gits.febraban.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;
import br.net.gits.febraban.utils.ModelMapperUtils;

@Service
public class CidadesServiceImpl implements ICidadesService {

	private ICidadesRepository cidadesRepository;

	private IEstadosRepository estadosRepository;

	public CidadesServiceImpl(ICidadesRepository cidadesRepository, IEstadosRepository estadosRepository) {
		this.cidadesRepository = cidadesRepository;
		this.estadosRepository = estadosRepository;
	}

	@Override
	public List<CidadeDTO> listarTodas() {
		return ModelMapperUtils.toList(this.cidadesRepository.findAll(), CidadeDTO.class);
	}

	@Override
	public CidadeDTO obterPorId(Integer id) throws NaoEncontradoException {
		var existente = this.cidadesRepository.findById(id)
				.orElseThrow(() -> new NaoEncontradoException("Cidade nao cadastrada"));
		return ModelMapperUtils.to(existente, CidadeDTO.class);
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
