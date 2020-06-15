package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.excilys.formation.cdb.JDBC.JDBC;
import com.excilys.formation.cdb.model.Computer;

public class DAOComputer {
	private static DAOComputer daoComputer;
	
	private DAOComputer() {}
	
	public static DAOComputer getDAOComputer() {
		if(daoComputer == null) {
			return new DAOComputer();
		} else {
		return daoComputer;
		}
	}
	
	public void Ajouter(Computer computer) {
		// Code pour ajouter un computer à la base de données.
	}
	
	public void Supprimer(Computer computer) {
		// Code pour supprimer un computer de la base de données.	
	}
	
	public List<Computer> Lister(){
		// Code pour obtenir la liste de computer de la base de donnés.
		return null;
	}
	
	public void Modifier(Computer computer) {
		// Code pour modifier un computer de la base de données.
	}

	public String ComputerDetails(int id) {
		// Code pour obtenir les informations d'un onrdinateur spécifiques en fonction de son Id.
		Computer computer = null;
		try {
			String requete = "Select * from computer where computer.id = " + id + ";";
			Statement stat = JDBC.con.createStatement();
			ResultSet rs = stat.executeQuery(requete);
			computer = new Computer(rs.getString("name"));
			computer.setId(rs.getInt("id"));
			computer.setIntroduced(	rs.getDate("introduced"));
			computer.setDiscontinued(rs.getDate("discontinued"));
			computer.setCompany(null);
		} catch (SQLException e) {
			System.out.println("Erreur lors de la création du statement pour une requête.");
			System.exit(1);
		}

		String message = computer.toString();
		return message;
	}
}
