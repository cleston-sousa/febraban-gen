package br.net.gits.febraban.persistence.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.in;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.net.gits.febraban.persistence.entities.Cidade;
import br.net.gits.febraban.persistence.entities.Estado;

@ExtendWith(SpringExtension.class)
@DataJpaTest

public class CidadesRepositoryIT {

	@Autowired
	private ICidadesRepository cidadesRepository;

	@Autowired
	private IEstadosRepository estadosRepository;

	private Estado estado1;
	private Estado estado2;

	private Cidade cidade1;
	private Cidade cidade2;
	private Cidade cidade3;

	@BeforeEach
	public void setUp() {

		this.estado1 = new Estado();
		this.estado1.setId(1);
		this.estado1.setCodigo("DO");
		this.estado1.setNome("Don Onion");
		this.estadosRepository.save(this.estado1);

		this.estado2 = new Estado();
		this.estado2.setId(2);
		this.estado2.setCodigo("AF");
		this.estado2.setNome("Away Forest");
		this.estadosRepository.save(this.estado2);

		this.cidade1 = new Cidade();
		this.cidade1.setId(1);
		this.cidade1.setNome("Bonito de Doer");
		this.cidade1.setEstado(this.estado2);
		this.cidadesRepository.save(this.cidade1);

		this.cidade2 = new Cidade();
		this.cidade2.setId(2);
		this.cidade2.setNome("Cidade Dos Divertidos");
		this.cidade2.setEstado(this.estado1);
		this.cidadesRepository.save(this.cidade2);

		this.cidade3 = new Cidade();
		this.cidade3.setId(3);
		this.cidade3.setNome("Monte Oeste do Sul Infito");
		this.cidade3.setEstado(this.estado2);
		this.cidadesRepository.save(this.cidade3);
	}

	@AfterEach
	public void tearDown() {
		this.cidadesRepository.deleteAll();
		this.estadosRepository.deleteAll();
	}

	@Test
	public void givenEstado_withEstadoValido_whenFindByEstado_thenReturnListOfCidade() {

		Estado estadoTeste = new Estado();
		estadoTeste.setId(1);

		var result = this.cidadesRepository.findByEstado(estadoTeste);

		assertThat(this.cidade2, is(in(result)));
		assertThat(this.cidade1, is(not(in(result))));
		assertThat(this.cidade3, is(not(in(result))));

	}

	@Test
	public void givenNome_withMaiusculasEMinusculas_whenFindByNomeContainingIgnoreCase_thenReturnListOfCidade() {

		var result1 = this.cidadesRepository.findByNomeContainingIgnoreCase("de DO");

		var result2 = this.cidadesRepository.findByNomeContainingIgnoreCase("iTo");

		assertThat(this.cidade1, is(in(result1)));
		assertThat(this.cidade2, is(in(result1)));
		assertThat(this.cidade3, is(not(in(result1))));

		assertThat(this.cidade1, is(in(result2)));
		assertThat(this.cidade3, is(in(result2)));
		assertThat(this.cidade2, is(not(in(result2))));

	}

}
