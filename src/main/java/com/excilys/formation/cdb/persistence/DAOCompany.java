package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;

/**
 * Classe comprenant les méthodes pour faire des requêtes à la base de données à
 * propos de la classe Companie.
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

	private static Logger logger = Logging.getLogger();

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	private DAOCompany() {
	}

	/**
	 * String représentant la requête pour obtenir la liste entière de companies de
	 * la base de données.
	 */
	private static final String REQUETELISTECOMPANIES = "SELECT company.id, company.name FROM company";

	private static final String REQUETEFINDBYID = "SELECT company.id, company.name FROM company WHERE company.id = ";

	/**
	 * Méthode permettant d'obtenir l'instance unique de la classe. Si l'instance
	 * existe déjà elle est renvoyée, sinon elle est créée puis renvoyée.
	 * 
	 * @return une instance de la classe.
	 */
	public static DAOCompany getDAOCompany() {
		if (daoCompany == null) {
			return new DAOCompany();
		} else {
			return daoCompany;
		}
	}

	/**
	 * Méthode faisant la requête à la base de données pour avoir la liste de
	 * companies.
	 * 
	 * @return un string étant soit un message d'erreur soit la liste de companies.
	 * @exception SQLException Si problème lorsque la base de données s'occupe de la
	 *                         requête.
	 */
	public List<Company> listerCompanies(String pOrderBy) {
		String orderBy = this.modificationOrderBy(pOrderBy);
		List<Company> listCompanies = new ArrayList<Company>();
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPANIES + " ORDER BY " + orderBy + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("company.id");
				String name = resultSet.getString("company.name");
				Company company = new Company.BuilderCompany(id).withName(name).build();
				listCompanies.add(company);
			}
		} catch (SQLException e) {
			logger.error("Problème lors de la requête de la liste de companies à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		return listCompanies;
	}

	/**
	 * Méthode faisant la requête à la base de données pour avoir la liste de
	 * companies.
	 * 
	 * @return un string étant soit un message d'erreur soit la liste de companies.
	 * @exception SQLException Si problème lorsque la base de données s'occupe de la
	 *                         requête.
	 */
	public List<Company> listerCompaniesRecherche(String rechercheNom, String pOrderBy) {
		String orderBy = this.modificationOrderBy(pOrderBy);
		List<Company> listCompanies = new ArrayList<Company>();
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPANIES + " WHERE company.name LIKE '%"
					+ rechercheNom + "%' ORDER BY " + orderBy + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("company.id");
				String name = resultSet.getString("company.name");
				Company company = new Company.BuilderCompany(id).withName(name).build();
				listCompanies.add(company);
			}
		} catch (SQLException e) {
			logger.error("Problème lors de la requête de la liste de companies à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		return listCompanies;
	}

	/**
	 * Méthode faisant une requête pour vérifier que la base de données contient la
	 * company dont l'id est passé en paramètre et retournée ses valeurs.
	 * 
	 * @param companyId : L'id de la company concernant la requête.
	 * @return ResultSet contenant les résultats de la requête.
	 * @exception SQLException Si problème lorsque la base de données s'occupe des
	 *                         requêtes.
	 */
	public ResultSet findCompanybyId(int companyId) {
		try {
			Statement statement = ConnexionSQL.getConnexion().createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETEFINDBYID + companyId + ";");
			return resultSet;
		} catch (SQLException e) {
			logger.error("Problème SQL lors de la requête de l'id d'une company à la base de données : "
					+ e.getLocalizedMessage());
			System.exit(1);
		}
		return null;
	}

	public void deleteCompanyAndComputers(int id) {
		System.out.println("Au début");
		String requeteRechercheEtDestructionComputers = "DELETE FROM computer WHERE computer.company_id = ?;";
		String requeteNombreComputersAvecCompany = "SELECT computer.company_id FROM computer WHERE computer.company_id = ?;";
		String requeteRechercheEtDestructionCompany = "DELETE FROM company WHERE company.id = ?";
		Connection connexion = ConnexionSQL.getConnexion();
		try {
			System.out.println("Le try");
			connexion.setAutoCommit(false);
			PreparedStatement statementDebut = connexion.prepareStatement(requeteRechercheEtDestructionComputers);
			PreparedStatement statementMilieu = connexion.prepareStatement(requeteNombreComputersAvecCompany);
			PreparedStatement statementFin = connexion.prepareStatement(requeteRechercheEtDestructionCompany);
			statementDebut.setInt(1, id);
			statementDebut.executeUpdate();
			System.out.println("Debut fait");
			statementMilieu.setInt(1, id);
			ResultSet resultSet = statementMilieu.executeQuery();
			if (resultSet.next()) {
				throw new SQLException();
			}
			System.out.println("milieu fait");
			statementFin.setInt(1, id);
			statementFin.executeUpdate();
			System.out.println("Fin faite");
			// 2 : détruire computers avec company
			// 3 Detruire companie
		} catch (SQLException e) {
			System.out.println("Prob e : " + e.getLocalizedMessage());
			if (connexion != null) {
				try {
					connexion.rollback();
				} catch (SQLException e1) {
					System.out.println("Prob e1 : " + e.getLocalizedMessage());
				}
			}
		} finally {
			try {
				connexion.commit();
				connexion.setAutoCommit(true);
//				connexion.close();
			} catch (SQLException e) {
			}
		}
	}

	public String modificationOrderBy(String orderBy) {
		String message = "";
		String[] orderBies = orderBy.split(" ");
		if (orderBies.length == 2) {
			message = " " + orderBies[1];
		}
		switch (orderBies[0]) {
		case "id":
			return "company.id" + message;
		case "name":
			return "company.name" + message;
		default:
			return "company.id" + message;
		}
	}
}
