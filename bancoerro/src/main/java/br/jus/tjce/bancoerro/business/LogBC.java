package br.jus.tjce.bancoerro.business;

import java.util.Date;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.SecurityContext;
import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.jus.tjce.bancoerro.domain.ErroAlerta;
import br.jus.tjce.bancoerro.domain.Log;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.domain.Usuario;
import br.jus.tjce.bancoerro.persistence.LogDAO;
import br.jus.tjce.bancoerro.persistence.UsuarioDAO;
import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;

@BusinessController
public class LogBC extends DelegateCrud<Log, Long, LogDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private SecurityContext securityContext;
	
	@Inject
	private Usuario usuario;
	
	@Inject
	private LogDAO logDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Transactional
	public void insert(AcaoEnum acao, EntidadeEnum entidade, Usuario loginUsuario, Sistema sistema, ErroAlerta erroAlerta) {
		
		if (securityContext.getUser() != null){
			usuario = usuarioDAO.load(securityContext.getUser().getAttribute("id"));
		}
		else {
			usuario = null;
		}
		
		if (usuario != null && sistema != null && erroAlerta != null) {
			Log logger = new Log(acao, entidade, usuario.getLogin(), null, sistema.getCodigo(), erroAlerta.getCodigo(), new Date());
			
			logDAO.insert(logger);
		}
		else if (usuario != null && sistema != null){
			Log logger = new Log(acao, entidade, usuario.getLogin(), null, sistema.getCodigo(), null, new Date());
			
			logDAO.insert(logger);
		}
		else if (usuario != null && erroAlerta != null) {
			Log logger = new Log(acao, entidade, usuario.getLogin(), null, erroAlerta.getSistema().getCodigo(), erroAlerta.getCodigo(), new Date());
			
			logDAO.insert(logger);
		}
		else if (usuario != null) {
			Log logger = new Log(acao, entidade, usuario.getLogin(), loginUsuario.getLogin(), null, null, new Date());
			
			logDAO.insert(logger);
		}
		else {
			Log logger = new Log(acao, entidade, null, null, sistema.getCodigo(), erroAlerta.getCodigo(), new Date());
			
			logDAO.insert(logger);
		}
	} 
}