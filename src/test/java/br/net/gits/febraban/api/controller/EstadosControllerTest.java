package br.net.gits.febraban.api.controller;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

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
		EstadosServiceStub.reset();
	}

	@Test
	public void given_with_whenListarTodos_thenReturnListOfEstadoResponse() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", hasSize(EstadosServiceStub.estadosRepository.size()))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_with_whenAdicionar_thenReturnEstadoResponse() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/inserir_estado.json");
		// @formatter:off
		RestAssuredMockMvc.given()
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
	public void givenEstadoRequest_withEstadoIdDuplicado_whenAdicionar_thenReturnBadRequest() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/inserir_estado_repetido.json");
		// @formatter:off
		RestAssuredMockMvc.given()
			.body(estadoJson)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value())
		;

		RestAssuredMockMvc.given()
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
	public void givenEstadoRequest_withEstadoIdValido_whenObterPorId_thenReturnEstadoResponse() {
		var estado = EstadosServiceStub.estadosRepository.get(0);
		// @formatter:off
		RestAssuredMockMvc.given()
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
	public void givenEstadoRequest_withEstadoIdInvalido_whenObterPorId_thenReturnNotFound() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/{estadoId}", 9999)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoRequest_withEstadoIdValido_whenAlterar_thenReturnEstadoResponse() {
		var estado = EstadosServiceStub.estadosRepository.get(0);
		var nomeAntigo = estado.getNome();
		var estadoJson = TestUtils.getContentFromResource("/json/estados/alterar_estado.json");
		// @formatter:off
		RestAssuredMockMvc.given()
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
	public void givenEstadoRequest_withEstadoIdInvalido_whenAlterar_thenReturnNotFound() {
		var estadoJson = TestUtils.getContentFromResource("/json/estados/alterar_estado.json");
		// @formatter:off
		RestAssuredMockMvc.given()
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

	@Test
	public void givenEstadoCodigo_withEstadoCodigoValido_whenObterPorCodigo_thenReturnEstadoResponse() {
		
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/codigo/{estadoCodigo}", "SP")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("cidade.nome", hasItems("Auriflama","Adamantina","São Paulo"))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoNome_withLetrasMaiusculasEMinusculas_whenObterPorNome_thenReturnEstadoResponse() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/nome/{estadoNome}", "IO")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", hasSize(3))
			.body("nome", hasItems("Rio de Janeiro", "Rio Grande do Norte","Rio Grande do Sul"))
		;
		// @formatter:on
	}
}
