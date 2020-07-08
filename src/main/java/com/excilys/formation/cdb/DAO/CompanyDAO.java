package com.excilys.formation.cdb.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.datasource.ConnectionSQL;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.mapper.MapperCompany;
import com.excilys.formation.cdb.model.Company;

/**
 * Classe comprenant les méthodes pour faire des requêtes à la base de données à
 * propos de la classe Companie.
 * 
 * @author kylian
 * @see Company
 *
 */
@Repository
public class CompanyDAO implements ObjectDAO<Company>{
	/**
	 * Champ privé permettant d'avoir un singleton pour cette classe.
	 */
	private static CompanyDAO daoCompany;

	private Logger logger;

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	private CompanyDAO() {
	}

	@Autowired
	ConnectionSQL connectionSQL;

	public ConnectionSQL getConnection() {
		return connectionSQL;
	}
	
	public void setConnection(ConnectionSQL connectionSQL) {
		this.connectionSQL = connectionSQL;
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
	public static CompanyDAO getDAOCompany() {
		if (daoCompany == null) {
			return new CompanyDAO();
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
	public List<Company> all(String pOrderBy) {
		String orderBy = this.modificationOrderBy(pOrderBy);
		List<Company> listCompanies = new ArrayList<Company>();
		try {
			Connection connection = connectionSQL.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPANIES + " ORDER BY " + orderBy + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("company.id");
				String name = resultSet.getString("company.name");
				Company company = new Company.BuilderCompany(id).withName(name).build();
				listCompanies.add(company);
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("dans except");
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
	public List<Company> allSearch(String rechercheNom, String pOrderBy) {
		String orderBy = this.modificationOrderBy(pOrderBy);
		List<Company> listCompanies = new ArrayList<Company>();
		try {
			Connection connection = connectionSQL.getConnection();
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(REQUETELISTECOMPANIES + " WHERE company.name LIKE '%"
					+ rechercheNom + "%' ORDER BY " + orderBy + ";");
			while (resultSet.next()) {
				int id = resultSet.getInt("company.id");
				String name = resultSet.getString("company.name");
				Company company = new Company.BuilderCompany(id).withName(name).build();
				listCompanies.add(company);
			}
			statement.close();
			connection.close();
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
	public Company find(int companyId) throws Exception {
		Company company = null;
		try {
			Connection connection = connectionSQL.getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(REQUETEFINDBYID + companyId + ";");
			if (resultSet.next()) {
				company = MapperCompany.resultSetToCompany(resultSet);
				if (company == null) {
					throw new Exception("Une company reçue par ResultSet est nulle : findCompanyById");
				}
				if(resultSet.next()) {
					throw new Exception("Le resultSet devant avoir une company en avait plusieurs : findCompanyById");
				}
			}
			statement.close();
			connection.close();
		} catch (SQLException e) {
			throw new Exception(e.getLocalizedMessage() + " : findCompanyById");
		} catch (Exception e) {
			throw new Exception(e.getLocalizedMessage() + " : findCompanyById");
		}
		return company;
	}

	public Resultat delete(int id) {
		String requeteRechercheEtDestructionComputers = "DELETE FROM computer WHERE computer.company_id = ?;";
		String requeteNombreComputersAvecCompany = "SELECT computer.company_id FROM computer WHERE computer.company_id = ?;";
		String requeteRechercheEtDestructionCompany = "DELETE FROM company WHERE company.id = ?";
		Connection connection = null;
		try {
			connection = connectionSQL.getConnection();
			connection.setAutoCommit(false);
			PreparedStatement statementDebut = connection.prepareStatement(requeteRechercheEtDestructionComputers);
			PreparedStatement statementMilieu = connection.prepareStatement(requeteNombreComputersAvecCompany);
			PreparedStatement statementFin = connection.prepareStatement(requeteRechercheEtDestructionCompany);
			statementDebut.setInt(1, id);
			statementDebut.executeUpdate();
			statementMilieu.setInt(1, id);
			ResultSet resultSet = statementMilieu.executeQuery();
			if (resultSet.next()) {
				throw new SQLException();
			}
			statementFin.setInt(1, id);
			statementFin.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Prob e : " + e.getLocalizedMessage());
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					System.out.println("Prob e1 : " + e.getLocalizedMessage());
				}
			}
		} finally {
			try {
				if (connection != null) {
					connection.commit();
					connection.setAutoCommit(true);
					connection.close();
				}
			} catch (SQLException e) {
			}
		}
		return Resultat.REUSSI;
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

	@Override
	public List<Company> some(String orderBy) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Company> someSearch(String orderBy, String search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat modify(Company object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resultat create(Company object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Logger getLogger() {
		return this.logger;
	}

	@Override
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
