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
import br.jus.tjce.bancoerro.business.SistemaBC;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.util.AcaoEnum;

@ViewController
@NextView ("/sistema_alterar.jsf")
@PreviousView ("/sistema_listar.jsf")
@RequiredRole ("ADMINISTRADOR")
public class SistemaEditMB extends AbstractEditPageBean<Sistema, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MessageContext messageContext;
	
	@Inject
	private SistemaBC sistemaBC;

	@Override
	public String insert() {	
		
		sistemaBC.insert(getBean());
		this.setBean(null);
		messageContext.add(AcaoEnum.CADASTRADO.getDescricao(), SeverityType.INFO, "");
		return null;
	}
	
	@Override
	public String delete() {
		
		sistemaBC.delete(getBean().getId());	
		clear();
		messageContext.add(AcaoEnum.DELETADO.getDescricao(), SeverityType.INFO, "");
		return getPreviousView();
	}

	@Override
	public String update() {
		
		sistemaBC.update(getBean());
		messageContext.add(AcaoEnum.ALTERADO.getDescricao(), SeverityType.INFO, "");
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		
		setBean(sistemaBC.load(getId()));
	}
	
	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
}