package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import org.hibernate.Session;

//import com.excilys.formation.cdb.config.SessionFactoryProvider;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDao;

public class CompanyDaoHibernate implements ComputerDao{

	@Override
	public int nombre() {
		return 0;
	}

	@Override
	public boolean findcomputerById(int computerId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int faireRequeteSansResultat(String requete) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Computer> some(String pOrderBy) throws Exception {
		
//		Session session = SessionFactoryProvider.getSessionFactory().openSession();
//		HibernateQuery query = new HibernateQuery(session);
//		QComputer c = Qcomputer.
//		QComputer c = QComputer.
//		QComputer c = QComputer.
		return null;
	}

	@Override
	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> all() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat create(Computer computer) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat modify(Computer computerObject) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat delete(int computerId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Computer find(int computerId) throws Exception {
		// TODO Auto-generated method stub
		return null;
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
