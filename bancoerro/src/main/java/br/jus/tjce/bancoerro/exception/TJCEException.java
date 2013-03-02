package br.jus.tjce.bancoerro.exception;

import java.util.ArrayList;
import java.util.List;

public class TJCEException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private List<Excecao> excecoes;
	
	public TJCEException() {
		excecoes = new ArrayList<Excecao>();
	}
	
	public TJCEException(String mensagem, Object... parametros) {
		this();
		adicionarMensagem(mensagem, parametros);
	}

	public void adicionarMensagem(String mensagem, Object... parametros) {
		excecoes.add(new Excecao(mensagem, parametros));
	}

	public List<Excecao> getExcecoes() {
		return excecoes;
	}
}