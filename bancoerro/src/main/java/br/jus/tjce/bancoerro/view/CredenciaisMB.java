package br.jus.tjce.bancoerro.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.security.Credenciais;

@ViewController
public class CredenciaisMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private MessageContext messageContext;

	@Inject
	private SecurityContext securityContext;
	
	@Inject
	private Credenciais credenciais;
	
	@Inject
	private TJCEException tjcee;
	
	public void login() {
		
		securityContext.login();
	}

	public void logout() {
		
		if (securityContext.getUser().getId() != null) {
			securityContext.logout();
		} else {
			tjcee.adicionarMensagem("{mensagem.credenciais.logout.erro}");
		}
	}

	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
	
	/**
	 * Verifica o papel do usuário
	 * @return 'true' se o papel for Administrador, caso contrário, false.
	 */
	public boolean getVerificarPapel() {

		if (securityContext.getUser() != null) {
			if (securityContext.getUser().getAttribute("papel").equals("ADMINISTRADOR")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Métodos Get's and Set's
	 * @return
	 */
	public Credenciais getCredenciais() {
		return credenciais;
	}
	public void setCredenciais(Credenciais credenciais) {
		this.credenciais = credenciais;
	}
}