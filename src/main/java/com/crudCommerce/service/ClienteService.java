package com.crudCommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Cliente;
import com.crudCommerce.repository.ClienteRepository;
import com.crudCommerce.repository.ConteinerRepository;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repository;
	
	@Autowired
	private ConteinerRepository conteinerRepostory;
	
	public List<Cliente> listAllClientes() {
		return this.repository.findAll();
	}
	
	public Optional<Cliente> findClienteById(Integer id) {
		return this.repository.findById(id);
	}
	
	public Cliente saveCliente(Cliente cliente) {
		
		if(cliente.getNome() == null || cliente.getNome().trim().equals("")) {
			throw new RegraNegocioException("Nome do cliente Inválido");
		}
		
		return this.repository.save(cliente);
	}
	
	public void deleteCliente(Integer id) {
		Optional<Cliente> cliente = findClienteById(id);
		
		if(this.conteinerRepostory.existsByCliente(cliente.get())) {
			throw new RegraNegocioException("Não é possivél deletar este cliente pois existe um contêiner cadastrado com este cliente");
		}
		
		this.repository.deleteById(id);
	}
}
