package com.crudCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crudCommerce.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
