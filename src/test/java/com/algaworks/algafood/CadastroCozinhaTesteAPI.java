package com.algaworks.algafood;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaTesteAPI {

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@BeforeEach
	public void setUp() {
		//habilita log quando teste falha
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		databaseCleaner.clearTables();
		
		prepararDados();
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
	public void deveConter2Cozinhas() {
				
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(2))
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
	
	//reponsavel por adicionar uma massa de dados
	private void prepararDados() {
		Gastronomia gastronomia1 = new Gastronomia();
		gastronomia1.setNome("Tailandesa");
		cozinhaRepository.save(gastronomia1);
		
		Gastronomia gastronomia2 = new Gastronomia();
		gastronomia2.setNome("Americana");
		cozinhaRepository.save(gastronomia2);
	}
}
