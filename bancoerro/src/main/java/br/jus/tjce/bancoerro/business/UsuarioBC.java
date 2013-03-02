package br.jus.tjce.bancoerro.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.jus.tjce.bancoerro.domain.Usuario;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.persistence.UsuarioDAO;
import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;
import br.jus.tjce.bancoerro.util.SenhaUtil;

@BusinessController
public class UsuarioBC extends DelegateCrud<Usuario, Long, UsuarioDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private LogBC logBC;
	
	@Inject
	private TJCEException tjcee;

	@Transactional
	public void insert(Usuario bean, String senhaConfirmacao) {

		if (validarFiltrosInsert(bean, senhaConfirmacao)) {

			bean.setLogin(bean.getLogin().toUpperCase());
			bean.setSenha(SenhaUtil.md5(bean.getSenha()));
			bean.setNome(bean.getNome().toUpperCase());

			logBC.insert(AcaoEnum.CADASTRADO, EntidadeEnum.USUARIO, bean, null, null);

			usuarioDAO.insert(bean);
		}
	}

	@Transactional
	public void update(Usuario bean, String senhaConfirmacao) {

		if (validarFiltrosUpdate(bean, senhaConfirmacao)){
			
			bean.setLogin(bean.getLogin().toUpperCase());
			bean.setSenha(SenhaUtil.md5(bean.getSenha()));
			bean.setNome(bean.getNome().toUpperCase());
			
			logBC.insert(AcaoEnum.ALTERADO, EntidadeEnum.USUARIO, bean, null, null);
			
			usuarioDAO.update(bean);
		}
	}
	
	@Override
	public void delete(Long id) {
		
		logBC.insert(AcaoEnum.DELETADO, EntidadeEnum.USUARIO, usuarioDAO.load(id), null, null);
		
		usuarioDAO.delete(id);
	}

	@Transactional
	public Usuario load(String login, String senha) {

		Usuario usuario = null;
		
		if (login == null || login.equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","usuário");
		}
		else if (senha == null || senha.equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","senha");
		}
		else {
			usuario = usuarioDAO.buscarUsuario(login.toUpperCase(), SenhaUtil.md5(senha));
			
			if (usuario == null){
				tjcee.adicionarMensagem("{mensagem.usuario.load.erro}");
			}
		}
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return usuario;
	}
	
	/**
	 * Valida os filtros(campos) da tela	 
	 * @param bean
	 * @return true, se todos os filtros foram validados corretamente, ou false, caso contrário.
	 */
	
	public boolean validarFiltrosInsert(Usuario bean, String senhaConfirmacao){
		boolean resultado = true;
		
		resultado &= validarCampoLogin(bean);
		resultado &= validarCampoSenha(bean);
		resultado &= validarCampoSenhaConfirmacao(bean, senhaConfirmacao);
		resultado &= validarCampoNome(bean);
		resultado &= validarCampoPerfil(bean);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	public boolean validarFiltrosUpdate(Usuario bean, String senhaConfirmacao){
		boolean resultado = true;
		
		resultado &= validarCampoSenha(bean);
		resultado &= validarCampoSenhaConfirmacao(bean, senhaConfirmacao);
		resultado &= validarCampoNome(bean);
		resultado &= validarCampoPerfil(bean);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	public boolean validarCampoLogin(Usuario bean){
		
		boolean resultado = true;
		
		if(bean.getLogin() == null || bean.getLogin().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","login");
			resultado = false;
		}else{
			for (Usuario element : usuarioDAO.findAll()) {
				if(element.getLogin().equals(bean.getLogin().toUpperCase())){
					tjcee.adicionarMensagem("{mensagem.usuario.cadastrar.existenciaLogin}");
					resultado = false;
				}
			}
		}
		return resultado;
	}
	
	public boolean validarCampoSenha(Usuario bean){
		
		boolean resultado = true;
		
		if(bean.getSenha() == null || bean.getSenha().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","senha");
			resultado = false;
		}else{
			if(bean.getSenha().length() < 6 || bean.getSenha().length() > 10){
				tjcee.adicionarMensagem("{mensagem.usuario.cadastrar.tamanhoSenha}");
				resultado = false;
			}
		}
		return resultado;
	}
	
	public boolean validarCampoSenhaConfirmacao(Usuario bean,String senhaConfirmacao){
		
		boolean resultado = true;
		
		if(senhaConfirmacao == null || senhaConfirmacao.equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","senha confirmação");
			resultado = false;
		}else{
			if(!(bean.getSenha().equals(senhaConfirmacao))){
				tjcee.adicionarMensagem("{mensagem.usuario.cadastrar.senhasDiferentes}");
				resultado = false;
			}
		}
		return resultado;
	}
	
	public boolean validarCampoNome(Usuario bean){
		
		boolean resultado = true;
		
		if(bean.getNome() == null || bean.getNome().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","nome");
			resultado = false;
		}
		return resultado;
	}
	
	public boolean validarCampoPerfil(Usuario bean){
		
		boolean resultado = true;
		
		if(bean.getPerfil() == null || bean.getPerfil().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","perfil");
			resultado = false;
		}
		return resultado;
	}
}