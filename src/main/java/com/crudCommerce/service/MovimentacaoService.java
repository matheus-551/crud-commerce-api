package com.crudCommerce.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crudCommerce.exception.RegraNegocioException;
import com.crudCommerce.model.Movimentacao;
import com.crudCommerce.repository.MovimentacaoRepository;

@Service
public class MovimentacaoService {

	@Autowired
	private MovimentacaoRepository repository;
	
	public List<Movimentacao> listAllMovimentacao() {
		return this.repository.findAll();
	}
	
	public Optional<Movimentacao> findMovimentacaoById(Integer id) {
		return this.repository.findById(id);
	}
	
	public Movimentacao saveMovimentacao(Movimentacao movimentacao) {
		ValidaMovimentacao(movimentacao);
		return this.repository.save(movimentacao);
	}
	
	public void deleteMovimentacao(Integer id) {
		this.repository.deleteById(id);
	}
	
	private void ValidaMovimentacao(Movimentacao movimentacao) {
		if(movimentacao.getConteiner() == null) {
			throw new RegraNegocioException("O contêiner é obrigatório");
		}
		
		if(this.repository.existsByConteiner(movimentacao.getConteiner()) && movimentacao.getId() == null) {
			throw new RegraNegocioException("Este contêiner já está em uma movimentação");
		}
		
		if(movimentacao.getTipoMovimentacao() == null) {
			throw new RegraNegocioException("O tipo da movimentação é obrigatório");
		}
		
		if(movimentacao.getDataHoraInicio() == null) {
			throw new RegraNegocioException("A data e hora do inicio da movimentação é obrigatória");
		}
		
		if(movimentacao.getDataHoraFim() == null) {
			throw new RegraNegocioException("A data e hora do fim da movimentação é obrigatória");
		}
	}
}
