package com.crudCommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crudCommerce.DTO.ConteinerDTO;
import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Categoria;
import com.crudCommerce.model.Cliente;
import com.crudCommerce.model.Conteiner;
import com.crudCommerce.model.Status;
import com.crudCommerce.model.TipoConteiner;
import com.crudCommerce.service.ClienteService;
import com.crudCommerce.service.ConteinerService;

@RestController
@RequestMapping("/api/conteineres")
public class ConteinerController {
	
	@Autowired
	private ConteinerService conteinerService;
	
	@Autowired
	private ClienteService clienteService;
	
	private Conteiner converteDtoToEntity(ConteinerDTO dto) {
		Conteiner conteiner = new Conteiner();
		
		if(dto.getId() != null) {
			conteiner.setId(dto.getId());
		}
		
		Optional<Cliente> cliente = this.clienteService.findClienteById(dto.getIdCliente());
		
		if(cliente.isPresent()) {
			conteiner.setCliente(cliente.get());
		}
		
		conteiner.setNumeroConteiner(dto.getNumeroConteiner());
		conteiner.setCategoria(Categoria.valueOf(dto.getCategoria()));
		conteiner.setTipoConteiner(TipoConteiner.valueOf(dto.getTipoConteiner()));
		conteiner.setStatus(Status.valueOf(dto.getStatus()));
		
		return conteiner;
	}
	
	@GetMapping
	public ResponseEntity<List<Conteiner>> list() {
		List<Conteiner> conteineres = this.conteinerService.listAllConteineres();
		return ResponseEntity.ok(conteineres);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findConteinerById(@PathVariable("id") Integer id) {
		Optional<Conteiner> conteiner = this.conteinerService.findConteinerById(id);
		
		if(conteiner.isPresent()) {
			return new ResponseEntity<Conteiner>(conteiner.get(), HttpStatus.OK);
		}else 
			return ResponseEntity.badRequest().body("Ocorreu um erro ao buscar o contêiner");
	}
	
	@GetMapping("/filter-conteiner")
	public ResponseEntity<List<Conteiner>> filterConteiner(
			@RequestParam(value = "categoria", required = false) String categoria,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "tipoConteiner", required = false) String tipoConteiner) {
		
		Conteiner conteiner = new Conteiner();
		
		if(categoria != null) {
			conteiner.setCategoria(Categoria.valueOf(categoria));
		}
		
		if(status != null) {
			conteiner.setStatus(Status.valueOf(status));
		}
		
		if(tipoConteiner != null) {
			conteiner.setTipoConteiner(TipoConteiner.valueOf(tipoConteiner));
		}
		
		return ResponseEntity.ok(this.conteinerService.filterConteiner(conteiner));
	}
	
	@PostMapping
	public ResponseEntity<?> createConteiner(@RequestBody ConteinerDTO dto) {
		try {
			Conteiner conteiner = converteDtoToEntity(dto);
			
			Conteiner conteinerSaved = this.conteinerService.saveConteiner(conteiner);
			return new ResponseEntity<Conteiner>(conteinerSaved, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateConteiner(
			@PathVariable("id") Integer id,
			@RequestBody ConteinerDTO dto) {
		
		return this.conteinerService.findConteinerById(id).map(entity -> {
			try {
				Conteiner conteiner = converteDtoToEntity(dto);
				conteiner.setId(entity.getId());
				
				Conteiner conteinerSaved = this.conteinerService.saveConteiner(conteiner);
				return new ResponseEntity<Conteiner>(conteinerSaved, HttpStatus.OK);
			} catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() ->
				ResponseEntity.badRequest().body("Ocorreu um erro ao buscar o contêiner"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteConteiner(@PathVariable("id") Integer id) {
		try {			
			this.conteinerService.deleteConteiner(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
