package br.jus.tjce.bancoerro.business;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.stereotype.BusinessController;
import br.gov.frameworkdemoiselle.template.DelegateCrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.jus.tjce.bancoerro.domain.ErroAlerta;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.persistence.ErroAlertaDAO;
import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;

@BusinessController
public class ErroAlertaBC extends DelegateCrud<ErroAlerta, Long, ErroAlertaDAO> {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ErroAlertaDAO erroAlertaDAO;

	@Inject
	private LogBC logBC;
	
	@Inject
	private TJCEException tjcee;

	@Override
	public void insert(ErroAlerta bean) {

		if (validarFiltrosInsert(bean)) {
			logBC.insert(AcaoEnum.CADASTRADO, EntidadeEnum.ERRO, null, null, bean);
			erroAlertaDAO.insert(bean);
		}
	}

	@Override
	public void update(ErroAlerta bean) {
		
		if(validarFiltrosUpdate(bean)){
			logBC.insert(AcaoEnum.ALTERADO, EntidadeEnum.ERRO, null, null, bean);
			erroAlertaDAO.update(bean);
		}	
	}

	@Override
	public void delete(Long id) {
		
		logBC.insert(AcaoEnum.DELETADO, EntidadeEnum.ERRO, null, null, erroAlertaDAO.load(id));
		erroAlertaDAO.delete(id);
	}

	/**
	 * Valida os filtros(campos) da tela	 
	 * @param bean
	 * @return true, se todos os filtros foram validados corretamente, ou false, caso contrário.
	 */
	public boolean validarFiltrosInsert(ErroAlerta bean){
		boolean resultado = true;
		
		resultado &= validarCampoSistema(bean);
		resultado &= validarCampoCodigo(bean);
		resultado &= validarCampoDescricao(bean);
		resultado &= validarCampoMotivo(bean);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	public boolean validarFiltrosUpdate(ErroAlerta bean){
		boolean resultado = true;
		
		resultado &= validarCampoDescricao(bean);
		resultado &= validarCampoMotivo(bean);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	/**
	 * Valida o campo sistema
	 * @param bean
	 * @return true, se todas as condições são atendidas, caso contrário, false.
	 */
	public boolean validarCampoSistema(ErroAlerta bean){
		boolean resultado = true;
		
		if(bean.getSistema() == null || bean.getSistema().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","sistema");
			resultado = false;
		}
		 
		return resultado;
	}
	
	/**
	 * Valida o campo código
	 * @param bean
	 * @return true, se todas as condições são atendidas, caso contrário, false.
	 */
	public boolean validarCampoCodigo(ErroAlerta bean){
		boolean resultado = true;
		
		if(bean.getCodigo() == null || bean.getCodigo().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","código");
			resultado = false;
		}
		else {
			for (ErroAlerta erroAlerta : erroAlertaDAO.findAll()) {
				if (erroAlerta.getSistema().getCodigo().equals(bean.getSistema().getCodigo()) && erroAlerta.getCodigo().equals(bean.getCodigo())){
					tjcee.adicionarMensagem("{mensagem.erroalerta.validacao.codigoerro_codigosistema}");
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
	public boolean validarCampoDescricao(ErroAlerta bean){
		boolean resultado = true;
		
		int tamanhoDescricao = bean.getDescricao().trim().length();
		
		if(bean.getDescricao() == null || bean.getDescricao().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","descrição");
			resultado = false;
		}else{
			if(tamanhoDescricao < 10 || tamanhoDescricao > 100){
				tjcee.adicionarMensagem("{mensagem.erroalerta.validacao.tamanhocampodescricao}");
				resultado = false;
			}
		}		
		return resultado;
	}
	
	/**
	 * Valida o campo motivo
	 * @param bean
	 * @return true, se todas as condições são atendidas, caso contrário, false.
	 */
	public boolean validarCampoMotivo(ErroAlerta bean){
		boolean resultado = true;
		
		if(bean.getMotivo() == null || bean.getMotivo().equals("") || bean.getMotivo().equals("<br>")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}","motivo");
			resultado = false;
		}
		return resultado;
	}
	
	/**
	 * Buscar um objeto Erro/Alerta através de um parâmetro
	 * dividido em: código do sistema e código erro/alerta. 
	 * @param codigo
	 * @return Um objeto do tipo Erro/Alerta
	 */
	@Transactional
	public ErroAlerta buscarErroAlertaByCodigo(String codigo){
		
		if (codigo == null || codigo.trim().isEmpty()) {
			tjcee.adicionarMensagem("{mensagem.erroalerta.consultar.erro}");
		}
		else {
			String codSistema = codigo.substring(0, 4);
			String codErroAlerta = codigo.substring(5, 9);
			
			ErroAlerta erroAlerta = erroAlertaDAO.pesquisarErroAlerta(codSistema, codErroAlerta);
			
			if (erroAlerta == null){
				tjcee.adicionarMensagem("{mensagem.generica.excecao}");
			}
			else {
				
				erroAlerta.setContador(erroAlerta.getContador()+1);
				erroAlertaDAO.update(erroAlerta);
				
				logBC.insert(AcaoEnum.PESQUISADO, EntidadeEnum.ERRO, null, erroAlerta.getSistema(), erroAlerta);
				
				return erroAlertaDAO.pesquisarErroAlerta(codSistema, codErroAlerta);
			} 
		}
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return null;
	}
	
	/**
	 * Realizar o preenchimento do campo codigo à partir de um determinado parâmetro.
	 * @param codigo_sistema
	 * @return Um número formatado em 'XXXX' do último 'id' do erro/alerta
	 */
	@Transactional
	public String preencherCampoCodigo(String codigo_sistema){
		
		NumberFormat numberFormat = new DecimalFormat("0000");
		
		String erroAlertaString = erroAlertaDAO
			.pesquisarMaiorIdErroAlertaByCodSistema(codigo_sistema);
		
		if (erroAlertaString == null){
			return String.valueOf("0001");
		}
		else {
			Long erroAlertaLong = Long.parseLong(erroAlertaString); 
			if (erroAlertaString.equals("9999")) {
				tjcee.adicionarMensagem("{mensagem.erroalerta.preencher.erro}");
				return null;
			}
			else {
				return String.valueOf(numberFormat.format(erroAlertaLong+1));
			}
		}
	}
	
	/**
	 * Buscar uma lista de erros/alertas por um determinado parâmetro.
	 * @param codigo
	 * @return Lista de erros/alertas
	 */
	@Transactional
	public List<ErroAlerta> buscar(String codigo) {
		return erroAlertaDAO.pesquisarErroAlertaByCodSistema(codigo);
	}
}