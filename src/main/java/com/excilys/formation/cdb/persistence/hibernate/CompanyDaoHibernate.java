package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.config.EntityManagerProvider;
import com.excilys.formation.cdb.config.HibernateUtil;
import com.excilys.formation.cdb.enumeration.ComputerOrderBy;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.model.QComputer;
import com.excilys.formation.cdb.persistence.CompanyDao;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class CompanyDaoHibernate implements CompanyDao{
	private static Logger logger = Logging.getLogger();

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Company> all(String pOrderBy) {
		logger.debug("Start of all.");
		QCompany company = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(EntityManagerProvider.getEntityManager());
		return query.from(company)
				.orderBy(ComputerOrderBy.getOrder(pOrderBy))
				.fetch();
	}

	@Override
	public List<Company> allSearch(String motRecherche, String pOrderBy) {
		logger.debug("Start of allSearch.");
		QCompany company = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(EntityManagerProvider.getEntityManager());
		return query.from(company)
				.where(company.name.contains(motRecherche))
				.orderBy(ComputerOrderBy.getOrder(pOrderBy))
				.fetch();
	}

	@Override
	public Company find(int companyId) throws Exception {
		logger.debug("Start of find.");
		QCompany company = QCompany.company;
		JPAQuery<Company> query = new JPAQuery<Company>(EntityManagerProvider.getEntityManager());
		List<Company> companyList = query.from(company).where(company.id.eq(companyId)).fetch();
		if(companyList.size() != 1) {
			return null;
		} else {
			return companyList.get(0);
		}
	}

	@Override
	public Resultat delete(int id) {
		logger.debug("Start of delete.");
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		QComputer computer = QComputer.computer;
		QCompany company = QCompany.company;
		new HibernateDeleteClause(session, computer).where(computer.company.id.eq(id)).execute();
		if(new HibernateDeleteClause(session, company).where(company.id.eq(id)).execute() == 1) {
			return Resultat.REUSSI;
		} else {
			return Resultat.ECHOUE;
		}
	}
}
