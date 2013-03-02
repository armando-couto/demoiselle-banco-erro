package br.jus.tjce.bancoerro.exception;

public class Excecao {

	private String mensagem;

	private Object parametros[];
	
	public Excecao(String mensagem, Object... parametros) {
		super();
		this.mensagem = mensagem;
		this.parametros = parametros;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Object[] getParametros() {
		return parametros;
	}

	public void setParametros(Object[] parametros) {
		this.parametros = parametros;
	}
}