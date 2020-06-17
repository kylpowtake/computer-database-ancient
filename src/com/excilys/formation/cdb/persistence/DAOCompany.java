package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.model.Company;

/**
 * Classe comprenant les méthodes pour faire des requêtes à la base de données à propos de la classe Companie.
 * 
 * @author kylian
 * @see Company
 *
 */
public class DAOCompany {
	/**
	 * Champ privé permettant d'avoir un singleton pour cette classe.
	 */
	private static DAOCompany daoCompany;
	
	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	private DAOCompany() {}
	
	/**
	 * Méthode permettant d'obtenir l'instance unique de la classe.
	 * Si l'instance existe déjà elle est renvoyée, sinon elle est créée puis renvoyée.
	 * @return une instance de la classe.
	 */
	public static DAOCompany getDAOCompany() {
		if(daoCompany == null) {
			return new DAOCompany();
		} else {
			return daoCompany;
		}
	}
	
	/**
	 * Méthode faisant la requête à la base de données pour avoir la liste de companies.
	 * @return un string étant soit un message d'erreur soit la liste de companies.
	 * @exception SQLException Si problème lorsque la base de données s'occupe de la requête.
	 */
	public String listerCompanies(){
		// Code pour obtenir la liste de company de la base de données.
		String message = "";
		try {
			String requete = "Select * from company";
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			while(resultSet.next()) {
				int idCompany = resultSet.getInt("company.id");
				String nameCompany = resultSet.getString("company.name");
				Company company = new Company(idCompany, nameCompany);
				message += company.toString();
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors du listage des computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}
}
