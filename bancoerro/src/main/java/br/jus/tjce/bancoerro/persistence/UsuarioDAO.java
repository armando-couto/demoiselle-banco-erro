package br.jus.tjce.bancoerro.persistence;


import javax.persistence.Query;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.jus.tjce.bancoerro.domain.Usuario;

@PersistenceController
public class UsuarioDAO extends JPACrud<Usuario, Long> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Buscar um usuário através dos parâmetros informados
	 * @param login
	 * @param senha
	 * @return Objeto Usuário
	 */
	
	public Usuario buscarUsuario(String login, String senha){
		
		Query query = createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha");
		
		query.setParameter("login", login);
		query.setParameter("senha", senha);

		if (query.getResultList().isEmpty()){
			return null;
		}
		else {
			return (Usuario) query.getSingleResult();
		}
	}
}