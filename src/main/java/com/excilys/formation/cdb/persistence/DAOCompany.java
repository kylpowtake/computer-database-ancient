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
	 * String représentant la requête pour obtenir la liste entière de companies de la base de données.
	 */
	private static final String REQUETELISTECOMPANIES = "SELECT company.id, company.name FROM company";
	
	private static final String REQUETEFINDBYID= "SELECT company.id, company.name FROM company WHERE company.id = ";
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
		String message = "";
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPANIES);
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
	
	/**
	 * Méthode faisant une requête pour vérifier que la base de données contient la company dont l'id est passé en paramètre et retournée ses valeurs.
	 * @param companyId : L'id de la company concernant la requête.
	 * @return ResultSet contenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des requêtes.
	 */
	public ResultSet findCompanybyId(int companyId) {
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETEFINDBYID + companyId + ";");
			return resultSet;
		} catch(SQLException e) {
			System.out.println("Erreur lors de la vérification de l'existence d'une company : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}
}
