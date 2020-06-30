package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperComputer;
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
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERS);
			if (resultSet.next()) {
				// La première colonne est le count de l'id des computers.
				nombreComputers = resultSet.getInt(1);
			}
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
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETEFINDCOMPUTERBYID + computerId + ";");
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
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
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
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			int nombreLignes = statement.executeUpdate(requete);
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
	public String listerComputersEnd() throws ParametresException {
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
			throw e;
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
	public List<Computer> listerComputersPage() throws ParametresException {
//		String message = "";
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(
					this.faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS + "LIMIT " + page.getNombreParPage()
							+ " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";"));
		} catch (ParametresException e) {
			throw e;
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
	public List<Computer> listerComputersPageRecherche(String motRecherche) throws ParametresException {
//		String message = "";
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		try {
			listComputers = MapperComputer
					.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS
							+ "WHERE computer.name LIKE '%" + motRecherche + "%' LIMIT " + page.getNombreParPage()
							+ " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";"));
		} catch (ParametresException e) {
			throw e;
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
	public String listerComputers() throws ParametresException {
		String message = "";
		List<Computer> listeComputers = null;
		try {
			listeComputers = MapperComputer
					.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETELISTECOMPLETECOMPUTERS));
		} catch (ParametresException e) {
			throw e;
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
	private String verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) {
		String message = "";
		if (nombreLignes == 1) {
			message = "L'opération a été effectué";
		} else if (nombreLignes == 0) {
			message = "Un problème a occuré, l'opération n'a pas été effectué.";
		} else {
			message = "Un problème a occuré, l'opération a été efectué trop de fois (" + nombreLignes + ").";
		}
		return message;
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
	public String ajouter(Computer computer, int companyId) {
		String message = "";
		String requete = "";
		ResultSet resultSet = DAOCompany.getDAOCompany().findCompanybyId(companyId);
		System.out.println(computer.getIntroduced());
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
			try {
				if (resultSet == null || !resultSet.next()) {
					message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
					return message;
				}
			} catch (SQLException e) {
				logger.error(
						"Problème lors de la requête de l'id d'une company lors de l'ajout d'un computer à la base de données : "
								+ e.getLocalizedMessage());
				System.exit(1);
			}
			requete += "'" + companyId + "');";
		} else {
			requete += "NULL" + ");";
		}
		System.out.println(requete);
		nombreLignes = this.faireRequeteSansResultat(requete);
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
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
	public String modifier(Computer computer, int companyId) {
		String message = "";
		try {
			ResultSet resultSet = this.findcomputerById(computer.getId());
			if (!resultSet.next()) {
				message = "L'indice du computer à modifier n'est pas celui d'un computer existant.";
				return message;
			}
		} catch (SQLException e) {
			logger.error("Problème lors de la requête de l'id d'une company à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		ResultSet resultSet = DAOCompany.getDAOCompany().findCompanybyId(companyId);
		try {
			if (resultSet == null || !resultSet.next()) {
				message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
				return message;
			}
		} catch (SQLException e) {
			logger.error("Problème lors de la requête pour modifier un computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		int nombreLignes = this.faireRequeteSansResultat(
				"update computer set computer.name = '" + computer.getName() + "', computer.introduced = '"
						+ computer.getIntroduced() + "', computer.discontinued = '" + computer.getDiscontinued()
						+ "', computer.company_id = " + companyId + " where computer.id = " + computer.getId() + ";");
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
	}

	/**
	 * Méthode faisant une requête pour supprimer un des computer.
	 * 
	 * @param idComputer : id du computer à supprimer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public String supprimer(int computerId) {
		String message = "";
		int nombreLignes = this.faireRequeteSansResultat(REQUETEDELETECOMPUTERBYID + computerId + ";");
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
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
	public Computer computerDetails(int computerId) throws ParametresException {
		String message = "";
		List<Computer> listeComputers = null;
		try {
			listeComputers = MapperComputer.mapResultSetToListComputer(
					this.faireRequeteAvecResultat(REQUETEFINDCOMPUTERBYID + computerId + ";"));
		} catch (ParametresException e) {
			throw e;
		}
		 if (listeComputers.get(0) != null) {
			return listeComputers.get(0);
		}
		return null;
	}
}
