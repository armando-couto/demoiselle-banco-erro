package br.jus.tjce.bancoerro.view;

import java.util.Arrays;
import java.util.List;

import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractListPageBean;
import br.jus.tjce.bancoerro.util.AcaoEnum;

@ViewController
@RequiredRole ({"ADMINISTRADOR","FUNCIONARIO"})
public class AcaoListMB extends AbstractListPageBean<AcaoEnum, Integer>{

	private static final long serialVersionUID = 1L;
	
	@Override
	protected List<AcaoEnum> handleResultList() {
		return Arrays.asList(AcaoEnum.values());
	}
}