package br.jus.tjce.bancoerro.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import br.jus.tjce.bancoerro.business.ErroAlertaBC;
import br.jus.tjce.bancoerro.domain.ErroAlerta;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;

@ViewController
public class ConsultaErroMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	private String codigo;
	
	private boolean status;
	
	@Inject
	private MessageContext messageContext;
	
	@Inject
	private ErroAlerta erroAlerta;	
	
	@Inject
	private ErroAlertaBC erroAlertaBC;

	public void pesquisar() {
		
		erroAlerta = erroAlertaBC.buscarErroAlertaByCodigo(codigo.toUpperCase());
		status = true;
		this.setCodigo(null);
	}
	
	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		status = false;
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
	
	/**
	 * Métodos Get's and Set's
	 * @return
	 */
	public ErroAlerta getErroAlerta() {
		return erroAlerta;
	}
	public void setErroAlerta(ErroAlerta erroAlerta) {
		this.erroAlerta = erroAlerta;
	}
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
}
