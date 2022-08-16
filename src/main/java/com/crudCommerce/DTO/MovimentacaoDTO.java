package com.crudCommerce.DTO;

public class MovimentacaoDTO {
	
	private Integer id;
	private Integer idConteiner;
	private String tipoMovimentacao;
	private String dataHoraInicio;
	private String dataHoraFim;
	
	public MovimentacaoDTO() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdConteiner() {
		return idConteiner;
	}

	public void setIdConteiner(Integer idConteiner) {
		this.idConteiner = idConteiner;
	}

	public String getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(String tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public String getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(String dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public String getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(String dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}
}
