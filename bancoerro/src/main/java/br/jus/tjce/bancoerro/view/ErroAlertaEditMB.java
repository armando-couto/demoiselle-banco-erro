package br.jus.tjce.bancoerro.view;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import br.gov.frameworkdemoiselle.annotation.NextView;
import br.gov.frameworkdemoiselle.annotation.PreviousView;
import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractEditPageBean;
import br.jus.tjce.bancoerro.business.ErroAlertaBC;
import br.jus.tjce.bancoerro.domain.ErroAlerta;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.util.AcaoEnum;

@ViewController
@NextView("/erro_alerta_alterar.jsf")
@PreviousView("/erro_alerta_listar.jsf")
@RequiredRole({ "ADMINISTRADOR", "FUNCIONARIO" })
public class ErroAlertaEditMB extends AbstractEditPageBean<ErroAlerta, Long> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private MessageContext messageContext;
	
	@Inject
	private ErroAlertaBC erroAlertaBC;

	@Override
	public String insert() {
		
		erroAlertaBC.insert(getBean());			
		this.setBean(null);
		messageContext.add(AcaoEnum.CADASTRADO.getDescricao(), SeverityType.INFO, "");
		return null;
	}

	@Override
	public String delete() {	
		
		erroAlertaBC.delete(getBean().getId());
		clear();
		messageContext.add(AcaoEnum.DELETADO.getDescricao(), SeverityType.INFO, "");
		return getPreviousView();
	}

	@Override
	public String update() {
		
		erroAlertaBC.update(getBean());	
		messageContext.add(AcaoEnum.ALTERADO.getDescricao(), SeverityType.INFO, "");			
		return getPreviousView();
	}

	@Override
	protected void handleLoad() {
		
		setBean(erroAlertaBC.load(getId()));
	}

	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
	
	public void preencher(ValueChangeEvent event) {
		
		if (event.getNewValue()==null){
			this.getBean().setCodigo(null);
		}
		else{
			String codigo = erroAlertaBC.preencherCampoCodigo(((Sistema)event.getNewValue()).getCodigo());
			this.getBean().setCodigo(codigo);
		}
	}
}