package br.jus.tjce.bancoerro.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.jus.tjce.bancoerro.domain.Perfil;

@PersistenceController
public class PerfilDAO extends JPACrud<Perfil, Long>{

	private static final long serialVersionUID = 1L;
}