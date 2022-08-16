package com.crudCommerce.DTO;

public class ConteinerDTO {
	
	private Integer id;
	private Integer idCliente;
	private String numeroConteiner;
	private String tipoConteiner;
	private String status;
	private String categoria;
	
	public ConteinerDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNumeroConteiner() {
		return numeroConteiner;
	}

	public void setNumeroConteiner(String numeroConteiner) {
		this.numeroConteiner = numeroConteiner;
	}

	public String getTipoConteiner() {
		return tipoConteiner;
	}

	public void setTipoConteiner(String tipoConteiner) {
		this.tipoConteiner = tipoConteiner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
