package br.net.gits.febraban.services.implementations;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.net.gits.febraban.persistence.repositories.ICidadesRepository;

@ExtendWith(MockitoExtension.class)
public class CidadesServiceImplTest {

	@Mock
	private ICidadesRepository cidadesRepository;

	@Autowired
	@InjectMocks
	private CidadesServiceImpl cidadesService;

	@Test
	void given_whenListarTodos_thenReturnListOfCidadeDTO() {
	}

	@Test
	void givenCidadeId_whenObterPorId_thenReturnCidadeDTO() {
	}

	@Test
	void givenCidadeId_whenObterPorId_thenThrowsNaoEncontradoException() {
	}

	@Test
	void givenCidadeDTO_whenAdicionar_thenReturnCidadeDTO() {
	}

	@Test
	void givenCidadeDTO_withCidadeIdRepetido_whenAdicionar_thenThrowsNegocioException() {
	}

	@Test
	void givenCidadeDTO_withEstadoIdInexistente_whenAdicionar_thenThrowsNegocioException() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_whenSalvar_thenReturnCidadeDTO() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withCidadeIdInexistente_whenSalvar_thenThrowsNaoEncontradoException() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withEstadoIdInexistente_whenSalvar_thenThrowsNegocioException() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_whenAlterarEstado_thenReturnCidadeDTO() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withCidadeIdInexistente_whenAlterarEstado_thenThrowsNaoEncontradoException() {
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withEstadoIdInexistente_whenAlterarEstado_thenThrowsNegocioException() {
	}

	@Test
	void givenCidadeId_whenRemover_then() {
	}

	@Test
	void givenCidadeId_withCidadeIdInexistente_whenRemover_thenThrowsNaoEncontradoException() {
	}

	@Test
	void givenListOfCidadeDTO_whenAdicionarLista_thenReturnListOfCidadeDTO() {
	}

	@Test
	void givenListOfCidadeDTO_withAnyItemHasIdRepetido_whenAdicionarLista_thenThrowsNegocioException() {
	}

}
