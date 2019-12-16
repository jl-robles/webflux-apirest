package com.jlrm.springbootwebflux;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.jlrm.springboot.webflux.models.documents.Producto;
import com.jlrm.springboot.webflux.models.service.ProductoService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = RouterFunctionConfigTest.class)
public class SpringBootWebfluxApirestApplicationTests {

	@Autowired
	private WebTestClient client;
	
	//@Autowired
	//private ProductoService service;
	
	@Test
	public void listarTest() {
		
		client.get()
		.uri("/api/v2/productos")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(Producto.class)
		.consumeWith(response -> {
			List<Producto> productos = response.getResponseBody();
			productos.forEach(p -> {
				System.out.println("Name "+p.getName());
			});
			
			Assertions.assertThat(productos.size() > 0).isTrue();
		});
		//.hasSize(11);
	}
	
	//@Test
	public void verTest() {
		Producto producto = null;//service.findByName("Asus Core i7").block();
		
		client.get()
		.uri("/api/v2/productos/{id}", Collections.singletonMap("id", producto.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.name").isEqualTo("Asus Core i7");
	}
	
	

}
