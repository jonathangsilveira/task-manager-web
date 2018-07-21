package edu.jonathangs.practice.taskmanager.model;

import java.util.Date;

public class Tarefa {

	private Integer id = 0;
	
	private String titulo = "";
	
	private String descricao;
	
	private Integer status = 0;
	
	private Date dataInclusao = new Date();
	
	private Date dataAlteracao;

	private Date dataConclusao;

	private Date dataExclusao;
	
	public Tarefa() {
		
	}
	
	public Tarefa(int id, String titulo, String descricao, int status, Date dataInclusao, 
			Date dataAlteracao, Date dataConclusao, Date dataExclusao) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.dataInclusao = dataInclusao;
		this.dataAlteracao = dataAlteracao;
		this.dataConclusao = dataConclusao;
		this.dataExclusao = dataExclusao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDataInclusao() {
		return dataInclusao;
	}

	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}

	public Date getDataExclusao() {
		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao) {
		this.dataExclusao = dataExclusao;
	}
	
}
