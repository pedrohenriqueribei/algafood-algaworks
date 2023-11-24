package com.algaworks.algafood;

import static org.hamcrest.CoreMatchers.equalTo;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import com.algaworks.algafood.domain.model.Gastronomia;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteTesteAPI {

	private static final int RESTAURANTE_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	private Restaurante restBurger;
	
	private int quantidadeRest;
	
	private String jsonCorretoRestauranteGoiana;
	private String jsonRestauranteSemTaxaFrete;
	private String jsonRestauranteSemCozinha;
	private String jsonRestauranteComCozinhaInexistente;
	
	@BeforeEach
	public void setUp() {
		//habilita log quando teste falha
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		
		RestAssured.port = port;
		RestAssured.basePath = "/restaurantes";
		
		jsonCorretoRestauranteGoiana = ResourceUtils.getContentFromResource("/json/correto/restaurante_goiana.json");
		jsonRestauranteSemTaxaFrete = ResourceUtils.getContentFromResource("/json/incorreto/jsonRestauranteSemTaxaFrete.json");
		jsonRestauranteSemCozinha   = ResourceUtils.getContentFromResource("/json/incorreto/jsonRestauranteSemCozinha.json");
		jsonRestauranteComCozinhaInexistente = ResourceUtils.getContentFromResource("/json/incorreto/jsonRestauranteComCozinhaInexistente.json");
		
		databaseCleaner.clearTables();
		
		prepararDados();
	}
	
	
	

	@Test
	public void deveRetornarStatus200_QuandoReqGet() {
		RestAssured
		.given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void deveRetornar201_QuandoCadastrarRestaurante() {
		
		RestAssured
		.given()
			.body(jsonCorretoRestauranteGoiana)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	
	@Test
	public void deveRetornarStatus200_Quanto_buscar_restauranteId() {
		
		RestAssured
			.given()
				.pathParam("restauranteId", restBurger.getId())
				.accept(ContentType.JSON)
			.when()
				.get("/{restauranteId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(restBurger.getNome()));
	}
	
	@Test
	public void deveRetornarQuantidadeRestaurantes() {
		
		RestAssured
			.given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(HttpStatus.OK.value());
//				.body("", Matchers.hasSize(quantidadeRest))
//				.body("nome", Matchers.hasItem("Burguer 10"));
		
	}
	
	@Test
	public void deveRetornar404_QuantoRestauranteInexistente() {
		
		RestAssured
			.given()
				.pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.get("/{restauranteId}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}
	
	@Test
	public void deveRetornar406_QuandoCadastrarRestauranteSemTaxaFrete() {
		RestAssured
			.given()
				.body(jsonRestauranteSemTaxaFrete)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.NOT_ACCEPTABLE.value());
	}
	
	
	@Test
	public void deveRetornar406_QuandoRestauranteSemCozinha() {
		RestAssured
			.given()
				.body(jsonRestauranteSemCozinha)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.NOT_ACCEPTABLE.value());
	}
	
	@Test
	public void deveRetornar406_QuandoRestauranteComCozinhaInexistente() {
		RestAssured
			.given()
				.body(jsonRestauranteComCozinhaInexistente)
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	
	private void prepararDados() {

		Gastronomia coz1 = new Gastronomia();
		coz1.setNome("Tailandesa");
		cozinhaRepository.save(coz1);
		
		Gastronomia coz2 = new Gastronomia();
		coz2.setNome("Americana");
		cozinhaRepository.save(coz2);
		
		Gastronomia coz3 = new Gastronomia();
		coz3.setNome("Goiana");
		cozinhaRepository.save(coz3);
		
		Restaurante rest1 = new Restaurante();
		rest1.setNome("Seu Zé");
		rest1.setTaxaFrete(new BigDecimal(11.0));
		rest1.setCozinha(coz1);
		
		cadastroRestauranteService.salvar(rest1);
		
		Restaurante rest2 = new Restaurante();
		rest2.setNome("Dona Ju");
		rest2.setTaxaFrete(new BigDecimal(1.5));
		rest2.setCozinha(coz2);
		cadastroRestauranteService.salvar(rest2);
		
		restBurger = new Restaurante();
		restBurger.setNome("Burguer 10");
		restBurger.setTaxaFrete(new BigDecimal(5));
		restBurger.setCozinha(coz2);
		cadastroRestauranteService.salvar(restBurger);
		
		quantidadeRest = (int) restauranteRepository.count();
		
		
	}
}
