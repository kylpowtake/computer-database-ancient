package com.excilys.formation.cdb.persistence.jdbc;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.datasource.ConnectionSQL;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDao;

public class CompanyDaoJdbc implements CompanyDao{
	private static Logger logger = Logging.getLogger();
	
	ConnectionSQL connectionSQL;

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	@Autowired
	public CompanyDaoJdbc(ConnectionSQL connectionSQL) {
		this.connectionSQL = connectionSQL;
	}


	public ConnectionSQL getConnection() {
		return connectionSQL;
	}
	
	public void setConnection(ConnectionSQL connectionSQL) {
		this.connectionSQL = connectionSQL;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Company> all(String pOrderBy) {
		logger.debug("Start of all of CompanyDaoJdbc");
		CompanyRowMapper companyRowMapper = new CompanyRowMapper();
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("orderBy", pOrderBy);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(connectionSQL.getDataSource());
		return namedParameterJdbcTemplate.query(REQUETELISTECOMPANIES, namedParameters, (RowMapper<Company>) companyRowMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Company> allSearch(String rechercheNom, String pOrderBy) {
		logger.debug("Start of allSearch of CompanyDaoJdbc");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("orderBy", pOrderBy).addValue("name", rechercheNom);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(connectionSQL.getDataSource());
		return (List<Company>) namedParameterJdbcTemplate.queryForObject(REQUETELISTECOMPANIESSEARCH, namedParameters, (RowMapper<Company>) new CompanyRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Company find(int companyId) throws Exception {
		logger.debug("Start of find of CompanyDaoJdbc");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", companyId);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(connectionSQL.getDataSource());
		return (Company) namedParameterJdbcTemplate.queryForObject(REQUETEFINDBYID, namedParameters, (RowMapper<Company>) new CompanyRowMapper());
	}

	@Transactional
	@Override
	public Resultat delete(int id) {
		logger.debug("Start of delete of CompanyDaoJdbc");
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", id);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		namedParameterJdbcTemplate.update(REQUETEDELETECOMPUTERS, namedParameters);
		int numberRow =  namedParameterJdbcTemplate.update(REQUETEDELETECOMPANY, namedParameters);
		if(numberRow != 1) {
			return Resultat.ECHOUE;
		} else {
			return Resultat.REUSSI;
		}
	}

}
