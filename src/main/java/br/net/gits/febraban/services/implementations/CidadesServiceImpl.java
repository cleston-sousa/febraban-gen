package br.net.gits.febraban.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
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
	public CidadeDTO obterPorId(Integer id) {
		var existente = this.cidadesRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cidade nao cadastrada"));
		return ModelMapperUtils.to(existente, CidadeDTO.class);
	}

	@Override
	public CidadeDTO adicionar(CidadeDTO cidade) {

		this.cidadesRepository.findById(cidade.getId()).ifPresent((item) -> {
			throw new BusinessException("Cidade ja cadastrada");
		});

		this.estadosRepository.findById(cidade.getEstado().getId())
				.orElseThrow(() -> new BusinessException("Estado escolhido inconsistente"));

		var novo = ModelMapperUtils.to(cidade, Cidade.class);

		this.cidadesRepository.save(novo);

		return ModelMapperUtils.to(novo, CidadeDTO.class);
	}

	@Override
	public CidadeDTO salvar(Integer id, CidadeDTO cidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remover(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CidadeDTO> adicionarLista(List<CidadeDTO> cidades) {
		// TODO Auto-generated method stub
		return null;
	}

}
