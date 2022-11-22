package br.com.vr.autorizador.controller;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.vr.autorizador.MiniAutorizadorApplicationTests;
import br.com.vr.autorizador.dto.request.NovoCartaoRequest;
import br.com.vr.autorizador.model.Cartao;
import br.com.vr.autorizador.service.CartoesService;
import io.restassured.http.ContentType;

@TestMethodOrder(OrderAnnotation.class)
public class CartoesControllerTest extends MiniAutorizadorApplicationTests {
	
	@Autowired
	private CartoesService cartoesService;
	
	@Test
	public void tentaCriarNovoCartaoSemNumero() {
		NovoCartaoRequest request = NovoCartaoRequest.builder()
				.senha("1234")
				.build();
		
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/cartoes")
		.then()
			.assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void tentaCriarNovoCartaoSemSenha() {
		NovoCartaoRequest request = NovoCartaoRequest.builder()
				.numeroCartao("6549873025634501")
				.build();
		
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/cartoes")
		.then()
			.assertThat()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}
	
	@Test
	public void criarNovoCartao() {
		NovoCartaoRequest request = NovoCartaoRequest.builder()
				.numeroCartao("6549873025634501")
				.senha("1234")
				.build();
		
		// valida requisicao
		given()
			.contentType(ContentType.JSON)
			.body(request)
			.accept(ContentType.JSON)
		.when()
			.post("/cartoes")
		.then()
			.assertThat()
				.statusCode(HttpStatus.SC_CREATED)
				.body("numeroCartao", equalTo("6549873025634501"))
				.body("senha", equalTo("1234"));
		
		// valida persistencia
		Cartao found = cartoesService.findByNumero("6549873025634501"); 
		assertNotEquals("1234", found.getSenha());
	}
	
}
