package br.net.gits.febraban.api.controller.stub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import br.net.gits.febraban.services.ICidadesService;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

@TestConfiguration
public class CidadesServiceStub {

	public ICidadesService stub;

	public static List<CidadeDTO> cidadesRepository = new ArrayList<>(Arrays.asList(new CidadeDTO[] {}));

	@Bean
	public ICidadesService stubService() {

		if (this.stub == null)
			this.stub = new ICidadesService() {

				@Override
				public CidadeDTO salvar(Integer cidadeId, CidadeDTO cidadeDTO)
						throws EntityNotFoundException, BusinessException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void remover(Integer cidadeId) throws EntityNotFoundException {
					// TODO Auto-generated method stub

				}

				@Override
				public CidadeDTO obterPorId(Integer cidadeId) throws EntityNotFoundException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public List<CidadeDTO> listarTodas() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public CidadeDTO alterarEstado(Integer cidadeId, Integer estadoId)
						throws EntityNotFoundException, BusinessException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public List<CidadeDTO> adicionarLista(List<CidadeDTO> listCidadeDTO) throws BusinessException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public CidadeDTO adicionar(CidadeDTO cidadeDTO) throws BusinessException {
					// TODO Auto-generated method stub
					return null;
				}
			};
		return this.stub;
	}

}
