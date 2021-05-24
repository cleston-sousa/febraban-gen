package br.net.gits.febraban.persistence.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.net.gits.febraban.persistence.entities.Estado;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EstadosRepositoryTest {

	@Autowired
	private EstadosRepository estadosRepository;

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
		this.estadosRepository.deleteAll();
	}

	@Test
	public void givenEstado_whenAdicionarEstado_thenReturnEstado() {
		var estado = new Estado();
		estado.setId(1);
		estado.setCodigo("TT");
		estado.setNome("Teste");

		var inserted = this.estadosRepository.save(estado);

		var consulta = this.estadosRepository.findById(estado.getId()).get();

		assertEquals(estado.getId(), consulta.getId());
		assertEquals(inserted.getId(), consulta.getId());
	}

	@Test
	public void given_whenListarTodos_thenReturnListTodosEstados() {
		var estado = new Estado();
		estado.setId(1);
		estado.setCodigo("TT");
		estado.setNome("Teste");
		this.estadosRepository.save(estado);

		Estado estado2 = new Estado();
		estado2.setId(2);
		estado2.setCodigo("SS");
		estado2.setNome("Segundo");
		this.estadosRepository.save(estado2);

		List<Estado> estados = (List<Estado>) this.estadosRepository.findAll();

		assertEquals(estado2.getNome(), estados.get(1).getNome());
	}

	@Test
	public void givenId_whenObterPorId_thenReturnEstado() {
		var estado = new Estado();
		estado.setId(1);
		estado.setCodigo("TT");
		estado.setNome("Teste");
		this.estadosRepository.save(estado);

		Estado estado2 = new Estado();
		estado2.setId(2);
		estado2.setCodigo("SS");
		estado2.setNome("Segundo");
		this.estadosRepository.save(estado2);

		Optional<Estado> optional = this.estadosRepository.findById(estado2.getId());

		assertEquals(estado2.getId(), optional.get().getId());
		assertEquals(estado2.getNome(), optional.get().getNome());
	}

}
