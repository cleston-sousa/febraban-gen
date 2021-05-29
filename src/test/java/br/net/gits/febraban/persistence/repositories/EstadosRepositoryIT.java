package br.net.gits.febraban.persistence.repositories;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isPresentAnd;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
public class EstadosRepositoryIT {

	@Autowired
	private IEstadosRepository estadosRepository;

	@BeforeEach
	public void setUp() {
	}

	@AfterEach
	public void tearDown() {
		this.estadosRepository.deleteAll();
	}

	@Test
	public void givenEstado_withDadosValidos_whenSave_thenReturnEstado() {
		var estado = new Estado();
		estado.setId(1);
		estado.setCodigo("TT");
		estado.setNome("Teste");

		var inserted = this.estadosRepository.save(estado);

		var consulta = this.estadosRepository.findById(estado.getId()).get();

		assertThat(consulta, hasProperty("id", equalTo(estado.getId())));
		assertThat(consulta, hasProperty("id", equalTo(inserted.getId())));

	}

	@Test
	public void given_with_whenFindAll_thenReturnListOfEstado() {
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

		assertThat(estado, is(in(estados)));
		assertThat(estado2, is(in(estados)));

	}

	@Test
	public void givenId_with_whenFindById_thenReturnEstado() {
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

		assertThat(optional, isPresentAnd(hasProperty("id", equalTo(estado2.getId()))));
		assertThat(optional, isPresentAnd(hasProperty("nome", equalTo(estado2.getNome()))));
	}

	@Test
	public void givenCodigo_withMaiusculasEMinusculas_whenFindByCodigoIgnoreCase_thenReturnListOfEstado() {
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

		Estado result = this.estadosRepository.findFirstByCodigoIgnoreCase("Ss");

		assertThat(estado2, is(equalTo(result)));
	}

	@Test
	public void givenNome_withMaiusculasEMinusculas_whenFindByNomeContainingIgnoreCase_thenReturnListOfEstado() {
		var estado = new Estado();
		estado.setId(1);
		estado.setCodigo("TF");
		estado.setNome("São Francisco");
		this.estadosRepository.save(estado);

		Estado estado2 = new Estado();
		estado2.setId(2);
		estado2.setCodigo("SJ");
		estado2.setNome("São José");
		this.estadosRepository.save(estado2);

		Estado estado3 = new Estado();
		estado3.setId(3);
		estado3.setCodigo("FA");
		estado3.setNome("Francisco de Assis");
		this.estadosRepository.save(estado3);

		List<Estado> result1 = this.estadosRepository.findByNomeContainingIgnoreCase("AnC");

		List<Estado> result2 = this.estadosRepository.findByNomeContainingIgnoreCase("SÃo");

		assertThat(result1, not(empty()));
		assertThat(estado, is(in(result1)));
		assertThat(estado3, is(in(result1)));
		assertThat(estado2, is(not(in(result1))));

		assertThat(result2, not(empty()));
		assertThat(estado, is(in(result2)));
		assertThat(estado2, is(in(result2)));
		assertThat(estado3, is(not(in(result2))));

	}

}
