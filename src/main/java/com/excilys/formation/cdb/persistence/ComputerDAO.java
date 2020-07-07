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
import com.excilys.formation.cdb.model.Computer;

/**
 * Classe comprenant les méthodes permettant de faire des requêtes à la base de
 * données à propos des computers.
 * 
 * @author kylian
 * @see Computer
 *
 */
public class ComputerDAO implements ObjectDAO<Computer>{
	/**
	 * Champ privé permettant d'avoir un singleton pour cette classe.
	 */
	private static ComputerDAO daoComputer;

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	private ComputerDAO() {
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

	private static final String REQUETEUPDATE = "update computer set computer.name = ?";

	/**
	 * Méthode permettant d'obtenir l'instance unique de la classe. Si l'instance
	 * existe déjà elle est renvoyée, sinon elle est créée puis renvoyée.
	 * 
	 * @return une instance de la classe.
	 */
	public static ComputerDAO getDAOComputer() {
		if (daoComputer == null) {
			daoComputer = new ComputerDAO();
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
			resultSet.close();
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
	private boolean findcomputerById(int computerId) throws Exception {
		boolean resultat = false;
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETEFINDCOMPUTERBYID + computerId + ";");
		try {
			if (!resultSet.next()) {
				resultat = false;
			} else {
				resultat = true;
			}
		} catch (SQLException e) {
			throw new Exception(e.getLocalizedMessage() + " : findComputerById");
		} finally {
			resultSet.close();
			statement.close();
			connection.close();
		}
		return resultat;
//	} catch( SQLException e)
//	{
//			logger.error("Problème lors de la requête de l'id d'un computer à la base de données : "
//					+ e.getLocalizedMessage());
//			System.exit(1);
//		}
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
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERSDEPUIS + "LIMIT " + page.getNombreParPage()
				+ " OFFSET " + (page.getNumeroPage() - 1) * page.getNombreParPage() + ";");
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(resultSet);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerEnd");
		} finally {
			statement.close();
			connection.close();
			resultSet.close();
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
	public List<Computer> some(String pOrderBy) throws Exception {
//		String message = "";
		String orderBy = this.modificationOrderBy(pOrderBy);
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERSDEPUIS + " ORDER BY " + orderBy + " LIMIT "
				+ page.getNombreParPage() + " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";");
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(resultSet);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerPage");
		} finally {
			statement.close();
			connection.close();
			resultSet.close();
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
	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception {
//		String message = "";
		String orderBy = this.modificationOrderBy(pOrderBy);
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = null;
		// Requête pour obtenir les computers de la page actuelle.
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERSDEPUIS + "WHERE ((computer.name LIKE '%"
				+ motRecherche + "%') OR (company.name LIKE '%" + motRecherche + "%')) ORDER BY " + orderBy + " LIMIT "
				+ page.getNombreParPage() + " OFFSET " + (page.getNumeroPage()) * page.getNombreParPage() + ";");
		try {
			listComputers = MapperComputer.mapResultSetToListComputer(resultSet);
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputerPageRecherche");
		} finally {
			resultSet.close();
			statement.close();
			connection.close();
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
	public String all() throws Exception {
		String message = "";
		List<Computer> listeComputers = null;
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPLETECOMPUTERS);
		try {
			listeComputers = MapperComputer.mapResultSetToListComputer(resultSet);
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " listerComputers");
		} finally {
			resultSet.close();
			statement.close();
			connection.close();
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
			throw new Exception("Un problème a occuré, l'opération de création de computer a été efectué trop de fois ("
					+ nombreLignes + ").");
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
	public Resultat create(Computer computer) throws Exception {		
		logger.debug("Début create de ComputerDAO.");
		Resultat resultat = Resultat.ECHOUE;
		String requete = "";
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
		if (computer.getCompany() != null && computer.getCompany().getId() != 0) {
			requete += "'" + computer.getCompany().getId() + "');";
		} else {
			requete += "NULL" + ");";
		}
		nombreLignes = this.faireRequeteSansResultat(requete);
		resultat = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		logger.debug("Fin create de ComputerDAO.");
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
	public Resultat modify(Computer computerObject) throws Exception {
		logger.debug("Début du modify de ComputerDAO.");
		Computer computer = (Computer) computerObject;
		Resultat resultat = Resultat.ECHOUE;
		int nombreLignes = 0;
		String requeteUpdate = REQUETEUPDATE;
		if (!this.findcomputerById(computer.getId())) {
			throw new Exception("L'id du computer a modifié n'est pas celui d'un computer existant : modifier");
		}
		Connection connection = DataSource.getConnection();
		connection.setAutoCommit(false);
		if (computer.getIntroduced() != null) {
			requeteUpdate += ", computer.introduced = ?";
		}
		if (computer.getDiscontinued() != null) {
			requeteUpdate += ", computer.discontinued = ?";
		}
		if (computer.getCompany() != null && computer.getCompany().getId() != 0) {
			requeteUpdate = requeteUpdate + ", computer.company_id = ? where computer.id = ?;";
		} else {
			requeteUpdate = requeteUpdate + " where computer.id = ?;";
		}
		PreparedStatement preparedStatement = connection.prepareStatement(requeteUpdate);
		try {
			int indice = 1;
			preparedStatement.setString(indice++, computer.getName());
			if (computer.getIntroduced() != null) {
				preparedStatement.setDate(indice++, Date.valueOf(computer.getIntroduced()));
			}
			if (computer.getDiscontinued() != null) {
				preparedStatement.setDate(indice++, Date.valueOf(computer.getDiscontinued()));
			}
			if (computer.getCompany() != null && computer.getCompany().getId() != 0) {
				preparedStatement.setInt(indice++, computer.getCompany().getId());
			}
			preparedStatement.setInt(indice, computer.getId());
			logger.debug("Avant update de modify de ComputerDAO.");
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new Exception(e1.getLocalizedMessage() + " 1modifier");
			}
			throw new Exception(e.getLocalizedMessage() + " 2modifier");
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new Exception(e.getLocalizedMessage() + " 3modifier");
			}
			connection.close();
			preparedStatement.close();
		}

		resultat = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		logger.debug("Fin du modify de ComputerDAO.");
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
	public Resultat delete(int computerId) throws Exception {
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
	public Computer find(int computerId) throws Exception {
		List<Computer> listeComputers = null;
		Connection connection = DataSource.getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(REQUETEFINDCOMPUTERBYID + computerId + ";");
		try {
			listeComputers = MapperComputer.mapResultSetToListComputer(resultSet);
		} catch (ParametresException e) {
			throw new Exception(e.getLocalizedMessage() + " computerDetails");
		} finally {
			resultSet.close();
			statement.close();
			connection.close();
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

	@Override
	public List<Computer> all(String orderBy) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> allSearch(String orderby, String search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
