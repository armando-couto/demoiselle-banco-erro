package br.jus.tjce.bancoerro.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.jus.tjce.bancoerro.business.PerfilBC;
import br.jus.tjce.bancoerro.domain.Perfil;

@ViewController
public class PerfilListMB extends AbstractListPageBean<Perfil, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private PerfilBC perfilBC;
	
	@Override
	protected List<Perfil> handleResultList() {
		return perfilBC.findAll();
	}
}