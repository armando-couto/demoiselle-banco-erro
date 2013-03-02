package br.jus.tjce.bancoerro.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Sistema implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "id", nullable = false)
	private Long id;
	
	@Column (name = "codigo", nullable = false, unique = true, length = 4)
	private String codigo;
	
	@Column (name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column (name = "observacao", nullable = false, length = 1000)
	private String observacao;
	
	@OneToMany (mappedBy = "sistema", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<ErroAlerta> erroAlertas;
	
	public Sistema() {}

	/**
	 * Construtor
	 * @param codigo
	 * @param descricao
	 * @param observacao
	 * @param erroAlertas
	 */
	
	public Sistema(String codigo, String descricao, String observacao,
			List<ErroAlerta> erroAlertas) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.observacao = observacao;
		this.erroAlertas = erroAlertas;
	}

	/**
	 * Método hashCode
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Método equals
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sistema other = (Sistema) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	/**
	 * Métodos Get's and Set´s
	 */
	public String getCodigo() {
		return codigo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public List<ErroAlerta> getErroAlertas() {
		return erroAlertas;
	}
	public void setErroAlertas(List<ErroAlerta> erroAlertas) {
		this.erroAlertas = erroAlertas;
	}
}