package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.model.Computer;

public interface ComputerDao {

	static final String REQUETENOMBRECOMPUTERS = "SELECT COUNT(computer.id) FROM computer;";

	static final String REQUETEPAGECOMPUTERSSTART = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "from computer LEFT JOIN company ON computer.company_id = company.id ORDER BY ";

	static final String REQUETEPAGECOMPUTERSEND = " LIMIT :computerParPage OFFSET :positionComputers;";

	static final String REQUETEPAGECOMPUTERSSEARCH = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "from computer LEFT JOIN company ON computer.company_id = company.id WHERE ((computer.name LIKE '%:motRecherche%') OR (company.name LIKE '%:motRecherche%')) ORDER BY ";

	static final String REQUETECOMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name \"\n"
			+ "from computer LEFT JOIN company ON computer.company_id = company.id";

	static final String REQUETELISTECOMPLETECOMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id ORDER BY :orderBy;";

	static final String REQUETEFINDCOMPUTERBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name "
			+ "FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id";

	static final String REQUETEDELETE = "DELETE FROM computer WHERE computer.id = :id";

	static final String REQUETEUPDATE = "update computer set computer.name = :name, computer.introduced = :introduced, computer.discontinued = :discontinued, computer.companyId = :companyId where computer.name = :id;";

	static final String REQUETECREATE = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :companyId);";

	
	public int nombre();

	public boolean findcomputerById(int computerId) throws Exception;

	public int faireRequeteSansResultat(String requete);

	public List<Computer> some(String pOrderBy) throws Exception;

	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception;

	public List<Computer> all() throws Exception;

	public Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception;

	public Resultat create(Computer computer) throws Exception;

	public Resultat modify(Computer computerObject) throws Exception;

	public Resultat delete(int computerId) throws Exception;

	public Computer find(int computerId) throws Exception;

	public List<Computer> all(String pOrderBy) throws Exception;

	public List<Computer> allSearch(String orderby, String search) throws Exception;

	public static String modificationOrderBy(String orderBy) {
		String message = "";
		String[] orderBies = orderBy.split(" ");
		if (orderBies.length == 2) {
			message = " " + orderBies[1];
		}
		switch (orderBies[0]) {
		case "id":
			return "computer.id" + message;
		case "name":
			return "computer.name" + message;
		case "introduced":
			return "computer.introduced" + message;
		case "discontinued":
			return "computer.discontinued" + message;
		case "company":
			return "company.name" + message;
		default:
			return "computer.id" + message;
		}
	}
}
