package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.model.Computer;

/**
 * Classe comprenant les méthodes permettant de faire des requêtes à la base de données à propos des computers.
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
	private DAOComputer() {}
	
	/**
	 * String représentant la requête pour avoir le nombre de computers dans la base de données.
	 */
	private static final String REQUETENOMBRECOMPUTERS = "SELECT COUNT(computer.id) FROM computer;";
	
	/**
	 * String représentant la requête pour avoir un ceratin nombre de computer à partir d'une position précise.
	 */
	private static final String REQUETENOMBRECOMPUTERSDEPUIS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name from computer LEFT JOIN company ON computer.company_id = company.id LIMIT 10 OFFSET ";
	/**
	 * String représentant la requête pour avoir la liste complète de computers dans la base de données.
	 */
	private static final String REQUETELISTECOMPLETECOMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id;";
	
	/**
	 * Méthode représentant une requête pour trouver le computer ayant l'id donné.
	 */
	private static final String REQUETEFINDCOMPUTERBYID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ";
	
	/**
	 * String représentant la requête pour supprimer de la base de données le computer ayant l'id donné.
	 */
	private static final String REQUETEDELETECOMPUTERBYID = "DELETE FROM computer WHERE computer.id = ";
	
	/**
	 * Méthode permettant d'obtenir l'instance unique de la classe.
	 * Si l'instance existe déjà elle est renvoyée, sinon elle est créée puis renvoyée.
	 * @return une instance de la classe.
	 */
	public static DAOComputer getDAOComputer() {
		if(daoComputer == null) {
			daoComputer = new DAOComputer();
		}
		return daoComputer;
	}
	
	/**
	 * Méthode Demandant à la base de données le nombre de computers selon leurs id.
	 * @return Le nombre de computers dans la base de données dans un ResultSet.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	private int DemandeNombreComputers() {
		int nombreComputers = 0;
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETENOMBRECOMPUTERS);
			if(resultSet.next()){
				// La première colonne est le count de l'id des computers.
				nombreComputers = resultSet.getInt(1);
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du nombre de computers: " + e.getLocalizedMessage());
			System.exit(1);
		}
		return nombreComputers;
	}
	
	/**
	 * Méthode faisant une requête pour vérifier que la base de données contient le computer dont l'id est passé en paramètre et retournée ses valeurs.
	 * @param companyId : L'id du computer concernant la requête.
	 * @return ResultSet contenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	private ResultSet findcomputerById(int computerId) {
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETEFINDCOMPUTERBYID + computerId + ";");
			return resultSet;
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence du computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}
	

	
	/**
	 * Méthode appliquant la requête (seulement SELECT) passé en paramètre à la base de données et retournant le résultat.
	 * @param requete Requête pour la base de données, forcément SELECT.
	 * @return ResultSet comprenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	private ResultSet faireRequeteAvecResultat(String requete) {
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			return resultSet;
		} catch(SQLException e) {
			System.out.println("Erreur lors du requêteage (SELECT) à la base de données à propos de Computers : \n " + e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}
	
	/**
	 * Méthode appliquant la requête (UPDATE, CREATE ou DELETE) passé en paramètre à la base de données et retournant le nombre de lignes concernées par la requête.
	 * @param requete Requête pour la base de données, peut être UPDATE, CREATE ou DELETE.
	 * @return nombreLignes : Le nombre de lignes ayant été concernées par la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	private int faireRequeteSansResultat(String requete) {
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			int nombreLignes = statement.executeUpdate(requete);
			return nombreLignes;
		} catch(SQLException e) {
			System.out.println("Erreur lors du requêteage (Non SELECT) à la base de données à propos de Computers : \n " + e.getLocalizedMessage());
			System.exit(1);
		}
		return 0;
	}
	
	/**
	 * Méthode faisant une requête à la base de données pour avoir la dernière page de computers et les renvoyant en String.
	 * @return un message d'erreur ou la liste des derniers computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 * @see Page
	 */
	public String listerComputersEnd() {
		String message = "";
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setNumeroPage(((nombreComputers-(nombreComputers%10))/10)+1);
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		List<Computer> listComputers = MapperComputer.mapResultSetToListComputer(faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS + (page.getNumeroPage()-1) * page.getNombreParPage() + ";"));
		message = listComputers.toString();
		return message;	
	}
	
	/**
	 * Méthode faisant une requête à la base de données pour avoir la page actuelle de computers et les renvoyant en String.
	 * @return un message d'erreur ou une liste de computers.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 * @see Page
	 */
	public String listerComputersPage() {
		String message = "";
		Page page = Page.getPage();
		int nombreComputers = DemandeNombreComputers();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		// Requête pour obtenir les computers de la page actuelle.
		List<Computer> listComputers = MapperComputer.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETENOMBRECOMPUTERSDEPUIS + (page.getNumeroPage()-1) * page.getNombreParPage() + ";"));
		message = listComputers.toString();
		return message;	
	}
	
	/**
	 * Méthode faisant une requête à la base de données pour avoir la liste entière de computers.
	 * @return Un message d'erreur ou la liste de computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String listerComputers(){
		String message = "";
		List<Computer> listeComputers = MapperComputer.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETELISTECOMPLETECOMPUTERS));
		message = listeComputers.toString();
		return message;
	}
	
	/**
	 * Méthode s'occupant de la vérification du nombre de lignes affecté par une requête (UPDATE, CREATE, DELETE) dans la base de données par rapport au nombre attendue (1).
	 * @param nombreLignes Nombre de lignes affectées par la requête. 
	 * @return String représentant le rapport nombre de ligne affectées et le nombre attendu (1).
	 */
	private String verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) {
		String message = "";
		if(nombreLignes == 1) {
			message = "L'opération a été effectué";
		} else if(nombreLignes == 0){
			message = "Un problème a occuré, l'opération n'a pas été effectué.";
		} else {
			message = "Un problème a occuré, l'opération a été efectué trop de fois (" + nombreLignes + ").";
		}
		return message;
	}
	
	/**
	 * Méthode faisant une requête pour ajouter le computer passé en argument à la base de données.
	 * @param computer Le computer à ajouter à la base de données.
	 * @param idCompany L'id de la company a ajouté au computer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String ajouter(Computer computer, int companyId) {
		String message = "";
		ResultSet resultSet = DAOCompany.getDAOCompany().findCompanybyId(companyId);
		int nombreLignes = 0;
		if(companyId != 0) {
			try {
				if(resultSet == null || !resultSet.next()) {
					message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
					return message;
				}
			} catch (SQLException e) {
				System.out.println("Un problème a occuré lors de l'utilisation d'un ResultSet : " + e.getLocalizedMessage());
				System.exit(1);
			}
			nombreLignes = this.faireRequeteSansResultat("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + computer.getName() + "', '" + computer.getIntroduced() + "', '" + computer.getDiscontinued() + "', '" + companyId + "');");
		} else {
			nombreLignes = this.faireRequeteSansResultat("INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + computer.getName() + "', '" + computer.getIntroduced() + "', '" + computer.getDiscontinued() + "', " + "NULL" + ");");
		}
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
	}
		
	/**
	 * Méthode faisant une requête à la base de données pour modifier les valeurs d'un computer.
	 * @param computer les nouvelles données du computer ayant le même id.
	 * @param idCompany L'id de la campany du computer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String modifier(Computer computer, int companyId) {
		String message = "";
		try {
			ResultSet resultSet = this.findcomputerById(computer.getId());
			if(!resultSet.next()) {
				message = "L'indice du computer à modifier n'est pas celui d'un computer existant.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence du computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		ResultSet resultSet = DAOCompany.getDAOCompany().findCompanybyId(companyId);
		try {
			if(resultSet == null || !resultSet.next()) {
				message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
				return message;
			}
		} catch (SQLException e) {
			System.out.println("Un problème a occuré lors de l'utilisation d'un ResultSet : " + e.getLocalizedMessage());
			System.exit(1);
		}
		int nombreLignes = this.faireRequeteSansResultat("update computer set computer.name = '" + computer.getName() + "', computer.introduced = '" + computer.getIntroduced() + "', computer.discontinued = '" + computer.getDiscontinued() + "', computer.company_id = " + companyId + " where computer.id = " + computer.getId() + ";");
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
	}
	
	/**
	 * Méthode faisant une requête pour supprimer un des computer.
	 * @param idComputer : id du computer à supprimer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String supprimer(int computerId) {
		String message = "";
		int nombreLignes = this.faireRequeteSansResultat(REQUETEDELETECOMPUTERBYID + computerId + ";");
		message = this.verificationFonctionnementRequêteNonSelectUnique(nombreLignes);
		return message;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir les informations du computer dont l'id est passé en paramètre.
	 * @param idComputer : id du computer à obtenir.
	 * @return message d'erreur ou les informations du computer en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String computerDetails(int computerId) {
		String message = "";
		List<Computer> listeComputers = MapperComputer.mapResultSetToListComputer(this.faireRequeteAvecResultat(REQUETEFINDCOMPUTERBYID + computerId + ";"));
		if(listeComputers.size() != 1) {
			message = "Le nombre de computers trouvés n'est pas valide (" + listeComputers.size() + ").";
		} else if(listeComputers.get(0) != null) {
			message =  listeComputers.get(0).toString();
		} else {
			message =  "Le computer trouvé est null.";
		}
		return message;
	}
}
