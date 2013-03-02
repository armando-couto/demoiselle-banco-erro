package br.jus.tjce.bancoerro.view;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.jus.tjce.bancoerro.business.ErroAlertaBC;
import br.jus.tjce.bancoerro.domain.ErroAlerta;

@ViewController
@NextView("/erro_alerta_listar.jsf")
@RequiredRole ({ "ADMINISTRADOR", "FUNCIONARIO" })
public class ErroAlertaListMB extends AbstractListPageBean<ErroAlerta, Long> {

	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private boolean status;
	private List<ErroAlerta> lista = new ArrayList<ErroAlerta>();
	
	@Inject
	private ErroAlertaBC erroAlertaBC;
	
	/**
	 * Pesquisa os erros/alertas e atribui 'true' a variável status
	 * para que seja renderizada a 'dataTable'.
	 */
	
	public void pesquisarErroAlerta() {
		this.setLista(erroAlertaBC.buscar(codigo));
		this.status = true;
	}
	
	@Override
	protected List<ErroAlerta> handleResultList() {
		return null;
	}
	
	/**
	 * Métodos Get's and Set's
	 */
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public List<ErroAlerta> getLista() {
		return lista;
	}
	public void setLista(List<ErroAlerta> lista) {
		this.lista = lista;
	}
}