package com.jlrm.springboot.webflux;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.jlrm.springboot.webflux.handler.ProductoHandler;

@Configuration
public class RouterFunctionConfig {
	
	
	@Bean
	public RouterFunction<ServerResponse> routes(ProductoHandler handler) {
		
		return route(RequestPredicates.GET("/api/v2/productos").or(GET("/api/v3/productos")), 
				handler::listar)
				.andRoute(GET("/api/v2/productos/{id}"), handler::ver)
				.andRoute(POST("/api/v2/productos"), handler::crear)
				.andRoute(PUT("/api/v2/productos/{id}"), handler::editar)
				.andRoute(DELETE("/api/v2/productos/{id}"), handler::eliminar)
				.andRoute(POST("/api/v2/productos/upload/{id}"), handler::upload)
				.andRoute(POST("/api/v2/productos/crear/{id}"), handler::crearConFoto);
	}

}
