package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.core.Pageable.Page;
//import com.excilys.formation.cdb.config.EntityManagerProvider;
//import com.excilys.formation.cdb.config.HibernateUtil;
import com.excilys.formation.cdb.core.enumeration.ComputerOrderBy;
//import com.excilys.formation.cdb.config.SessionFactoryProvider;
import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.core.model.QCompany;
import com.excilys.formation.cdb.core.model.QComputer;
import com.excilys.formation.cdb.core.model.QueryParams;
import com.excilys.formation.cdb.persistence.ComputerDao;
import com.querydsl.core.support.QueryBase;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ComputerDaoHibernate implements ComputerDao {
	private static Logger logger = Logging.getLogger();

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public int nombre() {
		logger.debug("Start of nombre.");
		QComputer qComputer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		int nombre = (int) query.from(qComputer).fetchCount();
		logger.debug("End of nombre.");
		return nombre;
	}

	@Override
	public boolean findcomputerById(Long computerId) throws Exception {
		logger.debug("Start of findcomputerById.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return 1 == query.from(computer).where(computer.id.eq((long) computerId)).fetchCount();
	}

	@Override
	public int faireRequeteSansResultat(String requete) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Computer> some(String pOrderBy) {
		logger.debug("Start of some.");
		logger.debug("Start of some.");
		logger.debug("Start of some.");
		logger.debug("Start of some.");
		logger.debug("Start of some.");

		Page page = Page.getPage();
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		QComputer qComputer = QComputer.computer;
		QCompany qCompany = QCompany.company;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		List<Computer> computerList = null;
		computerList = query.from(qComputer).leftJoin(qCompany).on(qComputer.company.id.eq(qCompany.id))
				.orderBy(ComputerOrderBy.getOrder(pOrderBy + " " + Page.getPage().getOrder().getValue()))
				.limit(page.getNombreParPage()).offset(indice).fetch();
		logger.debug("End of some.");
		return computerList;
	}

	@Override
	public List<Computer> someSearch(String motRecherche, String pOrderBy){
		logger.debug("Start of someSearch.");
		Page page = Page.getPage();
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return query.from(computer)
				.where(computer.name.contains(motRecherche).or(computer.company.name.contains(motRecherche)))
				.orderBy(ComputerOrderBy.getOrder(pOrderBy + " " + Page.getPage().getOrder().getValue())).offset(indice)
				.limit(page.getNombreParPage()).fetch();
	}
	
	@Override
	public List<Computer> someUltimateSearch(QueryParams queryParams){
		logger.debug("Start of someUltimateSearch.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return query.from(computer)
				.where(computer.name.contains(queryParams.getSearch()).or(computer.company.name.contains(queryParams.getSearch())))
				.orderBy(ComputerOrderBy.getOrder(queryParams.getOrderBy()))
				.offset(Integer.parseInt(queryParams.getOffSet()))
				.limit(Integer.parseInt(queryParams.getLimit()))
				.fetch();
	}

	@Override
	public List<Computer> all() throws Exception {
		logger.debug("Start of all.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		return query.from(computer).fetch();
	}

	@Override
	public Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Resultat create(Computer computer) throws Exception {
		logger.debug("Start of create.");
		try {
			entityManager.persist(computer);
			return Resultat.REUSSI;
		} catch (IllegalArgumentException iae) {
			return Resultat.ECHOUE;
		} catch (TransactionRequiredException tre) {
			return Resultat.ECHOUE;
		}
	}

	@Override
	@Transactional
	public Resultat modify(Computer computerObject) throws Exception {
		logger.debug("Start of modify.");
		try {
			entityManager.merge(computerObject);
			return Resultat.REUSSI;
		} catch (IllegalArgumentException iae) {
			return Resultat.ECHOUE;
		} catch (TransactionRequiredException tre) {
			return Resultat.ECHOUE;
		}
	}

	@Override
	@Transactional
	public Resultat delete(int computerId) throws Exception {
		logger.debug("Start of delete.");
		try {
			entityManager.remove(find(computerId));
			return Resultat.REUSSI;
		} catch (IllegalArgumentException iae) {
			logger.debug("illegalArgument exception.");
			return Resultat.ECHOUE;
		} catch (TransactionRequiredException tre) {
			logger.debug("Transaction exception.");
			return Resultat.ECHOUE;
		}
	}

	@Override
	public Computer find(int computerId) throws Exception {
		logger.debug("Start of find.");
		QComputer qComputer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		Computer computer = query.from(qComputer).where(qComputer.id.eq(Long.valueOf(computerId))).fetchOne();
		return computer;
	}

	@Override
	public List<Computer> all(String pOrderBy) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> allSearch(String orderby, String search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
