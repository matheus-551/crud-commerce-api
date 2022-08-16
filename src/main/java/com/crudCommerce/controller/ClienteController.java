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
import org.springframework.web.bind.annotation.RestController;

import com.crudCommerce.DTO.ClienteDTO;
import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Cliente;
import com.crudCommerce.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<Cliente>> list() {
		List<Cliente> clientes = this.clienteService.listAllClientes();
		return ResponseEntity.ok(clientes);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findClienteById(@PathVariable("id") Integer id) {
		Optional<Cliente> cliente = this.clienteService.findClienteById(id);
		
		if(cliente.isPresent()) {
			return new ResponseEntity<Cliente>(cliente.get(), HttpStatus.OK);
		}else 
			return ResponseEntity.badRequest().body("Ocorreu um erro ao buscar o cliente");
	}
	
	@PostMapping
	public ResponseEntity<?> createCliente(@RequestBody ClienteDTO dto) {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(dto.getNome());
			
			Cliente clienteSaved = this.clienteService.saveCliente(cliente);
			return new ResponseEntity<Cliente>(clienteSaved, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCliente(
			@PathVariable("id") Integer id,
			@RequestBody ClienteDTO dto) {
		return this.clienteService.findClienteById(id).map(entity -> {
			try {
				Cliente cliente = new Cliente();
				cliente.setId(entity.getId());
				cliente.setNome(dto.getNome());
				
				Cliente clienteSaved = this.clienteService.saveCliente(cliente);
				return new ResponseEntity<Cliente>(clienteSaved, HttpStatus.OK);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}).orElseGet(() -> 
			ResponseEntity.badRequest().body("Ocorreu um erro ao buscar o cliente"));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCliente(@PathVariable("id") Integer id) {
		try {			
			this.clienteService.deleteCliente(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
