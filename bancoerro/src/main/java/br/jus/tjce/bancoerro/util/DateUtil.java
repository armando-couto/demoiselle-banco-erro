package br.jus.tjce.bancoerro.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

/**
 * Classe utilitária para operações em datas.
 * 
 * @author d333379
 * 
 */
public class DateUtil {

	/**
	 * Fomato do Pattern padrão para data no sistema.
	 */
	public static final String FORMATO_DATA_PADRAO = "dd/MM/yyyy";
	

	/**
	 * Adiciona uma quantidade de dias na data.
	 * 
	 * @param dias
	 * @param data
	 * @return A data com a quantidade de dias adicionados
	 */
   public static final Date adicionaDias(int dias, Date data)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(6, dias);
        return calendar.getTime();
    }
	
	
	/**
	 * Verifica se a data inicial é maior que a data final. Se pelo menos uma
	 * das datas for null, retorna true.
	 * 
	 * @param dtInicio
	 * @param dtFim
	 * @return
	 */
	public static boolean isDataInicialMaiorQueFinal(Date dtInicio, Date dtFim) {
		if (dtInicio == null || dtFim == null)
			return true;

		// Critica se data inicial é maior que a data final.
		if (dtInicio.after(dtFim)) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a data final é maior que a data atual. Se a data final for
	 * null, retorna true.
	 * 
	 * @param dtFim
	 * @return
	 */
	public static boolean isDataFinalMaiorQueAtual(Date dtFim) {
		if (dtFim == null)
			return true;

		// Critica se data final é maior que a data atual.
		if (dtFim.after(new Date())) {
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a data inicial é anterior à data atual mais do que a
	 * quantidade de meses passada por parâmetro. Se a data inicial for null,
	 * retorna true.
	 * 
	 * @param dtInicio
	 * @param meses
	 * @return
	 */
	public static boolean isDataInicialAnterior(Date dtInicio, int meses) {
		if (meses <= 0)
			return true;

		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dtInicio);
		calendario.add(GregorianCalendar.MONTH, meses);
		calendario.add(GregorianCalendar.DAY_OF_MONTH, 1);
		if (calendario.getTime().before(new Date()))
			return true;
		return false;
	}
	
	/**
	 * Verifica se a data inicial é anterior à data final mais do que a
	 * quantidade de dias passado por parâmetro. Se a data inicial for null,
	 * retorna true.
	 * 
	 * @param dtInicio
	 * @param meses
	 * @return
	 */
	public static boolean isDiferencaDatasInferiorXDias(Date dtInicio, Date dtFim, int dias) {
		if (dias <= 0)
			return true;

		Calendar calendario = new GregorianCalendar();
		calendario.setTime(dtInicio);
		calendario.add(GregorianCalendar.DAY_OF_MONTH, dias);
		if (calendario.getTime().before(dtFim))
			return true;
		return false;
	}

	/**
	 * Esse método recebe uma String representando uma data no formato utilizado
	 * no Brasil (dd/MM/yyyy) e retorna um objeto <code>java.util.Date</code>
	 * representativo dessa mesma data.
	 *
	 * A informação de dia e mês pode conter um ou dois dígitos.
	 *
	 * @return java.util.Date representando a data informada como String.
	 *         Retorna <code>null</code> se a data for <code>null</code>.
	 * @param data
	 *            - data a ser reformatada.
	 * @throw IllegalArgumentException - se a data não representar um formato de
	 *        datas utilizado no Brasil ou não representar uma data válida.
	 */
	public static Date parseString2Date(String data) {
		Date retorno = null;

		if ((data == null) || (data.trim().equals(""))) {
			return retorno;
		}

		DateFormat df = new SimpleDateFormat(FORMATO_DATA_PADRAO);
		df.setLenient(false);
		try {
			retorno = df.parse(data);
		} catch (ParseException e) {
			throw new IllegalArgumentException("A data [" + data
					+ "] não representa uma data válida!");
		}
		return retorno;
	}

	/**
	 * Esse método recebe um objeto <code>java.util.Date</code> representando
	 * uma data obtida no banco de dados e retorna a representação dessa data em
	 * String no formato de data utilizada no Brasil (dd/MM/yyyy).
	 *
	 * @return String representando a data no formato brasileiro (dd/MM/yyyy).
	 *         Retorna <code>null</code> se a data for <code>null</code>.
	 * @param data
	 *            - data a ser reformatada.
	 */
	public static String parseDate2String(final Date data) {

		String retorno = null;

		if (data == null) {
			return retorno;
		}

		DateFormat df = new SimpleDateFormat(FORMATO_DATA_PADRAO);
		retorno = df.format(data);

		return retorno;
	}

	/**
	 * Valida se a data inicial é inferior a 10 anos da data atual Se a data
	 * inicial for inferir a data atual retorna 'true'
	 * 
	 * @param dataInicio
	 *            tipo String
	 * 
	 */
	public static boolean isDataInicialInferior10Anos(String dataInicio) {
		int anos = 10;
		Date dtAtual = new Date();
		Date dtInicio = parseString2Date(dataInicio);
		// System.out.println("Data Atual: " + dtAtual);

		// DataLimite (10 anos antes da atual)
		return isDiferencaDatasInferiorXAnos(dtInicio, dtAtual, anos);
	}

	/**
	 * Valida se a diferença entre as datas é menor que o intervalo em anos informado.
	 * @param dataInicio Data de início do intervalo
	 * @param dataFim Data finaL do intervalo
	 * @param anos Número de anos do intervalo (Positivo)
	 * @return
	 */
	public static boolean isDiferencaDatasInferiorXAnos(Date dataInicio, Date dataFim, int anos) {
		Calendar cLimite = Calendar.getInstance();
		cLimite.setTime(dataFim);
		cLimite.add(Calendar.YEAR, -anos);
		Date dtLimite = cLimite.getTime();

		// data inicial tem X anos ou mais da data atual
		if (dataInicio.before(dtLimite)) {
			return false;
		} else {// Data inicio tem menos de X anos da data atual
			return true;
		}
	}

	// private static void validarData(String data)
	// {
	// if (!(Pattern.matches("\\d\\d?/\\d\\d?/\\d\\d\\d\\d", data)))
	// throw new IllegalArgumentException("A data [" + data +
	// "] não representa um formato válido!");
	// }

	/**
	 * Retorna a data inicial mínima (formato dd/mm/yyyy) em relação à data
	 * atual de acordo com a quantidade de meses passada por parâmetro. Útil
	 * para limitar a data mínima no componente de calendário.
	 * 
	 * @param meses
	 * @return
	 */
	public static String getDataMinima(int meses) {
		if (meses <= 0)
			return "";

		Calendar calendario = new GregorianCalendar();
		calendario.add(GregorianCalendar.MONTH, -meses);
		return new SimpleDateFormat(FORMATO_DATA_PADRAO).format(calendario.getTime());

	}

	/**
	 * Valida se a data inicial é inferior a 2 anos da data final Se a data
	 * inicial for inferir a data final retorna 'true'
	 * 
	 * @param dataInicio
	 *            tipo String
	 * @param dataFim
	 *            tipo String
	 */
	public static boolean isDataInicialFinalMaior2Anos(String dataInicio, String dataFim) {
	
		Date dtInicio = parseString2Date(dataInicio);
		Date dtFim = parseString2Date(dataFim);
	
		// DataLimite (2 anos antes da final)
		Calendar cLimite = Calendar.getInstance();
		cLimite.setTime(dtInicio);
		cLimite.add(Calendar.YEAR, 2);
		Date dtLimite = cLimite.getTime();
		// data inicial maior de 2 anos da data final
		if (dtLimite.before(dtFim)) {
			return false;
		} else {// Data inicio tem 2 anos ou menos da data atual
			return true;
		}
	}

	public DateUtil() {
		super();
	}
	
	/**
	 * Valida se a data está no formato correto (dd/mm/aaaa) Se a data está no
	 * formato correto retorna 'true'
	 * 
	 * @param data
	 *            tipo String
	 * @param nomeCampo
	 *            tipo String
	 */
	public static boolean validarData(String data, String nomeCampo) {

		// Não escolheu opção válida
		if (data == null || data.equals("")) {
			MensagemUtil.exibirMsgErro(nomeCampo+" é obrigatório(a)");
			return false;
		}
		// Verifica se a data informada pelo usuário está no formato correto
		// (dd/MM/yyyy)
		if (!(Pattern.matches("\\d\\d?/\\d\\d?/\\d\\d\\d\\d", data))) {
			MensagemUtil.exibirMsgErro(nomeCampo+" inválido(a)");
			return false;
		}

		return true;
	}

}