package br.jus.tjce.bancoerro.util;

public enum AcaoEnum {

	CADASTRADO (1, "CADASTRADO COM SUCESSO."), 
	DELETADO   (2, "DELETADO COM SUCESSO."), 
	ALTERADO   (3, "ALTERADO COM SUCESSO."), 
	PESQUISADO (4, "PESQUISADO COM SUCESSO.");
	
	private Integer id;
	private String descricao;
	
	/**
	 * Construtor
	 * @param id
	 * @param descricao
	 */
	private AcaoEnum(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	/**
	 * Métodos Get's and Set's
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}