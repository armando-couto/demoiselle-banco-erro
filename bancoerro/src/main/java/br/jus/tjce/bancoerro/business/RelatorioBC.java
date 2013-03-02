package br.jus.tjce.bancoerro.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import br.jus.tjce.bancoerro.domain.Log;
import br.jus.tjce.bancoerro.domain.Relatorio;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.persistence.RelatorioDAO;
import br.jus.tjce.bancoerro.util.AcaoEnum;
import br.jus.tjce.bancoerro.util.EntidadeEnum;

public class RelatorioBC {

	/**
	 * logger de ações realizadas.
	 */
	private static final Logger LOGGER = Logger.getLogger(RelatorioBC.class);
	
	@Inject
	private RelatorioDAO relatorioDAO;
	
	@Inject
	private TJCEException tjcee;

	/**
	 * Buscar erros/alertas mais pesquisados.
	 * 
	 * @return Lista de logger's.
	 */
	public List<Relatorio> buscarErroAlertaMaisPesquisados(String codigoSistema, String codigoErro, Date dataInicial, Date dataFinal) throws Exception {

		List<Relatorio> resultado = null;

		try {

			List<Object> resultList = relatorioDAO.buscar(codigoSistema, codigoErro, dataInicial, dataFinal);

			if (resultList != null && !resultList.isEmpty()) {
				resultado = new ArrayList<Relatorio>();
				for (Object object : resultList) {
					Object[] temp = (Object[]) object;
					String codigoErroAlerta = (String) temp[0];
					String descricaoErro = (String) temp[1];
					Long quantidadePesquisaPorErro = (Long) temp[2];
					resultado.add(new Relatorio(codigoErroAlerta, quantidadePesquisaPorErro, descricaoErro));
				}
			}

		} catch (Exception e) {
			LOGGER.error("ERRO GERAL RELATÓRIO BUSCAR BC", e);
			throw new Exception("ERRO GERAL NEGÓCIO", e);
		}
		return resultado;
	}
	
	public List<Log> buscarLogOperacaoByUsuario(String descricaoUsuario, String descricaoOperacao, Date dataIni, Date dataFim) throws Exception{
 	
		List<Log> resultado = null;
		
		try {
						
			List<Object> resultList = relatorioDAO.buscarLogOperacao(descricaoUsuario, descricaoOperacao, dataIni, dataFim);
			
			if (resultList != null && !resultList.isEmpty()) {
				resultado = new ArrayList<Log>();
				for (Object object : resultList) {
					Object[] temp = (Object[]) object;
					String usuario = (String) temp[0];
					AcaoEnum acao = (AcaoEnum) temp[1];
					EntidadeEnum entidade = (EntidadeEnum) temp[2];
					String loginUsuario = (String) temp[3];
					String codSistema = (String) temp[4];
					String codErroAlerta = (String) temp[5];
					Date data = (Date) temp[6];
					resultado.add(new Log(acao, entidade, usuario, loginUsuario, codSistema, codErroAlerta, data));
				}
			}
			
		} catch (Exception e) {
			LOGGER.error("ERRO GERAL RELATÓRIO BUSCAR BC",e);
			throw new Exception("ERRO GERAL NEGÓCIO",e);
		}
		return resultado;
	}
	
	/**
	 * Valida os filtros da tela de relatório erro
	 * @return true, se todas as condições foram atendidas, caso contrário,false.
	 */
	public boolean validarFiltrosErro(Sistema sistema, Date dateIni, Date dataFim){
		
		boolean resultado = true;
		
		resultado &= validarCampoSistema(sistema);
		resultado &= validarCamposData(dateIni, dataFim);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	/**
	 * Valida os filtros da tela de relatório log
	 * @return true, se todas as condições foram atendidas, caso contrário,false.
	 */
	public boolean validarFiltrosLog(Date dateIni, Date dataFim){
		
		boolean resultado = true;
		
		resultado &= validarCamposData(dateIni, dataFim);
		
		if (!tjcee.getExcecoes().isEmpty()) {  
			throw tjcee;  
		} 
		
		return resultado;
	}
	
	/**
	 * Valida o campo sistema
	 * @return true, se todas as condições foram atendidas, caso contrário,false.
	 */
	public boolean validarCampoSistema(Sistema sistema){
		boolean retorno = true;
		
		if(sistema == null || sistema.equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}", "sistema");
			retorno = false;
		}
		return retorno;
	}
	
	/**
	 * Valida os campos data
	 * @return true, se todas as condições foram atendidas, caso contrário,false.
	 */
	public boolean validarCamposData(Date dateIni, Date dataFim){
		boolean retorno = true;
		
		if(dateIni == null || dateIni.toString().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}", "data inicial");
			retorno = false;
		}
		
		if(dataFim == null || dataFim.toString().equals("")){
			tjcee.adicionarMensagem("{mensagem.generica.campo.obrigatorio}", "data final");
			retorno = false;
		}
		
		if(dateIni != null && dataFim != null){
		
			//Validação do perído inválido
            if(dateIni.after(dataFim)){
            	tjcee.adicionarMensagem("{mensagem.generica.dataInicialMaiordataFinal}", "Data inicial", "data final");
                retorno = false;
            }
          
            if(dataFim.after(new Date())){
            	tjcee.adicionarMensagem("{mensagem.generica.dataFinalMaiordataAtual}");
                retorno = false;
            }
		}
		
		return retorno;
	}
}