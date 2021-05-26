package br.net.gits.febraban.api.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import br.net.gits.febraban.api.controller.stub.CidadesServiceStub;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(CidadesController.class)
@Import(CidadesServiceStub.class)
class CidadesControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		RestAssuredMockMvc.mockMvc(this.mockMvc);
		RestAssuredMockMvc.basePath = "/estados";
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
