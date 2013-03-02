package br.jus.tjce.bancoerro.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", nullable = false)
	private Long id;
	
	@Column (name = "login", nullable = false, unique = true, length = 16)
	private String login; 
	
	@Column (name = "senha", nullable = false, length = 32)
	private String senha;
	
	@Column (name = "nome", nullable = false, length = 150)
	private String nome;
	
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "perfil", nullable = false)
    private Perfil perfil;
	
	public Usuario() {}

	/**
	 * Construtor
	 * @param login
	 * @param senha
	 * @param nome
	 * @param perfil
	 */
	public Usuario(String login, String senha, String nome, Perfil perfil) {
		super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.perfil = perfil;
	}
	
	@Override
	public boolean equals(Object obj) {
		return ((Usuario)obj).login.equals(this.login);
	};

	/**
	 * Métodos Get's and Set´s
	 */
	public String getLogin() {
		return login;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Perfil getPerfil() {
		return perfil;
	}
	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}
}