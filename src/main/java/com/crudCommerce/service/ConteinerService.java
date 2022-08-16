package com.crudCommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Conteiner;
import com.crudCommerce.repository.ConteinerRepository;
import com.crudCommerce.repository.MovimentacaoRepository;

@Service
public class ConteinerService {
	
	@Autowired
	private ConteinerRepository repository;
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	
	public List<Conteiner> listAllConteineres() {
		return this.repository.findAll();
	}
	
	public Optional<Conteiner> findConteinerById(Integer id) {
		return this.repository.findById(id);
	}
	
	public List<Conteiner> filterConteiner(Conteiner conteiner) {
		List<Conteiner> conteineres = null;
		
		if(conteiner.getCategoria() != null) {
			conteineres = this.repository.findByCategoria(conteiner.getCategoria());
		}
		
		if(conteiner.getTipoConteiner() != null) {
			conteineres = this.repository.findByTipoConteiner(conteiner.getTipoConteiner());
		}
		
		if(conteiner.getStatus() != null) {
			conteineres = this.repository.findByStatus(conteiner.getStatus());
		}
		
		return conteineres;
	}
	
	public Conteiner saveConteiner(Conteiner conteiner) {
		validaConteiner(conteiner);
		return this.repository.save(conteiner);
	}
	
	public void deleteConteiner(Integer id) {
		Optional<Conteiner> conteiner = findConteinerById(id);
		
		if(this.movimentacaoRepository.existsByConteiner(conteiner.get())) {
			throw new RegraNegocioException("Não é possivél deletar este contêiner pois existe uma movimentação cadastrada com este contêiner");
		}
		
		this.repository.deleteById(id);
	}
	
	private void validaConteiner(Conteiner conteiner) {
		
		if(conteiner.getCliente() == null) {
			throw new RegraNegocioException("Informe um cliente válido");
		}
		
		if(conteiner.getNumeroConteiner() == null || conteiner.getNumeroConteiner().trim().equals("")) {
			throw new RegraNegocioException("O número do contêiner é obrigatório");
		}
		
		if(!conteiner.getNumeroConteiner().matches("[a-zA-Z]{4}[0-9]{7}")) {
			throw new RegraNegocioException("Número do contêiner inválido");
		}
		
		if(conteiner.getTipoConteiner() == null) {
			throw new RegraNegocioException("O tipo do contêiner é obrigatório");
		}
		
		if(conteiner.getCategoria() == null) {
			throw new RegraNegocioException("A categoria é obrigatória");
		}
		
		if(conteiner.getStatus() == null) {
			throw new RegraNegocioException("O status é obrigatório");
		}
	}
}
