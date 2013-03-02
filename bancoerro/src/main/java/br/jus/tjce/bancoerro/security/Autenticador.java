package br.jus.tjce.bancoerro.security;

import javax.enterprise.inject.Alternative;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.security.Authenticator;
import br.gov.frameworkdemoiselle.security.User;
import br.jus.tjce.bancoerro.business.UsuarioBC;
import br.jus.tjce.bancoerro.domain.Usuario;

@Alternative
public class Autenticador implements Authenticator {

	private static final long serialVersionUID = 1L;

	@Inject
	private Credenciais credenciais;

	@Inject
	private UsuarioBC usuarioBC;

	private MyUser user = null;

	@Override
	public boolean authenticate() {

		boolean autenticado = false;

		Usuario usuario = usuarioBC.load(credenciais.getLogin(), credenciais.getSenha());

		if (usuario != null) {
			autenticado = true;
			user = new MyUser();
			user.setAttribute("id", usuario.getId());
			user.setAttribute("nome", usuario.getNome());
			user.setAttribute("papel", usuario.getPerfil().getDescricao());
		}

		return autenticado;
	}

	@Override
	public User getUser() {
		return user;
	}

	@Override
	public void unAuthenticate() {
		this.user = null;
	}
}