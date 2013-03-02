package br.jus.tjce.bancoerro.business;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.persistence.SistemaDAO;
import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;

@BusinessController
public class SistemaBC extends DelegateCrud<Sistema, Long, SistemaDAO> {

	private static final long serialVersionUID = 1L;

	@Inject
	private SistemaDAO sistemaDAO;

	@Inject
	private LogBC logBC;
	
	@Inject
	private TJCEException tjcee;

	@Override
	public void insert(Sistema bean) {

		if (validarFiltrosInsert(bean)) {

			bean.setCodigo(bean.getCodigo().toUpperCase());
			bean.setDescricao(bean.getDescricao().toUpperCase());

			logBC.insert(AcaoEnum.CADASTRADO, EntidadeEnum.SISTEMA, null, bean, null);

			sistemaDAO.insert(bean);
		}
	}

	@Override
	public void update(Sistema bean) {

		if (validarFiltrosUpdate(bean)) {

			bean.setCodigo(bean.getCodigo().toUpperCase());
			bean.setDescricao(bean.getDescricao().toUpperCase());

			logBC.insert(AcaoEnum.ALTERADO, EntidadeEnum.SISTEMA, null, bean, null);

			sistemaDAO.update(bean);
		}
	}

	@Override
	public void delete(Long id) {
		
		logBC.insert(AcaoEnum.DELETADO, EntidadeEnum.SISTEMA, null, sistemaDAO.load(id), null);
		
		sistemaDAO.delete(id);
	}
	
	/**
     * Valida os filtros(campos) da tela     
     * @param bean
     * @return true, se todos os filtros foram validados corretamente, ou false, caso contrário.
     */
    public boolean validarFiltrosInsert(Sistema bean){
        boolean resultado = true;
       
        resultado &= validarCampoCodigo(bean);
        resultado &= validarCampoDescricao(bean);
        resultado &= validarCampoObservacao(bean);
       
        if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
        
        return resultado;
    }
   
    public boolean validarFiltrosUpdate(Sistema bean){
        boolean resultado = true;
       
        resultado &= validarCampoDescricao(bean);
        resultado &= validarCampoObservacao(bean);
       
        if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
        
        return resultado;
    }
    
    /**
     * Valida o campo codigo
     * @param bean
     * @return true, se todas as condições são atendidas, caso contrário, false.
     */
    public boolean validarCampoCodigo(Sistema bean){
        boolean resultado = true;
       
        if(bean.getCodigo() == null || bean.getCodigo().equals("")){
        	tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","código");
            resultado = false;
        }else{
            for (Sistema element : sistemaDAO.findAll()) {
	            if(element.getCodigo().equals(bean.getCodigo().toUpperCase())){
	            	tjcee.adicionarMensagem("{mensagem.sistema.cadastrar.codigoexiste}");
                    resultado = false;
	            }
            }
        }
        return resultado;
    }
   
    /**
     * Valida o campo descrição
     * @param bean
     * @return true, se todas as condições são atendidas, caso contrário, false.
     */
    public boolean validarCampoDescricao(Sistema bean){
        boolean resultado = true;
       
        int tamanhoDescricao = bean.getDescricao().trim().length();
       
        if(bean.getDescricao() == null || bean.getDescricao().equals("")){
        	tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","descrição");
            resultado = false;
        }else{
            if(tamanhoDescricao < 10 || tamanhoDescricao > 100){
            	tjcee.adicionarMensagem("{mensagem.sistema.validacao.tamanhoCampoDescricao}");
                resultado = false;
            }
        }              
        return resultado;
    }
   
    /**
     * Valida o campo observação
     * @param bean
     * @return true, se todas as condições são atendidas, caso contrário, false.
     */
    public boolean validarCampoObservacao(Sistema bean){
        boolean resultado = true;
           
        int tamanhoObservacao = bean.getObservacao().trim().length();
   
        if(tamanhoObservacao > 1000){
        	tjcee.adicionarMensagem("{mensagem.sistema.cadastrar.obervacao}");
            resultado = false;
        }              
        return resultado;
    }
}