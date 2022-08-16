package com.crudCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudCommerce.model.Conteiner;
import com.crudCommerce.model.Movimentacao;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer>{
	
	boolean existsByConteiner(Conteiner conteiner);
}
