package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.config.EntityManagerProvider;
import com.excilys.formation.cdb.config.HibernateUtil;
import com.excilys.formation.cdb.enumeration.ComputerOrderBy;
//import com.excilys.formation.cdb.config.SessionFactoryProvider;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QComputer;
//import com.excilys.formation.cdb.model.QComputer;
import com.excilys.formation.cdb.persistence.ComputerDao;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class ComputerDaoHibernate implements ComputerDao {
	private static Logger logger = Logging.getLogger();

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public int nombre() {
		logger.debug("Start of nombre.");
		QComputer computer = QComputer.computer;
		if(entityManager != null) {
					System.out.println("Plop : " + entityManager.toString());
		} else {
			System.out.println("OPlop null");
		}
		JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
		logger.debug("End of nombre.");
		return (int) query.fetchCount();
	}

	@Override
	public boolean findcomputerById(int computerId) throws Exception {
		logger.debug("Start of findcomputerById.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(EntityManagerProvider.getEntityManager());
		return 1 == query.from(computer).where(computer.id.eq(computerId)).fetchCount();
	}

	@Override
	public int faireRequeteSansResultat(String requete) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Computer> some(String pOrderBy) throws Exception {
		logger.debug("Start of some.");
		Page page = Page.getPage();
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(EntityManagerProvider.getEntityManager());
		return query.from(computer)
				.orderBy(ComputerOrderBy.getOrder(pOrderBy + " " + Page.getPage().getOrder().getValue())).offset(indice)
				.limit(page.getNombreParPage()).fetch();
	}

	@Override
	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception {
		logger.debug("Start of someSearch.");
		Page page = Page.getPage();
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(EntityManagerProvider.getEntityManager());
		return query.from(computer)
				.where(computer.name.contains(motRecherche).or(computer.company.name.contains(motRecherche)))
				.orderBy(ComputerOrderBy.getOrder(pOrderBy + " " + Page.getPage().getOrder().getValue())).offset(indice)
				.limit(page.getNombreParPage()).fetch();
	}

	@Override
	public List<Computer> all() throws Exception {
		logger.debug("Start of all.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(EntityManagerProvider.getEntityManager());
		return query.from(computer).fetch();
	}

	@Override
	public Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat create(Computer computer) throws Exception {
		logger.debug("Start of create.");
		Session session = HibernateUtil.getSession();
		Transaction transaction = null;
		try {
		transaction = session.beginTransaction();
		session.save(computer);
		transaction.commit();
		return Resultat.REUSSI;
		} catch(Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public Resultat modify(Computer computerObject) throws Exception {
		logger.debug("Start of modify.");
		Session session = HibernateUtil.getSession();
		QComputer computer = QComputer.computer;
		if(new HibernateUpdateClause(session, computer).where(computer.id.eq(computerObject.getId())).set(computer,
				computerObject).execute() == 1) {
			return Resultat.REUSSI;
		} else {
			return Resultat.ECHOUE;
		}
	}

	@Override
	public Resultat delete(int computerId) throws Exception {
		logger.debug("Start of delete.");
		Session session = HibernateUtil.getSession();
		QComputer computer = QComputer.computer;
		if(new HibernateDeleteClause(session, computer).where(computer.id.eq(computerId)).execute() == 1) {
			return Resultat.REUSSI;
		} else {
			return Resultat.ECHOUE;
		}
	}

	@Override
	public Computer find(int computerId) throws Exception {
		logger.debug("Start of find.");
		QComputer computer = QComputer.computer;
		JPAQuery<Computer> query = new JPAQuery<Computer>(EntityManagerProvider.getEntityManager());
		List<Computer> computerList = query.from(computer).where(computer.id.eq(computerId)).fetch();
		if(computerList.size() != 1) {
			return null;
		} else {
			return computerList.get(0);
		}
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
