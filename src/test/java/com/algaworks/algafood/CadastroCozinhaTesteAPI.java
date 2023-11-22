package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaTesteAPI {

	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setUp() {
		//habilita log quando teste falha
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
	}
	
	@Test
	public void deveRetornarStatus200() {
		
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
	}
	
	//fazer corpo da resposta
	@Test
	public void deveConter4Cozinhas() {
				
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(4))
				.body("nome", Matchers.hasItem("Americana"));
	}
}
