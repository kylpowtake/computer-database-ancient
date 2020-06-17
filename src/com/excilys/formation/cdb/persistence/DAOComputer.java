package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;

import com.excilys.formation.cdb.JDBC.ConnexionSQL;
import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.model.Company;
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
	 * Méthode faisant une requête à la base de données pour avoir la dernière page de computers et les renvoyant en String.
	 * @return un message d'erreur ou la liste des derniers computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 * @see Page
	 */
	public String listerComputersEnd() {
		Page page = Page.getPage();
		String message = "";
		// Passage pour mettre à jour les informations de la page concernant la présence de page antérieure et postérieure..
		try {
			// Requête pour obtenir le nombre de computers dans la base de données.
			String requete = "SELECT COUNT(computer.id) FROM computer;";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			if(resultSet.next()){
				int nombreComputers = resultSet.getInt(1);
				// Le numéro de la dernière page doit être calculé à partir du nombre de computers et de computers par page.
				page.setNumeroPage(((nombreComputers-(nombreComputers%10))/10)+1);
				page.setPeutAllerNouvellePage(nombreComputers > page.getNumeroPage() * page.getNombreParPage());
				page.setPeutAllerAnciennePage(!(page.getNumeroPage() == 1));
			} else {
				message = "La recherche du nombre de computers n'a mené à aucun résultat, il n'y a pas de computer.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du nombre de computers: " + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			// Requête pour obtenir les computers de la dernière page.
			String requete = "Select * from computer left join company on computer.company_id = company.id LIMIT 10 OFFSET " + (page.getNumeroPage()-1) * page.getNombreParPage() + ";";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			while(resultSet.next()) {
				Computer computer = null;
				int idCompany = resultSet.getInt("company.id");
				String nameCompany = resultSet.getString("company.name");
				int indice = resultSet.getInt("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, idCompany, nameCompany);
				message += computer.toString();
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors du listage des computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;	
	}
	
	/**
	 * Méthode faisant une requête à la base de données pour avoir la page actuelle de computers et les renvoyant en String.
	 * @return un message d'erreur ou une liste de computers.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 * @see Page
	 */
	public String listerComputersPage() {
		Page page = Page.getPage();
		String message = "";
		// Passage pour mettre à jour les informations de la page concernant la présence de page antérieure et postérieure..
		try {
			// Requête pour obtenir le nombre de computers dans la base de données.
			String requete = "SELECT COUNT(computer.id) FROM computer;";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			if(resultSet.next()){
				int nombreComputers = resultSet.getInt(1);
				page.setPeutAllerNouvellePage(nombreComputers > page.getNumeroPage() * page.getNombreParPage());
			} else {
				message = "La recherche du nombre de computers n'a mené à aucun résultat, il n'y a pas de computer.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la recherche du nombre de computers: " + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			// Requête pour obtenir les computers de la page actuelle.
			String requete = "Select * from computer left join company on computer.company_id = company.id LIMIT 10 OFFSET " + (page.getNumeroPage()-1) * page.getNombreParPage() + ";";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			while(resultSet.next()) {
				Computer computer = null;
				int idCompany = resultSet.getInt("company.id");
				String nameCompany = resultSet.getString("company.name");
				int indice = resultSet.getInt("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, idCompany, nameCompany);
				message += computer.toString();
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors du listage des computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;	
	}
	
	/**
	 * Méthode faisant une requête à la base de données pour avoir la liste entière de computers.
	 * @return Un message d'erreur ou la liste de computers en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String listerComputers(){
		String message = "";
		try {
			// Requête pour obtenir tous les computers.
			String requete = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id;";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			while(resultSet.next()) {
				Computer computer = null;
				int idCompany = resultSet.getInt("company.id");
				String nameCompany = resultSet.getString("company.name");
				int indice = resultSet.getInt("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, idCompany, nameCompany);
				message += computer.toString();
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors du listage des computer : " + e.getLocalizedMessage());
			System.exit(1);
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
	public String ajouter(Computer computer, int idCompany) {
		String message = "";
		try {
			// Requête pour vérifier que l'id de company passé en argument est bien celui d'une company existante.
			String requete = "SELECT * FROM company WHERE company.id = " + idCompany;
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultatSet = statement.executeQuery(requete);
			if(!resultatSet.next()) {
				message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence de la company passé en paramètre : " + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			// Requête pour créer le nouveau computer.
			String requete = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + computer.getName() + "', '" + computer.getIntroduced() + "', '" + computer.getDiscontinued() + "', '" + idCompany + "');";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			int resultat = statement.executeUpdate(requete);
			if(resultat == 1) {
				message = "Le computer a bien été créé.";
			} else if(resultat == 0){
				message = "Un problème a occuré, aucun computer n'a été créé.";
			} else {
				message = "Un problème a occuré, trop de computer ont été créés (" + resultat + ").";
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la création du computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}
		
	/**
	 * Méthode faisant une requête à la base de données pour modifier les valeurs d'un computer.
	 * @param computer les nouvelles données du computer ayant le même id.
	 * @param idCompany L'id de la campany du computer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String modifier(Computer computer, int idCompany) {
		String message = "";
		try {
			// Requête pour vérifier que le computer devant être modifié existe bien.
			String requete = "select * from computer where computer.id = " + computer.getId();
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultatSet = statement.executeQuery(requete);
			if(!resultatSet.next()) {
				message = "L'indice du computer à modifier n'est pas celui d'un computer existant.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence du computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			// Requête pour vérifier que la companie passé en paramètre existe.
			String requete = "select * from company where company.id = " + idCompany;
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultatSet = statement.executeQuery(requete);
			if(!resultatSet.next()) {
				message = "L'indice de la company passé en paramètre n'est pas celui d'une company existante.";
				return message;
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence de la company passé en paramètre : " + e.getLocalizedMessage());
			System.exit(1);
		}
		try {
			// Requête pour modifier les données du computer.
			String requete = "update computer set computer.name = '" + computer.getName() + "', computer.introduced = '" + computer.getIntroduced() + "', computer.discontinued = '" + computer.getDiscontinued() + "', computer.company_id = " + idCompany + " where computer.id = " + computer.getId() + ";";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			int resultat = statement.executeUpdate(requete);
			if(resultat == 1) {
				message = "Le computer a bien été modifié.";
			} else if(resultat == 0){
				message = "Un problème a occuré, aucun computer a été modifé.";
			} else {
				message = "Un problème a occuré, trop de computer ont été modifés (" + resultat + ").";
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la modification du computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}
	
	/**
	 * Méthode faisant une requête pour supprimer un des computer.
	 * @param idComputer : id du computer à supprimer.
	 * @return Un message d'erreur ou de succès.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String supprimer(int idComputer) {
		String message = "";
		try {
			// Requête pour supprimer le computer.
			String requete = "delete from computer where computer.id = " + idComputer + ";";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			int nombreSupprimer = statement.executeUpdate(requete);
			if(nombreSupprimer != 0) {
			message = "Nombre d'éléments supprimés : " + nombreSupprimer;
			} else {
			message = "Aucun computer n'a été supprimé, l'indice donné ne correspondait pas à un computer.";	
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la création du statement pour la requête de suppression d'un computer." + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}

	/**
	 * Méthode faisant une requête à la base de données pour avoir les informations du computer dont l'id est passé en paramètre.
	 * @param idComputer : id du computer à obtenir.
	 * @return message d'erreur ou les informations du computer en String.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public String computerDetails(int idComputer) {
		Computer computer = null;
		try {
			// Requête pour obtenir les informations du computer.
			String requete = "Select * from computer left join company on computer.company_id = company.id where computer.id = " + idComputer + ";";
			Statement stat = ConnexionSQL.getConnexion().createStatement();
			ResultSet rs = stat.executeQuery(requete);
			if(rs.next()) {
				int idCompany = rs.getInt("company.id");
				String nameCompany = rs.getString("company.name");
				Company company = null;
				if(idCompany != 0 && nameCompany != null) {
					company = new Company(idCompany, nameCompany);
				}
				int indice = rs.getInt("computer.id");
				String name = rs.getString("computer.name");
				if(name == "null") {
					name = "";
				}
				Date introducedDate = rs.getDate("computer.introduced");
				Date discontinuedDate = rs.getDate("computer.discontinued");
				LocalDate introduced = null;
				LocalDate discontinued = null;				
				if(introducedDate != null) {
					introduced = introducedDate.toLocalDate();
				}
				if(discontinuedDate != null) {
					discontinued = discontinuedDate.toLocalDate();
				}	
				computer = new Computer(indice,
						name,
						introduced,
						discontinued,
						company);
			}
		} catch (SQLException e) {
			System.out.println("Erreur lors de la création du statement pour une requête." + e.getLocalizedMessage());
			System.exit(1);
		}
		if(computer != null) {
			return computer.toString();
		} else {
			return "L'indice que vous avez donné ne correspond pas à un computer.";
		}
	}
}
