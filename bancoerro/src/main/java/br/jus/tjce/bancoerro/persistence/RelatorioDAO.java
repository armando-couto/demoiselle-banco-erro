package br.jus.tjce.bancoerro.persistence;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import br.gov.frameworkdemoiselle.stereotype.PersistenceController;
import br.gov.frameworkdemoiselle.template.JPACrud;
import br.gov.frameworkdemoiselle.transaction.Transactional;
import br.jus.tjce.bancoerro.domain.Relatorio;

@PersistenceController
public class RelatorioDAO extends JPACrud<Relatorio, String>{

	private static final long serialVersionUID = 1L;

	/**
	 * logger de ações realizadas.
	 */
	private static final Logger LOGGER = Logger.getLogger(RelatorioDAO.class);
		
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object> buscar(String idCodigoSistema,String idCodigoErro,Date dataIni,Date dataFim) throws Exception {

		try {
			Query query = null;
			
			if(getEntityManager() != null){
				
				query = getEntityManager().createNativeQuery(
						"SELECT " +
							"ea.codigo as erroAlertaLog, " +
							"ea.descricao as descricaoErro, " +
							"count(*) as quantidadePesquisaPorErro " +
						"FROM Log l "+
                            "inner join Sistema s on (l.codSistema = s.codigo) "+
                            "inner join ErroAlerta ea on (l.codErroAlerta = ea.codigo and s.id = ea.codigo_sistema) " +
                        "WHERE " +
                        	"l.codSistema = ? " +
                        "AND " +
                        	"((l.codErroAlerta = ? and 1 = ?) OR (0 = ?)) " +
                        "AND " +
                        	"l.data BETWEEN ? AND ? " +
                        "GROUP BY " +
                        	"l.codErroAlerta, l.codSistema " +
                        "ORDER BY " +
               				"quantidadePesquisaPorErro DESC"
					);

				query.setParameter(1, idCodigoSistema);
				
				if(idCodigoErro == null || idCodigoErro.equals("")){
					query.setParameter(2, 0);
					query.setParameter(3, 0);
					query.setParameter(4, 0);
				}else{
					query.setParameter(2, idCodigoErro);
					query.setParameter(3, 1);
					query.setParameter(4, 1);
				}
				
				query.setParameter(5, dataIni);
				query.setParameter(6, dataFim);
				
			}
			
			return query.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("ERRO GERAL LOGGER BUSCAR DAO",e);
			throw new Exception("ERRO GERAL DAO",e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Object> buscarLogOperacao(String descricaoUsuario,String descricaoOperacao,Date dataIni,Date dataFim) throws Exception{
		
		try {
			Query query = null;
			if(getEntityManager() != null){
				
				query = createQuery(
						"SELECT " +
							"l.usuario, "+
							"l.acao, "+
							"l.entidade, "+
							"l.loginUsuario, "+
							"l.codSistema, "+
							"l.codErroAlerta, "+
							"l.data "+
						"FROM Log l "+
						"WHERE " +
							"((l.usuario LIKE :param0 AND 1 = :param1) OR (0 = :param1)) "+
						"AND " +
							"l.usuario IS NOT NULL "+
						"AND " +
							"((l.acao LIKE :param2 AND 1 = :param3) OR (0 = :param3)) "+
						"AND " +
							"l.data BETWEEN :param4 AND :param5 "+
						"ORDER BY " +
							"l.data DESC, l.usuario DESC"
					);
				
				if(descricaoUsuario == null || descricaoUsuario.equals("")){
					query.setParameter("param0","%"+0+"%");
					query.setParameter("param1",0);
					query.setParameter("param1",0);
				}else{
					query.setParameter("param0", "%"+descricaoUsuario+"%");
					query.setParameter("param1",1);
					query.setParameter("param1",1);
				}
				
				if(descricaoOperacao == null || descricaoOperacao.equals("")){
					query.setParameter("param2","%"+0+"%");
					query.setParameter("param3",0);
					query.setParameter("param3",0);
				}else{
					query.setParameter("param2","%"+descricaoOperacao+"%");
					query.setParameter("param3",1);
					query.setParameter("param3",1);
				}
				
				query.setParameter("param4", dataIni);
				query.setParameter("param5", dataFim);
		
			}	
			
			return query.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("ERRO GERAL RELATÓRIO BUSCAR LOG OPERACAO DAO",e);
			throw new Exception("ERRO GERAL DAO",e);
		}
	}
}