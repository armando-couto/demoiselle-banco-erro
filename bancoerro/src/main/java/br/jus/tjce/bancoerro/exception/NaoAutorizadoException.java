package br.jus.tjce.bancoerro.exception;

import br.gov.frameworkdemoiselle.annotation.Redirect;
import br.gov.frameworkdemoiselle.exception.ApplicationException;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.AuthorizationException;

@ApplicationException(severity=SeverityType.ERROR)
@Redirect(viewId="/index.xhtml")
public class NaoAutorizadoException extends AuthorizationException{

	private static final long serialVersionUID = 1L;
	
	public NaoAutorizadoException(String mensagem) {
		super(mensagem);
	}
}