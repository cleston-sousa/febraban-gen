package br.net.gits.febraban.services.implementations;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import br.net.gits.febraban.persistence.repositories.ICidadesRepository;
import br.net.gits.febraban.persistence.repositories.IEstadosRepository;
import br.net.gits.febraban.services.dtos.AdicionarCidadeDTO;
import br.net.gits.febraban.services.dtos.AlterarCidadeDTO;
import br.net.gits.febraban.services.dtos.CidadeDTO;
import br.net.gits.febraban.services.dtos.EstadoDTO;
import br.net.gits.febraban.services.exceptions.EntityNotFoundException;
import br.net.gits.febraban.services.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
public class CidadesServiceImplTest {

	@Mock
	private ICidadesRepository cidadesRepository;

	@Mock
	private IEstadosRepository estadosRepository;

	@Autowired
	@InjectMocks
	private CidadesServiceImpl cidadesService;

	@Test
	void given_whenListarTodos_thenReturnListOfCidadeDTO() {
		// @formatter:off
		var cidades = Arrays.asList(new Cidade[] { 
				new Cidade(), 
				new Cidade(), 
				new Cidade() });

		when(this.cidadesRepository.findAll())
			.thenReturn(cidades);
		// @formatter:on

		var result = this.cidadesService.listarTodas();

		assertEquals(cidades.size(), result.size());
		assertThat(result.get(0)).isInstanceOf(CidadeDTO.class);
	}

	@Test
	void givenCidadeId_whenObterPorId_thenReturnCidadeDTO() {
		var cidade = new Cidade();
		cidade.setNome("Teste");

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(cidade));
		// @formatter:on

		var cidadeId = 100;
		var result = this.cidadesService.obterPorId(cidadeId);

		verify(this.cidadesRepository, times(1)).findById(cidadeId);

		assertThat(result).isNotNull();
		assertEquals(cidade.getNome(), result.getNome());
	}

	@Test
	void givenCidadeId_withCidadeIdInexistente_whenObterPorId_thenThrowsEntityNotFoundException() {
		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var cidadeId = 100;
		assertThrows(EntityNotFoundException.class, () -> {
			this.cidadesService.obterPorId(cidadeId);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
	}

	@Test
	void givenCidadeDTO_whenAdicionar_thenReturnCidadeDTO() {
		var estadoId = 55;
		var cidade = new Cidade();
		cidade.setNome("Teste");

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));
		
		when(this.cidadesRepository.save(any()))
			.thenReturn(cidade);
		// @formatter:on

		var cidadeId = 100;
		var cidadeDTO = AdicionarCidadeDTO.builder().id(cidadeId).nome("Teste")
				.estado(EstadoDTO.builder().id(estadoId).build()).build();

		var result = this.cidadesService.adicionar(cidadeDTO);

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.cidadesRepository, times(1)).save(any());

		assertThat(result).isNotNull();
		assertEquals(cidadeDTO.getNome(), result.getNome());
	}

	@Test
	void givenCidadeDTO_withCidadeIdRepetido_whenAdicionar_thenThrowsBusinessException() {
		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(new Cidade()));
		// @formatter:on

		var cidadeDTO = AdicionarCidadeDTO.builder().id(1).nome("Teste").build();

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.adicionar(cidadeDTO);
		});

		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeDTO_withEstadoIdInexistente_whenAdicionar_thenThrowsBusinessException() {
		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());

		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var cidadeDTO = AdicionarCidadeDTO.builder().id(1).nome("Teste").estado(EstadoDTO.builder().id(2).build())
				.build();

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.adicionar(cidadeDTO);
		});

		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeIdAndCidadeDTO_whenSalvar_thenReturnCidadeDTO() {
		var estadoId = 55;
		var cidade = new Cidade();
		cidade.setNome("ZZZZZZZZZZZZ");

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(cidade));
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));
		
		when(this.cidadesRepository.save(any()))
			.thenReturn(cidade);
		// @formatter:on

		var cidadeId = 100;
		var cidadeDTO = AlterarCidadeDTO.builder().nome("Teste").estado(EstadoDTO.builder().id(estadoId).build())
				.build();

		var result = this.cidadesService.salvar(cidadeId, cidadeDTO);

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.cidadesRepository, times(1)).save(any());

		assertThat(result).isNotNull();
		assertEquals(cidadeDTO.getNome(), result.getNome());
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withCidadeIdInexistente_whenSalvar_thenThrowsEntityNotFoundException() {
		var cidade = new Cidade();
		cidade.setNome("ZZZZZZZZZZZZ");

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var cidadeId = 100;
		var cidadeDTO = AlterarCidadeDTO.builder().nome("Teste").build();
		assertThrows(EntityNotFoundException.class, () -> {
			this.cidadesService.salvar(cidadeId, cidadeDTO);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeIdAndCidadeDTO_withEstadoIdInexistente_whenSalvar_thenThrowsBusinessException() {
		var estadoId = 55;
		var cidade = new Cidade();
		cidade.setNome("ZZZZZZZZZZZZ");

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(cidade));
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var cidadeId = 100;
		var cidadeDTO = AlterarCidadeDTO.builder().nome("Teste").estado(EstadoDTO.builder().id(estadoId).build())
				.build();

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.salvar(cidadeId, cidadeDTO);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeIdAndEstadoId_whenAlterarEstado_thenReturnCidadeDTO() {
		var cidadeId = 100;
		var estadoId = 55;

		var cidade = new Cidade();
		cidade.setId(cidadeId);
		Estado estado = new Estado();
		estado.setId(estadoId);
		cidade.setEstado(estado);

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(cidade));
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));
		
		when(this.cidadesRepository.save(any()))
			.thenReturn(cidade);
		// @formatter:on

		var result = this.cidadesService.alterarEstado(cidadeId, estadoId);

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.cidadesRepository, times(1)).save(any());

		assertThat(result).isNotNull();
		assertEquals(cidade.getId(), result.getId());
		assertEquals(cidade.getEstado().getId(), result.getEstado().getId());
	}

	@Test
	void givenCidadeIdAndEstadoId_withCidadeIdInexistente_whenAlterarEstado_thenThrowsEntityNotFoundException() {
		var cidadeId = 100;
		var estadoId = 55;

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		assertThrows(EntityNotFoundException.class, () -> {
			this.cidadesService.alterarEstado(cidadeId, estadoId);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeIdAndEstadoId_withEstadoIdInexistente_whenAlterarEstado_thenThrowsBusinessException() {
		var cidadeId = 100;
		var estadoId = 55;

		var cidade = new Cidade();
		cidade.setId(cidadeId);

		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(cidade));
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.alterarEstado(cidadeId, estadoId);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.estadosRepository, times(1)).findById(estadoId);
		verify(this.cidadesRepository, never()).save(any());
	}

	@Test
	void givenCidadeId_whenRemover_then() {
		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.of(new Cidade()));
		// @formatter:on

		var cidadeId = 100;
		this.cidadesService.remover(cidadeId);

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.cidadesRepository, times(1)).deleteById(cidadeId);
	}

	@Test
	void givenCidadeId_withCidadeIdInexistente_whenRemover_thenThrowsEntityNotFoundException() {
		// @formatter:off
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		// @formatter:on

		var cidadeId = 100;
		assertThrows(EntityNotFoundException.class, () -> {
			this.cidadesService.remover(cidadeId);
		});

		verify(this.cidadesRepository, times(1)).findById(cidadeId);
		verify(this.cidadesRepository, never()).deleteById(any());
	}

	@Test
	void givenListOfCidadeDTO_whenAdicionarLista_thenReturnListOfCidadeDTO() {
		var estado = new Estado();
		// @formatter:off
		var cidadesDTO = Arrays.asList(new AdicionarCidadeDTO[] { 
				AdicionarCidadeDTO.builder().id(1).nome("Teste1").estado(EstadoDTO.builder().id(55).build()).build(),
				AdicionarCidadeDTO.builder().id(2).nome("Teste2").estado(EstadoDTO.builder().id(66).build()).build(),
				AdicionarCidadeDTO.builder().id(3).nome("Teste3").estado(EstadoDTO.builder().id(77).build()).build() });

		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado));
		
		when(this.cidadesRepository.save(any()))
			.thenReturn(new Cidade());
		// @formatter:on

		var result = this.cidadesService.adicionarLista(cidadesDTO);

		verify(this.cidadesRepository, times(3)).findById(any());
		verify(this.estadosRepository, times(3)).findById(any());
		verify(this.cidadesRepository, times(3)).save(any());

		assertEquals(cidadesDTO.size(), result.size());
		assertThat(result.get(0)).isInstanceOf(CidadeDTO.class);
	}

	@Test
	void givenListOfCidadeDTO_withAnyItemHasIdRepetido_whenAdicionarLista_thenThrowsBusinessException() {
		// @formatter:off
		var cidadesDTO = Arrays.asList(new AdicionarCidadeDTO[] { 
				AdicionarCidadeDTO.builder().id(1).nome("Teste1").estado(EstadoDTO.builder().id(55).build()).build(),
				AdicionarCidadeDTO.builder().id(2).nome("Teste2").estado(EstadoDTO.builder().id(66).build()).build(),
				AdicionarCidadeDTO.builder().id(3).nome("Teste3").estado(EstadoDTO.builder().id(77).build()).build() });
		
		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty())
			.thenReturn(Optional.of(new Cidade()));

		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(new Estado()));

		when(this.cidadesRepository.save(any()))
			.thenReturn(new Cidade());
		// @formatter:on

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.adicionarLista(cidadesDTO);
		});

		verify(this.cidadesRepository, times(2)).findById(any());
		verify(this.estadosRepository, times(1)).findById(any());
		verify(this.cidadesRepository, times(1)).save(any());
	}

	@Test
	void givenListOfCidadeDTO_withAnyItemHasEstadoIdInexistente_whenAdicionarLista_thenThrowsBusinessException() {
		var estado = new Estado();
		// @formatter:off
		var cidadesDTO = Arrays.asList(new AdicionarCidadeDTO[] { 
				AdicionarCidadeDTO.builder().id(1).nome("Teste1").estado(EstadoDTO.builder().id(55).build()).build(),
				AdicionarCidadeDTO.builder().id(2).nome("Teste2").estado(EstadoDTO.builder().id(66).build()).build(),
				AdicionarCidadeDTO.builder().id(3).nome("Teste3").estado(EstadoDTO.builder().id(77).build()).build() });

		when(this.cidadesRepository.findById(any()))
			.thenReturn(Optional.empty());
		
		when(this.estadosRepository.findById(any()))
			.thenReturn(Optional.of(estado))
			.thenReturn(Optional.empty());
		
		when(this.cidadesRepository.save(any()))
			.thenReturn(new Cidade());
		// @formatter:on

		assertThrows(BusinessException.class, () -> {
			this.cidadesService.adicionarLista(cidadesDTO);
		});

		verify(this.cidadesRepository, times(2)).findById(any());
		verify(this.estadosRepository, times(2)).findById(any());
		verify(this.cidadesRepository, times(1)).save(any());
	}

}
