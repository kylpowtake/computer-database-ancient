package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.connectiviteSQL.DataSource;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * Classe comprenant les méthodes permettant de faire des requêtes à la base de
 * données à propos des computers.
 * 
 * @author kylian
 * @see Computer
 *
 */
public class DAOComputer {
	/**
	 * Champ privé permettant d'avoir un singleton pour cette classe.
	 */
	private static DAOComputer daoComputer;

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	private DAOComputer() {
	}

	/**
	 * String représentant la requête pour avoir le nombre de computers dans la base
	 * de données.
	 */
	private static final String REQUETENOMBRECOMPUTERS = "SELECT COUNT(computer.id) FROM computer;";

	/**
	 * String représentant la requête pour avoir un certain nombre de computer à
	 * partir d'une position précise.
	 */
	private static final String REQUETENOMBRECOMPUTERSDEPUIS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name from computer LEFT JOIN company ON computer.company_id = company.id ";
	/**
	 * String représentant la requête pour avoir la liste complète de computers dans
	 * la base de données.
	 */
	private static final String REQUETELISTECOMPLETECOMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";

	/**
	 * Méthode représentant une requête pour trouver le computer ayant l'id donné.
	 */
	private static final String REQUETEFINDCOMPUTERBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ";

	/**
	 * String représentant la requête pour supprimer de la base de données le
	 * computer ayant l'id donné.
	 */
	private static final String REQUETEDELETECOMPUTERBYID = "DELETE FROM computer WHERE computer.id = ";

	private static Logger logger = Logging.getLogger();

	private static final String REQUETEUPDATE = "update computer set computer.name = ?, computer.introduced = ?,"
			+ " computer.discontinued = ?";

	/**
	 * Méthode permettant d'obtenir l'instance unique de la classe. Si l'instance
	 * existe déjà elle est renvoyée, sinon elle est créée puis renvoyée.
	 * 
	 * @return une instance de la classe.
	 */
	public static DAOComputer getDAOComputer() {
		if (daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}

	/**
	 * Méthode Demandant à la base de données le nombre de computers selon leurs id.
	 * 
	 * @return Le nombre de computers dans la base de données dans un ResultSet.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public int DemandeNombreComputers() {
		int nombreComputers = 0;
		try {
			Connection connection = DataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERS);
			if (resultSet.next()) {
				// La première colonne est le count de l'id des computers.
				nombreComputers = resultSet.getInt(1);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			logger.error("Problème lors de la requête du nombre de computers à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		return nombreComputers;
	}

	/**
	 * Méthode faisant une requête pour vérifier que la base de données contient le
	 * computer dont l'id est passé en paramètre et retournée ses valeurs.
	 * 
	 * @param companyId : L'id du computer concernant la requête.
	 * @return ResultSet contenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	private ResultSet findcomputerById(int computerId) {
		try {
			Connection connection = DataSource.getConnection();
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(REQUETEFINDCOMPUTERBYID + computerId + ";");
			statement.close();
			connection.close();
			return resultSet;
		} catch (SQLException e) {
			logger.error("Problème lors de la requête de l'id d'un computer à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}

	/**
	 * Méthode appliquant la requête (seulement SELECT) passé en paramètre à la base
	 * de données et retournant le résultat.
	 * 
	 * @param requete Requête pour la base de données, forcément SELECT.
	 * @return ResultSet comprenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	private ResultSet faireRequeteAvecResultat(String requete) {
		try {
			Connection connection = DataSource.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			statement.close();
			connection.close();
			return resultSet;
		} catch (SQLException e) {
			logger.error("Problème lors d'une requête concernant les computers : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}

	/**
	 * Méthode appliquant la requête (UPDATE, CREATE ou DELETE) passé en paramètre à
	 * la base de données et retournant le nombre de lignes concernées par la
	 * requête.
	 * 
	 * @param requete Requête pour la base de données, peut être UPDATE, CREATE ou
	 *                DELETE.
	 * @return nombreLignes : Le nombre de lignes ayant été concernées par la
	 *         requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	private int faireRequeteSansResultat(String requete) {
		try {
			Connection connection = DataSource.getConnection();
			Statement statement = connection.createStatement();
			int nombreLignes = statement.executeUpdate(requete);
			statement.close();
			connection.close();
			return nombreLignes;
		} catch (SQLException e) {
			logger.error("Problème lors d'une requête concernant les computers : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return 0;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir la dernière page
	 * de computers et les renvoyant en String.
	 * 
	 * @return un message d'erreur ou la liste des derniers computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 * @see Page
	 */
	public String listerComputersEnd() throws Exception {
		String message = "";
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setNumeroPage(((nombreComputers - (nombreComputers % 10)) / 10) + 1);
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(
					faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS + "LIMIT " + page.getNombreParPage()
							+ " OFFSET " + (page.getNumeroPage() - 1) * page.getNombreParPage() + ";"));
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerEnd");
		}
		message = listComputers.toString();
		return message;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir la page actuelle
	 * de computers et les renvoyant en String.
	 * 
	 * @return un message d'erreur ou une liste de computers.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 * @see Page
	 */
	public List<Computer> listerComputersPage(String pOrderBy) throws Exception {
//		String message = "";
		String orderBy = this.modificationOrderBy(pOrderBy);
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(this.faireRequeteAvecResultat(
					REQUETENOMBRECOMPUTERSDEPUIS + " ORDER BY " + orderBy + " LIMIT " + page.getNombreParPage()
							+ " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";"));
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerPage");
		}
//		message = listComputers.toString();
		return listComputers;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir la page actuelle
	 * de computers dont le nom contient celui passé en paramètre et les renvoyant
	 * en String.
	 * 
	 * @return un message d'erreur ou une liste de computers.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 * @see Page
	 */
	public List<Computer> listerComputersPageRecherche(String motRecherche, String pOrderBy)
			throws Exception {
//		String message = "";
		String orderBy = this.modificationOrderBy(pOrderBy);
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		try {
			listComputers = MapperComputer
					.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS
							+ "WHERE ((computer.name LIKE '%" + motRecherche + "%') OR (company.name LIKE '%"
							+ motRecherche + "%')) ORDER BY " + orderBy + " LIMIT " + page.getNombreParPage()
							+ " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";"));
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerPageRecherche");
		}
//		message = listComputers.toString();
		return listComputers;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir la liste entière
	 * de computers.
	 * 
	 * @return Un message d'erreur ou la liste de computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public String listerComputers() throws Exception {
		String message = "";
		List<Computer> listeComputers = null;
		try {
			listeComputers = MapperComputer
					.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETELISTECOMPLETECOMPUTERS));
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputers");
		}
		message = listeComputers.toString();
		return message;
	}

	/**
	 * Méthode s'occupant de la vérification du nombre de lignes affecté par une
	 * requête (UPDATE, CREATE, DELETE) dans la base de données par rapport au
	 * nombre attendue (1).
	 * 
	 * @param nombreLignes Nombre de lignes affectées par la requête.
	 * @return String représentant le rapport nombre de ligne affectées et le nombre
	 *         attendu (1).
	 */
	private Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception {
		if (nombreLignes == 1) {
			return Resultat.REUSSI;
		} else if (nombreLignes == 0) {
			throw new Exception("Un problème a occuré, l'opération de création de computer n'a pas été effectué.");
		} else {
			throw new Exception ("Un problème a occuré, l'opération de création de computer a été efectué trop de fois (" + nombreLignes + ").");
		}
	}

	/**
	 * Méthode faisant une requête pour ajouter le computer passé en argument à la
	 * base de données.
	 * 
	 * @param computer  Le computer à ajouter à la base de données.
	 * @param idCompany L'id de la company a ajouté au computer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public Resultat ajouter(Computer computer, int companyId) throws Exception {
		Resultat resultat = Resultat.ECHOUE;
		String requete = "";
		Company company;
		try {
			company = DAOCompany.getDAOCompany().findCompanybyId(companyId);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " ajouter");
		}
		int nombreLignes = 0;
		requete += "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + computer.getName()
				+ "', ";
		if (computer.getIntroduced() != null) {
			requete += "'" + computer.getIntroduced() + "', ";
		} else {
			requete += "NULL, ";
		}
		if (computer.getDiscontinued() != null) {
			requete += "'" + computer.getDiscontinued() + "', ";
		} else {
			requete += "NULL, ";
		}
		if (companyId != 0) {
			requete += "'" + company.getId() + "');";
		} else {
			requete += "NULL" + ");";
		}
		System.out.println(requete);
		nombreLignes = this.faireRequeteSansResultat(requete);
		resultat = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return resultat;
	}

	/**
	 * Méthode faisant une requête à la base de données pour modifier les valeurs
	 * d'un computer.
	 * 
	 * @param computer  les nouvelles données du computer ayant le même id.
	 * @param idCompany L'id de la campany du computer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public Resultat modifier(Computer computer, int companyId) throws Exception {
		Resultat resultat = Resultat.ECHOUE;
		int nombreLignes = 0;
		String requeteUpdate = "";
		try {
			ResultSet resultSet = this.findcomputerById(computer.getId());
			if (!resultSet.next()) {
				throw new Exception("L'id du computer a modifié n'est pas celui d'un computer existant : modifier");
			}
		} catch (SQLException e) {
			throw new Exception(e.getLocalizedMessage() + " : modifier");
		}
		if (companyId != 0) {
			Company company;
			try {
				company = DAOCompany.getDAOCompany().findCompanybyId(companyId);
			} catch (Exception e2) {
				throw new Exception(e2.getLocalizedMessage() + " : modifier");
			}
			requeteUpdate = REQUETEUPDATE + ", computer.company_id = ? where computer.id = ?;";
			try {
				Connection connection = DataSource.getConnection();
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection.prepareStatement(requeteUpdate);
				preparedStatement.setString(1, computer.getName());
				preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
				preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
				preparedStatement.setInt(4, company.getId());
				preparedStatement.setInt(5, computer.getId());
				preparedStatement.executeUpdate();
				DataSource.getConnection().commit();
				connection.close();
				preparedStatement.close();
			} catch (SQLException e) {
				try {
					DataSource.getConnection().rollback();
				} catch (SQLException e1) {
					System.out.println(e1.getLocalizedMessage());
				}
			} finally {
				try {
					DataSource.getConnection().setAutoCommit(true);
				} catch (SQLException e) {
					System.out.println(e.getLocalizedMessage());
				}
			}
		} else {
			requeteUpdate = REQUETEUPDATE + " where computer.id = ?;";
			try {
				Connection connection = DataSource.getConnection();
				connection.setAutoCommit(false);
				PreparedStatement preparedStatement = connection.prepareStatement(requeteUpdate);
				preparedStatement.setString(1, computer.getName());
				preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
				preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
				preparedStatement.setInt(4, computer.getId());
				System.out.println(preparedStatement.toString());
				preparedStatement.executeUpdate();
				DataSource.getConnection().commit();
				preparedStatement.close();
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
				try {
					DataSource.getConnection().rollback();
				} catch (SQLException e1) {
					System.out.println(e1.getLocalizedMessage());
				}
			}
		}

		resultat = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return resultat;
	}

	/**
	 * Méthode faisant une requête pour supprimer un des computer.
	 * 
	 * @param idComputer : id du computer à supprimer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public Resultat supprimer(int computerId) throws Exception {
		Resultat resultat = Resultat.ECHOUE;
		int nombreLignes = this.faireRequeteSansResultat(REQUETEDELETECOMPUTERBYID + computerId + ";");
		try {
			resultat = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " supprimer");
		}
		return resultat;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir les informations
	 * du computer dont l'id est passé en paramètre.
	 * 
	 * @param idComputer : id du computer à obtenir.
	 * @return message d'erreur ou les informations du computer en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public Computer computerDetails(int computerId) throws Exception {
		List<Computer> listeComputers = null;
		try {
			listeComputers = MapperComputer.mapResultSetToListComputer(
					this.faireRequeteAvecResultat(REQUETEFINDCOMPUTERBYID + computerId + ";"));
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " computerDetails");
		}
		if (listeComputers.get(0) != null) {
			return listeComputers.get(0);
		}
		return null;
	}

	public String modificationOrderBy(String orderBy) {
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
