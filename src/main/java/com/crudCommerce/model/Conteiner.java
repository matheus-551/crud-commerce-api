package com.crudCommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Conteiner {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	private Cliente cliente;
	@Column(nullable = false)
	private String numeroConteiner;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TipoConteiner tipoConteiner;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Categoria categoria;
	
	public Conteiner() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getNumeroConteiner() {
		return numeroConteiner;
	}

	public void setNumeroConteiner(String numeroConteiner) {
		this.numeroConteiner = numeroConteiner;
	}

	public TipoConteiner getTipoConteiner() {
		return tipoConteiner;
	}

	public void setTipoConteiner(TipoConteiner tipoConteiner) {
		this.tipoConteiner = tipoConteiner;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	@Override
	public String toString() {
		return "Conteiner [id=" + id + ", cliente=" + cliente + ", numeroConteiner=" + numeroConteiner
				+ ", tipoConteiner=" + tipoConteiner + ", status=" + status + ", categoria=" + categoria + "]";
	}
}
