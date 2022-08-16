package com.crudCommerce.model;

public enum TipoConteiner {
	
	PEQUENO(20), GRANDE(40);
	
	private Integer tamanho;
	
	private TipoConteiner(Integer tamanho) {
		this.tamanho = tamanho;
	}
	
	public Integer getTamanho() {
		return this.tamanho;
	}
}
