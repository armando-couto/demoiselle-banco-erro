package br.jus.tjce.bancoerro.persistence;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.jus.tjce.bancoerro.domain.Sistema;

@PersistenceController
public class SistemaDAO extends JPACrud<Sistema, Long> {

	private static final long serialVersionUID = 1L;
}