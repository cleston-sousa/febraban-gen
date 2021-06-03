package br.net.gits.febraban.api.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.empty;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import br.net.gits.febraban.api.controller.stub.CidadesServiceStub;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(CidadesController.class)
@Import(CidadesServiceStub.class)
class CidadesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.mockMvc(this.mockMvc);
		RestAssuredMockMvc.basePath = "/cidades";
		CidadesServiceStub.reset();
	}

	@Test
	public void givenEstadoId_with_whenListarTodas_thenReturnListOfCidadeResponse() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/estado/{estadoId}", 35)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", hasItems("Auriflama","Adamantina","São Paulo"))
		;
		// @formatter:on
	}

	@Test
	public void givenEstadoId_withCodigoInvalido_whenListarTodas_thenReturnEmptyList() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/estado/{estadoId}", 99)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("", empty())
		;
		// @formatter:on
	}

	@Test
	public void givenCidadeId_withIdValido_whenObterPorId_thenReturnCidadeResponse() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}", 3557154)
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("Zacarias"))
		;
		// @formatter:on
	}

	@Test
	public void givenCidadeId_withIdInvalido_whenObterPorId_thenReturnNotFound() {
		// @formatter:off
		RestAssuredMockMvc.given()
			.accept(ContentType.JSON)
		.when()
			.get("/{cidadeId}", 999999)
		.then()
			.statusCode(HttpStatus.NOT_FOUND.value())
		;
		// @formatter:on
	}

	@Test
	public void givenArquivoDeCidades_with_whenImportarArquivo_thenReturnListOfCidadeResponse() throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		var arquivoCidade = new File(classLoader.getResource("fixedlength/cidade").getPath());

		// @formatter:off
		RestAssuredMockMvc.given()
			.multiPart("arquivo", arquivoCidade)
		.when()
			.post("/importar")
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body("nome", hasItems("Santa Rita do Passa Quatro"))
		;
		// @formatter:on

	}

}
