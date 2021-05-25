package br.net.gits.febraban.api.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import br.net.gits.febraban.api.controller.stub.EstadosServiceStub;
import br.net.gits.febraban.api.controller.utils.TestUtils;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(EstadosController.class)
@Import(EstadosServiceStub.class)
public class EstadosControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.mockMvc(this.mockMvc);
		RestAssuredMockMvc.basePath = "/estados";
	}

	@Test
	public void given_whenListarTodos_thenReturnListOfEstadoResponse() {
		// @formatter:off
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", Matchers.hasSize(EstadosServiceStub.estadosRepository.size()))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_whenAdicionar_thenReturnEstadoResponse() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/inserir_estado.json");
		// @formatter:off
		given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequestDuplicado_whenAdicionar_thenReturnBadRequest() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/inserir_estado_repetido.json");
		// @formatter:off
		given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
		;

		given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value())
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_whenObterPorId_thenReturnEstadoResponse() {
		var estado = EstadosServiceStub.estadosRepository.get(0);
		// @formatter:off
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}", estado.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo(estado.getNome()))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_whenObterPorId_thenReturnNotFound() {
		// @formatter:off
		given()
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}", 9999)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_whenAlterar_thenReturnEstadoResponse() {
		var estado = EstadosServiceStub.estadosRepository.get(0);
		var nomeAntigo = estado.getNome();
		var estadoJson = TestUtils.getContentFromResource("/json/estados/alterar_estado.json");
		// @formatter:off
		given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{estadoId}", estado.getId())
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", not(equalTo(nomeAntigo)))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_whenAlterar_thenReturnNotFound() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/alterar_estado.json");
		// @formatter:off
		given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.put("/{estadoId}", 9999)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
		;
		// @formatter:on
	}

}
