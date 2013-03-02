package br.jus.tjce.bancoerro.business;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.jus.tjce.bancoerro.domain.Perfil;
import br.jus.tjce.bancoerro.persistence.PerfilDAO;

@BusinessController
public class PerfilBC extends DelegateCrud<Perfil, Long, PerfilDAO>{

	private static final long serialVersionUID = 1L;
}