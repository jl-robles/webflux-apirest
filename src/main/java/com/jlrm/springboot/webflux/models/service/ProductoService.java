package com.jlrm.springboot.webflux.models.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jlrm.springboot.webflux.dao.CategoriaDao;
import com.jlrm.springboot.webflux.dao.ProductoDao;
import com.jlrm.springboot.webflux.models.documents.Categoria;
import com.jlrm.springboot.webflux.models.documents.Producto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService implements IProductoService {
	
	@Autowired
	private ProductoDao productoDao;
	
	@Autowired
	private CategoriaDao categoriaDao;

	@Override
	public Flux<Producto> findAll() {
		return productoDao.findAll();
	}

	@Override
	public Mono<Producto> findById(String id) {
		return productoDao.findById(id);
	}

	@Override
	public Mono<Producto> save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	public Mono<Void> delete(Producto producto) {
		return productoDao.delete(producto);
	}

	@Override
	public Flux<Producto> findAllUppercase() {
		return productoDao.findAll().map(producto -> {
			producto.setName(producto.getName().toUpperCase());
			return producto;
		});
	}

	@Override
	public Flux<Producto> findAllRepeat() {
		return findAllUppercase().repeat(5000);
	}

	@Override
	public Flux<Categoria> findAllCategoria() {
		return categoriaDao.findAll();
	}

	@Override
	public Mono<Categoria> findCategoriaById(String id) {
		return categoriaDao.findById(id);
	}

	@Override
	public Mono<Categoria> saveCategoria(Categoria categoria) {
		return categoriaDao.save(categoria);
	}

	@Override
	public Mono<Producto> findByName(String name) {
		return productoDao.getName(name);
	}

}
