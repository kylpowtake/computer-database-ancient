package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import com.excilys.formation.cdb.JDBC.JDBC;
import com.excilys.formation.cdb.model.Company;

public class DAOCompany {
	private static DAOCompany daoCompany;
	
	private DAOCompany() {}
	
	public static DAOCompany getDAOCompany() {
		if(daoCompany == null) {
			return new DAOCompany();
		} else {
		return daoCompany;
		}
	}
	public String ListerCompany(){
		// Code pour obtenir la liste de company de la base de données.
		String message = "";
		try {
			String requete = "Select * from company";
			Statement statement = JDBC.con.createStatement();
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
