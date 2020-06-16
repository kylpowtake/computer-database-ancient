package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.sql.Date;

import com.excilys.formation.cdb.JDBC.JDBC;
import com.excilys.formation.cdb.model.Company;
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
	

	
	public String ListerComputer(){
		// Code pour obtenir la liste de computer de la base de donnés.
		String message = "";
		try {
			String requete = "Select * from computer left join company on computer.company_id = company.id;";
			Statement statement = JDBC.con.createStatement();
			ResultSet resultSet = statement.executeQuery(requete);
			while(resultSet.next()) {
				Computer computer = null;
				int idCompany = resultSet.getInt("company.id");
				String nameCompany = resultSet.getString("company.name");
				Company company = null;
				if(idCompany != 0 && nameCompany != null) {
					company = new Company(idCompany, nameCompany);
				}
				int indice = resultSet.getInt("computer.id");
				String name = resultSet.getString("computer.name");
				if(name == "null") {
					name = "";
				}
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
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
				message += computer.toString();
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors du listage des computer : " + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}
	
	public String Ajouter(Computer computer, int idCompany) {
		// Code pour ajouter un computer à la base de données.
		String message = "";
		try {
			String requete = "select * from company where company.id = " + idCompany;
			Statement statement = JDBC.con.createStatement();
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
			String requete = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES ('" + computer.getName() + "', '" + computer.getIntroduced() + "', '" + computer.getDiscontinued() + "', '" + idCompany + "');";
			Statement statement = JDBC.con.createStatement();
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
		
	public String Modifier(Computer computer, int idCompany) {
		// Code pour modifier un computer de la base de données.
		String message = "";
		try {
			String requete = "select * from computer where computer.id = " + computer.getId();
			Statement statement = JDBC.con.createStatement();
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
			String requete = "select * from company where company.id = " + idCompany;
			Statement statement = JDBC.con.createStatement();
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
			String requete = "update computer set computer.name = '" + computer.getName() + "', computer.introduced = '" + computer.getIntroduced() + "', computer.discontinued = '" + computer.getDiscontinued() + "', computer.company_id = " + idCompany + " where computer.id = " + computer.getId() + ";";
			Statement statement = JDBC.con.createStatement();
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
	
	public String Supprimer(int idComputer) {
		// Code pour supprimer un computer de la base de données.
		String message = "";
		try {
			String requete = "delete from computer where computer.id = " + idComputer + ";";
			Statement statement = JDBC.con.createStatement();
			int nombreSupprimer = statement.executeUpdate(requete);
			if(nombreSupprimer != 0) {
			message = "Nombre d'éléments supprimés : " + nombreSupprimer;
			} else {
			message = "Aucun computer n'a été supprimé, l'indice donné ne corrspondait pas à un computer.";	
			}
		} catch(SQLException e) {
			System.out.println("Erreur lors de la création du statement pour la requête de suppression d'un computer." + e.getLocalizedMessage());
			System.exit(1);
		}
		return message;
	}

	public String ComputerDetails(int idComputer) {
		// Code pour obtenir les informations d'un onrdinateur spécifiques en fonction de son Id.
		Computer computer = null;
		try {
			String requete = "Select * from computer left join company on computer.company_id = company.id where computer.id = " + idComputer + ";";
			Statement stat = JDBC.con.createStatement();
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
