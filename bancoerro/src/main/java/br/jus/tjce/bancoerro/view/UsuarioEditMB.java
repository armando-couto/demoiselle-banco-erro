package br.jus.tjce.bancoerro.view;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.jus.tjce.bancoerro.business.UsuarioBC;
import br.jus.tjce.bancoerro.domain.Usuario;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.util.AcaoEnum;

@ViewController
@NextView ("/usuario_alterar.jsf")
@PreviousView ("/usuario_listar.jsf")
@RequiredRole ("ADMINISTRADOR")
public class UsuarioEditMB extends AbstractEditPageBean<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	private String senhaConfirmacao;
	
	@Inject
	private UsuarioBC usuarioBC;
	
	@Inject
	private MessageContext messageContext;
	
	@Override
	public String insert() {
		
		usuarioBC.insert(getBean(), senhaConfirmacao);
		this.setBean(null);
		this.setSenhaConfirmacao(null);
		messageContext.add(AcaoEnum.CADASTRADO.getDescricao(), SeverityType.INFO, "");
		return null;
	}
	
	@Override
	public String delete() {
		
		usuarioBC.delete(getBean().getId());
		clear();
		messageContext.add(AcaoEnum.DELETADO.getDescricao(), SeverityType.INFO, "");
		return getPreviousView();
	}

	@Override
	public String update() {
		
		usuarioBC.update(getBean(), senhaConfirmacao);
		messageContext.add(AcaoEnum.ALTERADO.getDescricao(), SeverityType.INFO, "");
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		
		setBean(usuarioBC.load(getId()));
		if (getBean() != null){
			getBean().setSenha(null);
		}	
	}

	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
	
	/**
	 * Métodos Get's and Set's
	 */	
	public String getSenhaConfirmacao() {
		return senhaConfirmacao;
	}
	public void setSenhaConfirmacao(String senhaConfirmacao) {
		this.senhaConfirmacao = senhaConfirmacao;
	}
}