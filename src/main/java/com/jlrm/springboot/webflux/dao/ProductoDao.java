package com.jlrm.springboot.webflux.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jlrm.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Mono;

public interface ProductoDao extends ReactiveMongoRepository<Producto, String>{

	public Mono<Producto> findByName(String name);
	
	@Query("{'name':?0}")
	public Mono<Producto> getName(String name);
}
