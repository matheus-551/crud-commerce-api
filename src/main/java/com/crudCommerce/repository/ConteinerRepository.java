package com.crudCommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudCommerce.model.Categoria;
import com.crudCommerce.model.Cliente;
import com.crudCommerce.model.Conteiner;
import com.crudCommerce.model.Status;
import com.crudCommerce.model.TipoConteiner;

public interface ConteinerRepository extends JpaRepository<Conteiner, Integer>{
	
	List<Conteiner> findByCategoria(Categoria categoria);
	
	List<Conteiner> findByTipoConteiner(TipoConteiner tipoConteiner);
	
	List<Conteiner> findByStatus(Status status);
	
	boolean existsByCliente(Cliente cliente);
}
