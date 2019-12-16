package com.jlrm.springboot.webflux.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.jlrm.springboot.webflux.models.documents.Categoria;

public interface CategoriaDao extends ReactiveMongoRepository<Categoria, String	>{

}
