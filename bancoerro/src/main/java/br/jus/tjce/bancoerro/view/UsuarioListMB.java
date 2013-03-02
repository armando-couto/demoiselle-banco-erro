package br.jus.tjce.bancoerro.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.jus.tjce.bancoerro.business.UsuarioBC;
import br.jus.tjce.bancoerro.domain.Usuario;

@ViewController
@RequiredRole ({ "ADMINISTRADOR", "FUNCIONARIO" })
public class UsuarioListMB extends AbstractListPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioBC usuarioBC;
	
	@Override
	protected List<Usuario> handleResultList() {
		return usuarioBC.findAll();
	}
}