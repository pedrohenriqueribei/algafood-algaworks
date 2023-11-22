package com.algaworks.algafood;

import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroCozinhaTesteAPI {

	@LocalServerPort
	private int port;
	
	@Autowired
	private Flyway flyway;
	
	@BeforeEach
	public void setUp() {
		//habilita log quando teste falha
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		//para cada teste o flyway vai executar a migração( com o arquivo aftermigrate)
		flyway.migrate();
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
	
	//fazer teste com corpo da resposta
	@Test
	public void deveConter3Cozinhas() {
				
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(3))
				.body("nome", Matchers.hasItem("Americana"));
	}
	
	@Test
	public void deveRetornarStatus201() {
		RestAssured
			.given()
				.body("{ \"nome\": \"Chilena\" }")
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
}
