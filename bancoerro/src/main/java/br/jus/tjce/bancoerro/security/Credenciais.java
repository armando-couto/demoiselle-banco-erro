package br.jus.tjce.bancoerro.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class Credenciais implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	public Credenciais() {}
	
	public Credenciais(String login, String senha) {
		this.login = login;
		this.senha = senha;
	}
	
	public void clear() {
		this.setLogin(null);
		this.setSenha(null);
	}
	
	/**
	 * Métodos Get's and Set's
	 * @return
	 */
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
}