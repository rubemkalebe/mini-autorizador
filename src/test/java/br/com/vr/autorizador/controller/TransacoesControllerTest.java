package br.com.vr.autorizador.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.com.vr.autorizador.MiniAutorizadorApplicationTests;
import br.com.vr.autorizador.dto.request.TransacaoRequest;
import io.restassured.http.ContentType;

@TestMethodOrder(OrderAnnotation.class)
public class TransacoesControllerTest extends MiniAutorizadorApplicationTests {
	
	@Test
	public void tentaUsarCartaoInexistente() {
		TransacaoRequest request = TransacaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.valor(BigDecimal.valueOf(10.00))
				.build();
		
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/transacoes")
		.then()
			.assertThat()
				.statusCode(HttpStatus.SC_CREATED)
				.body(equalTo("OK"));
	}
	
}
