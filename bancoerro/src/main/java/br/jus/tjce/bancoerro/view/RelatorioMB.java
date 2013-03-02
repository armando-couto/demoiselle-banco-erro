package br.jus.tjce.bancoerro.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import br.gov.frameworkdemoiselle.exception.ExceptionHandler;
import br.gov.frameworkdemoiselle.message.MessageContext;
import br.gov.frameworkdemoiselle.message.SeverityType;
import br.gov.frameworkdemoiselle.report.Report;
import br.gov.frameworkdemoiselle.report.Type;
import br.gov.frameworkdemoiselle.report.annotation.Path;
import br.gov.frameworkdemoiselle.security.RequiredRole;
import br.gov.frameworkdemoiselle.stereotype.ViewController;
import br.gov.frameworkdemoiselle.template.AbstractPageBean;
import br.gov.frameworkdemoiselle.util.FileRenderer;
import br.jus.tjce.bancoerro.business.RelatorioBC;
import br.jus.tjce.bancoerro.domain.Log;
import br.jus.tjce.bancoerro.domain.Relatorio;
import br.jus.tjce.bancoerro.domain.Sistema;
import br.jus.tjce.bancoerro.exception.Excecao;
import br.jus.tjce.bancoerro.exception.TJCEException;
import br.jus.tjce.bancoerro.util.DateUtil;

@ViewController
@RequiredRole ({"ADMINISTRADOR","FUNCIONARIO"})
public class RelatorioMB extends AbstractPageBean {

	private static final long serialVersionUID = 1L;

	@Inject
	@Path("reports/RelatorioErroAlerta.jrxml")
	private Report relatorioErros;
	
	@Inject
	@Path("reports/RelatorioLogOperacao.jrxml")
	private Report relatorioLog;
	
	@Inject
	private MessageContext messageContext;
	
	@Inject
	private FileRenderer fileRenderer;
	
	@Inject
	private RelatorioBC relatorioBC;
	
	/**
	 * Atributos dos Relatórios
	 */
	private List<Object> lista;
	private Date dataIni;
	private Date dataFim;
	
	/**
	 * Atributos Relatório Erros
	 */
	private Sistema sistema;
	private String codigoErro;
	
	/**
	 * Atributos Relatório Log's
	 */
	private String descricaoUsuario;
	private String descricaoAcao;
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	/**
	 * Construtor
	 */
	public RelatorioMB() {
		lista = new ArrayList<Object>();
	}	
	
	public String showReportErro(){
		
		if(relatorioBC.validarFiltrosErro(getSistema(), getDataIni(), getDataFim())){
			
			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("instituicao", "Tribunal de Justiça do Ceará");
			parametros.put("sistema", "Sistema de Banco de Erros");
			parametros.put("titulo", "Relatório dos Erros e Alertas");
			parametros.put("descricaoSistema", sistema.getDescricao());
			parametros.put("codigoErro", codigoErro);
			parametros.put("resultado", getCountErroBySistema());
			parametros.put("dataInicial", df.format(dataIni));
			parametros.put("dataFinal", df.format(dataFim));
			
			try {
				if(getResultList() != null && !getResultList().isEmpty()) {
					byte[] buffer = this.relatorioErros.export(getResultList(), parametros, Type.PDF);
					
					this.fileRenderer.render(buffer, FileRenderer.ContentType.PDF, "relatorio.pdf");
				}
				else {
					messageContext.add("{mensagem.generica.relatoriovazio}", SeverityType.ERROR, "");
				}
			} catch (Exception e) {
				messageContext.add(e.getMessage(), SeverityType.ERROR, "");
			}
		}
		return null;
	}
	
	public String showReportLog() {
		
		if(relatorioBC.validarFiltrosLog(getDataIni(), getDataFim())){
			
			Map<String, Object> parametros = new HashMap<String, Object>();
	
			parametros.put("instituicao", "Tribunal de Justiça do Ceará");
			parametros.put("sistema", "Sistema de Banco de Erros");
			parametros.put("titulo", "Relatório dos Logs");
			parametros.put("descricaoUsuario", getDescricaoUsuario());
			parametros.put("descricaoAcao", getDescricaoAcao());
			parametros.put("dataInicial", df.format(dataIni));
			parametros.put("dataFinal", df.format(dataFim));
			
			try {
				if(getResultListLog() != null && !getResultListLog().isEmpty()) {
					byte[] buffer = this.relatorioLog.export(getResultListLog(), parametros, Type.PDF);
					
					this.fileRenderer.render(buffer, FileRenderer.ContentType.PDF, "relatorio.pdf");
				}
				else {
					messageContext.add("{mensagem.generica.relatoriovazio}", SeverityType.ERROR, "");
				}
			} catch (Exception e) {
				messageContext.add(e.getMessage(), SeverityType.ERROR, "");
			}
		}
		return null;
	}
	
	public List<Relatorio> getResultList(){
		
		List<Relatorio> listaRelatorio = new ArrayList<Relatorio>();
		
		try {			
			Date dtFim = DateUtil.adicionaDias(1, dataFim);
			listaRelatorio =  relatorioBC.buscarErroAlertaMaisPesquisados(sistema.getCodigo(), codigoErro, dataIni, dtFim);			
		} catch (Exception e) {
			messageContext.add(e.getMessage(), SeverityType.ERROR, "");
		}
		return listaRelatorio;
	}
	
	@ExceptionHandler
	public void tratador(TJCEException exception) {
		
		for (Excecao excecao : exception.getExcecoes()) {
			messageContext.add(excecao.getMensagem(), SeverityType.ERROR, excecao.getParametros());
		}
		exception.getExcecoes().clear();
	}
	
	public List<Log> getResultListLog(){
		
		List<Log> listaRelatorioLog = new ArrayList<Log>();
		
		try {			
			Date dtFim = DateUtil.adicionaDias(1, dataFim);
			listaRelatorioLog =  relatorioBC.buscarLogOperacaoByUsuario(descricaoUsuario, descricaoAcao, dataIni, dtFim);			
		} catch (Exception e) {
			messageContext.add(e.getMessage(), SeverityType.ERROR, "");
		}
		return listaRelatorioLog;
	}
	
	/**
	 * Obtém a quantidade de erro/alerta de um determinado sistema
	 * @return A Quantidade (Long)
	 */
	
	public Long getCountErroBySistema(){
		
		Long quantidade = 0L;
		
		if(getResultList() != null && !getResultList().isEmpty()){
            for (Relatorio item : getResultList()) {
                    quantidade += item.getQuantidadePesquisaPorErro();
            }
		}
		return quantidade;
	}
	
	/**
	 * Métodos Gets e Sets
	 * @return
	 */
	public Sistema getSistema() {
		return sistema;
	}
	public void setSistema(Sistema sistema) {
		this.sistema = sistema;
	}
	public String getCodigoErro() {
		return codigoErro;
	}
	public void setCodigoErro(String codigoErro) {
		this.codigoErro = codigoErro;
	}	
	public Date getDataIni() {
		return dataIni;
	}
	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}
	public List<Object> getLista() {
		return lista;
	}
	public void setLista(List<Object> lista) {
		this.lista = lista;
	}
	public String getDescricaoUsuario() {
		return descricaoUsuario;
	}
	public void setDescricaoUsuario(String descricaoUsuario) {
		this.descricaoUsuario = descricaoUsuario;
	}
	public String getDescricaoAcao() {
		return descricaoAcao;
	}
	public void setDescricaoAcao(String descricaoAcao) {
		this.descricaoAcao = descricaoAcao;
	}	
}