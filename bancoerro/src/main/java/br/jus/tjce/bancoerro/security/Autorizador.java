package br.jus.tjce.bancoerro.security;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authorizer;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.jus.tjce.bancoerro.exception.TJCEException;

@Alternative
public class Autorizador implements Authorizer {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private SecurityContext securityContext;
	
	@Inject
	private TJCEException tjcee;

	@Override
	public boolean hasPermission(String arg0, String arg1) {
		return true;
	}

	@Override
	// Responde pela anotacao @RequiredRole (nao utilizada neste exemplo)
	public boolean hasRole(String role) {
		boolean autorizado = false;

		if (securityContext.isLoggedIn()) {
			if (securityContext.getUser().getAttribute("papel").equals(role)) {
				autorizado = true;
			}
		} 
		else {
			tjcee.adicionarMensagem("{mensagem.autorizador.erro}");
		}
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 

		return autorizado;
	}
}