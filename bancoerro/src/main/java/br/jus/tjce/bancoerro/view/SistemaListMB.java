package br.jus.tjce.bancoerro.view;

import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.jus.tjce.bancoerro.business.SistemaBC;
import br.jus.tjce.bancoerro.domain.Sistema;

@ViewController
@RequiredRole ({"ADMINISTRADOR","FUNCIONARIO"})
public class SistemaListMB extends AbstractListPageBean<Sistema, Long> {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaBC sistemaBC;
	
	@Override
	protected List<Sistema> handleResultList() {
		return sistemaBC.findAll();
	}
}
