package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;


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
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaTesteAPI {

	private static final int VALOR_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Gastronomia cozinhaAmericana;
	
	private int quantidadeCozinhas;
	
	private String jsonCorretoCozinhaChilena;

	
	@BeforeEach
	public void setUp() {
		//habilita log quando teste falha
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";
		
		jsonCorretoCozinhaChilena = ResourceUtils.getContentFromResource("/json/correto/cozinha-chilena.json");

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
	public void deveConterRetornarQuantidadeDeCozinhas() {
				
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("", Matchers.hasSize(quantidadeCozinhas))
				.body("nome", Matchers.hasItem(cozinhaAmericana.getNome()));
	}
	
	@Test
	public void deveRetornarStatus201() {
		RestAssured
			.given()
				.body(jsonCorretoCozinhaChilena)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deveRetornarRespostaEStatusCorretos() {
		RestAssured
			.given()
				.pathParam("cozinhaId", cozinhaAmericana.getId())
				.accept(ContentType.JSON)
			.when()
				.get("/{cozinhaId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(cozinhaAmericana.getNome()));
				
	}
	
	@Test
	public void deveRetornarStatus404_QuandoCozinhaInexistente() {
		RestAssured
			.given()
				.pathParam("cozinhaId", VALOR_ID_INEXISTENTE)
				.accept(ContentType.JSON)
			.when()
				.get("/{cozinhaId}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
				
	}
	
	//reponsavel por adicionar uma massa de dados
	private void prepararDados() {
		Gastronomia gastronomia1 = new Gastronomia();
		gastronomia1.setNome("Tailandesa");
		cozinhaRepository.save(gastronomia1);
		
		cozinhaAmericana = new Gastronomia();
		cozinhaAmericana.setNome("Americana");
		cozinhaRepository.save(cozinhaAmericana);
		
		quantidadeCozinhas = (int) cozinhaRepository.count();
	}
	
	
}
