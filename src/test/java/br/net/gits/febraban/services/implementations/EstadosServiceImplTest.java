package br.net.gits.febraban.services.implementations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.BusinessException;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class EstadosServiceImplTest {

	@Mock
	private IEstadosRepository estadosRepository;

	@Autowired
	@InjectMocks
	private EstadosServiceImpl estadosService;

	@Test
	void givenEstadoDTO_whenAdicionar_thenReturnEstadoDTO() {
		var estado = new Estado();
		estado.setCodigo("TT");
		estado.setNome("Teste");

		// @formatter:off
		when(this.estadosRepository.findById(any()))
		.thenReturn(Optional.empty());
		
		when(this.estadosRepository.save(any()))
			.thenReturn(estado);
		// @formatter:on

		var estadoId = 100;
		var estadoDTO = EstadoDTO.builder().id(estadoId).codigo("TT").nome("Teste").build();

		var result = this.estadosService.adicionar(estadoDTO);

		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.estadosRepository, times(1)).save(any());

		assertThat(result).isNotNull();
		assertEquals(estadoDTO.getCodigo(), result.getCodigo());
		assertEquals(estadoDTO.getNome(), result.getNome());
	}

	@Test
	void givenEstadoDTO_withIdRepetido_whenAdicionar_thenThrowsBusinessException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));
		// @formatter:on

		var estadoDTO = EstadoDTO.builder().id(1).codigo("TT").nome("Teste").build();

		assertThrows(BusinessException.class, () -> {
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
		var estado = new Estado();
		estado.setCodigo("ZZ");
		estado.setNome("ZZZZZZ");

		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));

		when(this.estadosRepository.save(any()))
			.thenReturn(new Estado());
		// @formatter:on

		var estadoId = 100;
		var estadoDTO = EstadoDTO.builder().codigo("TT").nome("Teste").build();

		var result = this.estadosService.salvar(estadoId, estadoDTO);

		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.estadosRepository, times(1)).save(any());

		assertThat(result).isNotNull();
		assertEquals(estadoDTO.getCodigo(), result.getCodigo());
		assertEquals(estadoDTO.getNome(), result.getNome());
	}

	@Test
	void givenEstadoIdAndEstadoDTO_withEstadoIdInexistente_whenSalvar_thenThrowsEntityNotFoundException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var estadoId = 100;
		var estadoDTO = EstadoDTO.builder().codigo("TT").nome("Teste").build();

		assertThrows(EntityNotFoundException.class, () -> {
			this.estadosService.salvar(estadoId, estadoDTO);
		});

		verify(this.estadosRepository, times(1)).findById(estadoId);
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
	void givenListOfEstadoDTO_withAnyItemHasIdRepetido_whenAdicionarLista_thenThrowsBusinessException() {
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

		assertThrows(BusinessException.class, () -> {
			this.estadosService.adicionarLista(estadosDTO);
		});

		verify(this.estadosRepository, times(2)).findById(any());
		verify(this.estadosRepository, times(1)).save(any());
	}

	@Test
	void givenEstadoId_whenObterPorId_thenReturnEstadoDTO() {
		var estado = new Estado();
		estado.setCodigo("TT");
		estado.setNome("Teste");

		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));
		// @formatter:on

		var estadoId = 100;
		var result = this.estadosService.obterPorId(estadoId);

		verify(this.estadosRepository, times(1)).findById(estadoId);

		assertThat(result).isNotNull();
		assertEquals(estado.getCodigo(), result.getCodigo());
		assertEquals(estado.getNome(), result.getNome());
	}

	@Test
	void givenEstadoId_withEstadoIdInexistente_whenObterPorId_thenThrowsEntityNotFoundException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var estadoId = 100;
		assertThrows(EntityNotFoundException.class, () -> {
			this.estadosService.obterPorId(estadoId);
		});

		verify(this.estadosRepository, times(1)).findById(estadoId);
	}

	@Test
	void givenEstadoId_whenRemover_then() {
		var estado = new Estado();
		estado.setCidade(new ArrayList<>(Arrays.asList(new Cidade[] {})));
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));
		// @formatter:on

		var estadoId = 100;
		this.estadosService.remover(estadoId);

		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.estadosRepository, times(1)).deleteById(estadoId);
	}

	@Test
	void givenEstadoId_withEstadoIdInexistente_whenRemover_thenThrowsEntityNotFoundException() {
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var estadoId = 100;
		assertThrows(EntityNotFoundException.class, () -> {
			this.estadosService.remover(estadoId);
		});

		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.estadosRepository, never()).deleteById(any());
	}

	@Test
	void givenEstadoId_withEstadoComAoMenosUmaCidade_whenRemover_thenThrowsBusinessException() {
		var estado = new Estado();
		estado.setCidade(new ArrayList<>(Arrays.asList(new Cidade[] { new Cidade() })));
		// @formatter:off
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));
		// @formatter:on

		var estadoId = 100;
		assertThrows(BusinessException.class, () -> {
			this.estadosService.remover(estadoId);
		});

		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.estadosRepository, never()).deleteById(any());
	}
}
