package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.persistence.datasource.ConnectionSQL;

import com.excilys.formation.cdb.core.enumeration.ComputerOrderBy;
import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.core.model.QCompany;
import com.excilys.formation.cdb.persistence.CompanyDao;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class CompanyDaoHibernate implements CompanyDao{
	private static Logger logger = Logging.getLogger();

	@PersistenceContext
	EntityManager entityManager;

	ConnectionSQL connectionSQL;
	
	@Override
	public List<Company> all(String pOrderBy) {
		logger.debug("Start of all.");
		QCompany qCompany = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		return query.from(qCompany)
				.orderBy(qCompany.id.asc())
				.fetch();
	}

	@Override
	public List<Company> allSearch(String motRecherche, String pOrderBy) {
		logger.debug("Start of allSearch.");
		QCompany company = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		return query.from(company)
				.where(company.name.contains(motRecherche))
				.orderBy(ComputerOrderBy.getOrder(pOrderBy))
				.fetch();
	}

	@Override
	public Company find(int companyId) throws Exception {
		logger.debug("Start of find.");
		QCompany company = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
		List<Company> companyList = query.from(company).where(company.id.eq(Long.valueOf(companyId))).fetch();
		if(companyList.size() != 1) {
			return null;
		} else {
			return companyList.get(0);
		}
	}
	
	@Override
	@Transactional
	public Resultat create(Company company) {
		logger.debug("Start of create.");
		try {
			entityManager.persist(company);
			return Resultat.REUSSI;
		} catch (IllegalArgumentException iae) {
			return Resultat.ECHOUE;
		} catch (TransactionRequiredException tre) {
			return Resultat.ECHOUE;
		}
	}

	@Override
	@Transactional
	public Resultat update(Company company) {
		logger.debug("Start of modify.");
		try {
			entityManager.merge(company);
			return Resultat.REUSSI;
		} catch (IllegalArgumentException iae) {
			return Resultat.ECHOUE;
		} catch (TransactionRequiredException tre) {
			return Resultat.ECHOUE;
		}
	}

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
