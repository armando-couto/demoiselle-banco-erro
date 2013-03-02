package br.jus.tjce.bancoerro.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class ErroAlerta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", nullable = false)
	private Long id;
	
	@Column(name = "codigo", nullable = false, length = 4)
	private String codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@ManyToOne (cascade = CascadeType.PERSIST)
	@JoinColumn(name = "codigo_sistema", nullable = false)
	private Sistema sistema;

	@Lob
	@Column(name = "motivo", columnDefinition = "TEXT")
	private String motivo;

	@Column(name = "acao", columnDefinition = "TEXT")
	private String acao;
	
	@Column(name = "contador", nullable = false)
	private Long contador = (long) 0;

	public ErroAlerta() {}

	/**
	 * Construtor
	 * @param codigo
	 * @param descricao
	 * @param sistema
	 * @param motivo
	 * @param acao
	 * @param contador
	 */
	
	public ErroAlerta(String codigo, String descricao, Sistema sistema,
			String motivo, String acao, Long contador) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.sistema = sistema;
		this.motivo = motivo;
		this.acao = acao;
		this.contador = contador;
	}

	/**
	 * Métodos Get's and Set´s
	 */
	public Sistema getSistema() {
		return sistema;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Long getContador() {
		return contador;
	}
	public void setContador(Long contador) {
		this.contador = contador;
	}
}