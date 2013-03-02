package br.jus.tjce.bancoerro.domain;

public class Relatorio {
	
	private String codigoErroAlerta;
	
	private Long quantidadePesquisaPorErro;
	
	private String descricaoErro;

	
	public Relatorio() {}
	
	/**
	 * Construtor
	 * @param codigoErroAlerta
	 * @param quantidadePesquisaPorErro
	 * @param descricaoErro
	 */
	public Relatorio(String codigoErroAlerta, Long quantidadePesquisaPorErro, String descricaoErro) {
		this.codigoErroAlerta = codigoErroAlerta;
		this.quantidadePesquisaPorErro = quantidadePesquisaPorErro;
		this.descricaoErro = descricaoErro;
	}

	/**
	 * Métodos Gets e Sets
	 * @return
	 */
	public Long getQuantidadePesquisaPorErro() {
		return quantidadePesquisaPorErro;
	}
	public void setQuantidadePesquisaPorErro(Long quantidadePesquisaPorErro) {
		this.quantidadePesquisaPorErro = quantidadePesquisaPorErro;
	}
	public String getDescricaoErro() {
		return descricaoErro;
	}
	public void setDescricaoErro(String descricaoErro) {
		this.descricaoErro = descricaoErro;
	}
	public String getCodigoErroAlerta() {
		return codigoErroAlerta;
	}
	public void setCodigoErroAlerta(String codigoErroAlerta) {
		this.codigoErroAlerta = codigoErroAlerta;
	}
}
