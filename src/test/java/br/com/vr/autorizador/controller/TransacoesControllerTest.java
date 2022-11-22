package br.com.vr.autorizador.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.vr.autorizador.MiniAutorizadorApplicationTests;
import br.com.vr.autorizador.dto.request.TransacaoRequest;
import br.com.vr.autorizador.model.Cartao;
import br.com.vr.autorizador.service.CartoesService;
import br.com.vr.autorizador.values.TransacaoEnum;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@TestMethodOrder(OrderAnnotation.class)
public class TransacoesControllerTest extends MiniAutorizadorApplicationTests {
	
	@Autowired
	private CartoesService cartoesService;
	
	@Test
	public void tentaUsarCartaoInexistente() {
		TransacaoRequest request = TransacaoRequest.builder()
				.numeroCartao("6549873025634502")
				.senha("1234")
				.valor(BigDecimal.valueOf(10.00))
				.build();
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(request)
				.accept(ContentType.JSON)
			.when()
				.post("/transacoes");
			
			assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusCode());
			assertEquals(TransacaoEnum.CARTAO_INEXISTENTE.toString(), response.getBody().asString());
	}
	
	@Test
	public void tentaUsarCartaoComSenhaInvalida() {
		TransacaoRequest request = TransacaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("4321")
				.valor(BigDecimal.valueOf(10.00))
				.build();
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(request)
				.accept(ContentType.JSON)
			.when()
				.post("/transacoes");
			
			assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusCode());
			assertEquals(TransacaoEnum.SENHA_INVALIDA.toString(), response.getBody().asString());
	}
	
	@Test
	public void tentaUsarCartaoComSaldoInsuficiente() {
		TransacaoRequest request = TransacaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.valor(BigDecimal.valueOf(500.01))
				.build();
		
		Response response = given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/transacoes");
		
		assertEquals(HttpStatus.SC_UNPROCESSABLE_ENTITY, response.getStatusCode());
		assertEquals(TransacaoEnum.SALDO_INSUFICIENTE.toString(), response.getBody().asString());
	}
	
	@Test
	public void debitaComSucesso() {
		TransacaoRequest request = TransacaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.valor(BigDecimal.valueOf(10.01))
				.build();
		
		Response response = given()
				.contentType(ContentType.JSON)
				.body(request)
				.accept(ContentType.JSON)
			.when()
				.post("/transacoes");
			
			assertEquals(HttpStatus.SC_CREATED, response.getStatusCode());
			assertEquals(TransacaoEnum.OK.toString(), response.getBody().asString());
		
		Cartao found = cartoesService.findByNumero("6549873025634501");
		assertEquals("489.99", found.getSaldo().toPlainString());
	}
	
}
