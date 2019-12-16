package com.jlrm.springboot.webflux.models.service;

import com.jlrm.springboot.webflux.models.documents.Categoria;
import com.jlrm.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductoService {
	
	public Flux<Producto> findAll();
	
	public Flux<Producto> findAllUppercase();
	
	public Flux<Producto> findAllRepeat();
	
	public Mono<Producto> findById(String id);
	
	public Mono<Producto> save(Producto producto);
	
	public Mono<Void> delete(Producto producto);
	
	public Flux<Categoria> findAllCategoria();
	
	public Mono<Categoria> findCategoriaById(String id);
	
	public Mono<Categoria> saveCategoria(Categoria categoria);

	public Mono<Producto> findByName(String name);
	
}
