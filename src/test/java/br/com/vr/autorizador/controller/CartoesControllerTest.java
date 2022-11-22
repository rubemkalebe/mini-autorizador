package br.com.vr.autorizador.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.vr.autorizador.MiniAutorizadorApplicationTests;
import br.com.vr.autorizador.dto.request.NovoCartaoRequest;
import io.restassured.http.ContentType;

@TestMethodOrder(OrderAnnotation.class)
public class CartoesControllerTest extends MiniAutorizadorApplicationTests {

	@Test
	public void criarNovoCartao() {
		NovoCartaoRequest request = NovoCartaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.build();
		
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/api/cartoes")
		.then()
			.assertThat()
				.statusCode(HttpStatus.SC_OK)
				.body("numeroCartao", equalTo("6549873025634501"))
				.body("senha", equalTo("1234"));
	}
	
}
