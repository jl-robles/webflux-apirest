package com.jlrm.springboot.webflux.controller;

import java.io.File;
import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.jlrm.springboot.webflux.models.documents.Producto;
import com.jlrm.springboot.webflux.models.service.IProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private IProductoService service;
	
	@Value("${path.uploads}")
	private String path;
	
	@PostMapping("/v2")
	public Mono<ResponseEntity<Producto>> crearFoto(Producto product, @RequestPart FilePart file) {
		
		if(product.getCreateAt() == null) {
			product.setCreateAt(new Date());
		}
		
		product.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
		.replace(" ", "")
		.replace(":", "")
		.replace("\\", ""));
		
		return file.transferTo(new File(path + product.getFoto())).then(service.save(product))
				.map(p -> ResponseEntity
				.created(URI.create("/api/productos/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p));
	}
	
	@PostMapping("/upload/{id}")
	public Mono<ResponseEntity<Producto>> upload(@PathVariable String id, @RequestPart FilePart file) {
		return service.findById(id).flatMap(p -> {
			p.setFoto(UUID.randomUUID().toString() + "-" + file.filename()
			.replace(" ", "")
			.replace(":", "")
			.replace("\\", ""));
			
			return file.transferTo(new File(path + p.getFoto()))
					.then(service.save(p));
		}).map(p -> ResponseEntity.ok(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public Flux<Producto> lista() {
		return service.findAll();
	}
	
	@GetMapping("/all")
	public Mono<ResponseEntity<Flux<Producto>>> listar() {
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.findAll()));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Producto>> ver(@PathVariable String id) {
		return service.findById(id).map(p -> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<Producto>> crear(@RequestBody Producto producto) {
		
		if(producto.getCreateAt() == null) {
			producto.setCreateAt(new Date());
		}
		
		return service.save(producto).map(p -> ResponseEntity
				.created(URI.create("/api/productos/".concat(p.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(p));
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Producto>> editar(@RequestBody Producto producto, @PathVariable String id) {
		
		return service.findById(id)
				.flatMap(p -> {
					p.setName(producto.getName());
					p.setPrice(producto.getPrice());
					p.setCategoria(producto.getCategoria());
					return service.save(p);
				}).map(p -> ResponseEntity.created(URI.create("/api/productos/".concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id) {
		return service.findById(id).flatMap(p -> {
			return service.delete(p).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
	
}
