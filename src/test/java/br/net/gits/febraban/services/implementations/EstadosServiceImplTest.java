package br.net.gits.febraban.services.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.EstadosRepository;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exception.NaoEncontradoException;
import br.net.gits.febraban.services.exception.NegocioException;

@ExtendWith(MockitoExtension.class)
public class EstadosServiceImplTest {

	@Mock
	private EstadosRepository estadosRepository;

	@Autowired
	@InjectMocks
	private EstadosServiceImpl estadosService;

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
	}

	@Test
	void givenEstadoDTO_whenAdicionar_thenReturnEstadoDTO() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
		.thenReturn(Optional.empty());
		
		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());
		// @formatter:on

		var estadoDTO = EstadoDTO.builder().id(1).codigo("TT").nome("Teste").build();

		var result = this.estadosService.adicionar(estadoDTO);

		verify(this.estadosRepository, times(1)).findById(any());
		verify(this.estadosRepository, times(1)).save(any());
		assertThat(result).isInstanceOf(EstadoDTO.class);
	}

	@Test
	void givenEstadoDTOWithIdRepetido_whenAdicionar_thenThrowsNegocioException() {
		// @formatter:off
		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());

		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty())
			.thenReturn(Optional.of(new Estado()));
		// @formatter:on

		var estadoDTO = EstadoDTO.builder().id(1).codigo("TT").nome("Teste").build();

		this.estadosService.adicionar(estadoDTO);

		assertThrows(NegocioException.class, () -> {
			this.estadosService.adicionar(estadoDTO);
		});

	}

	@Test
	void given_whenListarTodos_thenReturnListOfEstadoDTO() {
		// @formatter:off
		var estados = Arrays.asList(new Estado[] { 
				new Estado(), 
				new Estado(), 
				new Estado() });

		when(this.estadosRepository.findAll())
			.thenReturn(estados);
		// @formatter:on

		var result = this.estadosService.listarTodos();

		assertEquals(estados.size(), result.size());
		assertThat(result.get(0)).isInstanceOf(EstadoDTO.class);
	}

	@Test
	void givenEstadoIdAndEstadoDTO_whenSalvar_thenReturnEstadoDTO() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));

		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());
		// @formatter:on

		var estadoDTO = EstadoDTO.builder().codigo("TT").nome("Teste").build();

		var result = this.estadosService.salvar(1, estadoDTO);

		verify(this.estadosRepository, times(1)).findById(any());
		verify(this.estadosRepository, times(1)).save(any());
		assertThat(result).isInstanceOf(EstadoDTO.class);

		assertEquals(estadoDTO.getCodigo(), result.getCodigo());
		assertEquals(estadoDTO.getNome(), result.getNome());
	}

	@Test
	void givenEstadoIdAndEstadoDTO_whenSalvar_thenThrowsNaoEncontradoException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var estadoDTO = EstadoDTO.builder().codigo("TT").nome("Teste").build();

		assertThrows(NaoEncontradoException.class, () -> {
			this.estadosService.salvar(1, estadoDTO);
		});

		verify(this.estadosRepository, times(1)).findById(any());
		verify(this.estadosRepository, times(0)).save(any());
	}

	@Test
	void givenListOfEstadoDTO_whenAdicionarLista_thenReturnListOfEstadoDTO() {
		// @formatter:off
		var estadosDTO = Arrays.asList(new EstadoDTO[] { 
				EstadoDTO.builder().build(),
				EstadoDTO.builder().build(),
				EstadoDTO.builder().build() });

		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		
		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());
		// @formatter:on

		var result = this.estadosService.adicionarLista(estadosDTO);

		verify(this.estadosRepository, times(3)).findById(any());
		verify(this.estadosRepository, times(3)).save(any());
		assertEquals(estadosDTO.size(), result.size());
		assertThat(result.get(0)).isInstanceOf(EstadoDTO.class);
	}

	@Test
	void givenListOfEstadoDTO_whenAdicionarLista_thenThrowsNegocioException() {
		// @formatter:off
		var estadosDTO = Arrays.asList(new EstadoDTO[] { 
				EstadoDTO.builder().build(),
				EstadoDTO.builder().build(),
				EstadoDTO.builder().build() });

		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty())
			.thenReturn(Optional.of(new Estado()));
		
		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());
		// @formatter:on

		assertThrows(NegocioException.class, () -> {
			this.estadosService.adicionarLista(estadosDTO);
		});

		verify(this.estadosRepository, times(2)).findById(any());
		verify(this.estadosRepository, times(1)).save(any());
	}

	@Test
	void givenEstadoId_whenObterPorId_thenReturnEstadoDTO() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));
		// @formatter:on

		var result = this.estadosService.obterPorId(1);

		verify(this.estadosRepository, times(1)).findById(any());
		assertThat(result).isInstanceOf(EstadoDTO.class);
	}

	@Test
	void givenEstadoId_whenObterPorId_thenThrowsNaoEncontradoException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		assertThrows(NaoEncontradoException.class, () -> {
			this.estadosService.obterPorId(1);
		});

		verify(this.estadosRepository, times(1)).findById(any());
	}
}
