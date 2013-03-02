package br.jus.tjce.bancoerro.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;

@Entity
public class Log implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", length = 20)
	private Long id;
	
	@Column (name = "acao", nullable = false, columnDefinition = "enum('CADASTRADO', 'DELETADO', 'ALTERADO','PESQUISADO')")
	@Enumerated (EnumType.STRING)
	private AcaoEnum acao;
	
	@Column (name = "entidade", nullable = false, columnDefinition = "enum('SISTEMA', 'ERRO', 'USUARIO', 'PERFIL')")
	@Enumerated (EnumType.STRING)
	private EntidadeEnum entidade;

	@Column (name = "usuario", length = 20)
	private String usuario;
	
	@Column (name = "loginUsuario", length = 10)
	private String loginUsuario;
	
	@Column (name = "codSistema", length = 20)
	private String codSistema;

	@Column (name = "codErroAlerta", length = 20)
	private String codErroAlerta;
	
	@Column (name = "data", nullable = false)
	@Temporal(value = TemporalType.DATE)
	private Date data;
	
	public Log() {}
	
	/**
	 * Construtor
	 * @param acao
	 * @param entidade
	 * @param usuario
	 * @param loginUsuario
	 * @param codSistema
	 * @param codErroAlerta
	 * @param data
	 */
	public Log(AcaoEnum acao, EntidadeEnum entidade, String usuario, String loginUsuario, String codSistema, String codErroAlerta, Date data) {
		this.acao = acao;
		this.entidade = entidade;
		this.usuario = usuario;
		this.loginUsuario = loginUsuario;
		this.codSistema = codSistema;
		this.codErroAlerta = codErroAlerta;
		this.data = data;
	}

	/**
	 * Métodos Get's and Set´s
	 * @return
	 */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AcaoEnum getAcao() {
		return acao;
	}
	public void setAcao(AcaoEnum acao) {
		this.acao = acao;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}	
	public String getCodSistema() {
		return codSistema;
	}
	public void setCodSistema(String codSistema) {
		this.codSistema = codSistema;
	}
	public String getCodErroAlerta() {
		return codErroAlerta;
	}
	public void setCodErroAlerta(String codErroAlerta) {
		this.codErroAlerta = codErroAlerta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public EntidadeEnum getEntidade() {
		return entidade;
	}
	public void setEntidade(EntidadeEnum entidade) {
		this.entidade = entidade;
	}
	public String getLoginUsuario() {
		return loginUsuario;
	}
	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}
}