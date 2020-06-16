package com.excilys.formation.cdb.service;

import java.time.LocalDate;

import com.excilys.formation.cdb.JDBC.JDBC;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DAOCompany;
import com.excilys.formation.cdb.persistence.DAOComputer;
import com.excilys.formation.cdb.ui.CLI;

public class Util {
	public static DAOComputer daoComputer;
	public static DAOCompany daoCompany;
	
	public static void main(String[] args) {
		JDBC.connexionSQL();
		daoComputer = DAOComputer.getDAOComputer();
		daoCompany = DAOCompany.getDAOCompany();
		demandeDeCommande();
	}
	
	public static void gestionMessage(String message) {
		CLI.Ecriture(message);
		receptionGestionMessage(CLI.Lecture());
	}

	
	public static void receptionGestionMessage(String message) {
		String[] messageSplit = message.split(" : ");
		if(messageSplit.length == 1) {
			switch(message) {
				case "Help":
					commandeDAide();
					break;
				case "List computer":
					commandeListComputer();
					break;
				case "List company":
					commandeListCompany();
					break;
				default:
					demandeMauvaiseCommande();
					break;
			}
		} else if(messageSplit.length == 2){
			String commande = messageSplit[0];
			String argument = messageSplit[1];
			switch(commande) {
			case "Create a computer":
				commandeCreateComputer(argument);
				break;
			case "Update a computer":
				commandeMAJComputer(argument);
				break;
			case "Delete a computer":
				commandeSupprimerComputer(argument);
				break;
			case "Show computer details":
				commandeDetailsComputer(argument);
				break;
			default:
				demandeMauvaiseCommande();
				break;
			}
		} else {
			demandeTropDeSplittage();
		}
	}
	
	public static void commandeListCompany() {
		String message = "";
		message = daoCompany.ListerCompany();
		gestionMessage(message);
	}
	
	public static void commandeListComputer() {
		String message = "";
		message = daoComputer.ListerComputer();
		gestionMessage(message);
	}
	
	
	
	/*
	 * La majorité de la fonction est interchangeable avec commandeMAJComputer;  
	 */
	public static void commandeCreateComputer(String argument) {
		String message = "";
		String[] argumentSplit = argument.split("::");
		if(argumentSplit.length == 4) {
			String name = argumentSplit[0];
			String[] introducedString = argumentSplit[1].split(":");
			String[] discontinuedString = argumentSplit[2].split(":");
			LocalDate introduced = null;
			LocalDate discontinued = null;
			int idCompany = 0;
			if(testArgumentInt(argumentSplit[3])) {
				idCompany = Integer.parseInt(argumentSplit[3]);
			} else {
				message = "L'argument donné pour l'indice de la company du computer n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(introducedString.length == 3) {
				int yearIntroduced = stringDateToInt(introducedString[0], "year");
				int monthIntroduced = stringDateToInt(introducedString[1], "month");
				int dayIntroduced = stringDateToInt(introducedString[2], "day");
				if(yearIntroduced != 0 && monthIntroduced != 0 && dayIntroduced != 0) {
					introduced = LocalDate.of(yearIntroduced,monthIntroduced, dayIntroduced);
				} else {
					message = "L'argument donné pour la date 'introduced' n'est pas valide.";
					gestionMessage(message);
					return;
				}
			} else if(introducedString != null){
				message = "L'argument donné pour la date 'introduced' n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(discontinuedString.length == 3) {
				int yearDiscontinued = stringDateToInt(discontinuedString[0], "year");
				int monthDiscontinued = stringDateToInt(discontinuedString[1], "month");
				int dayDiscontinued = stringDateToInt(discontinuedString[2], "day");
				if(yearDiscontinued != 0 && monthDiscontinued != 0 && dayDiscontinued != 0) {
					discontinued = LocalDate.of(yearDiscontinued, monthDiscontinued, dayDiscontinued);
				} else {
					message = "L'argument donné pour la date 'discontinued' n'est pas valide.";
					gestionMessage(message);
					return;
				}
			} else if(discontinuedString != null){
				message = "L'argument donné pour la date 'discontinued' n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(introduced.isBefore(discontinued)) {
			Computer computer = new Computer(0, name, introduced, discontinued, null);
			message = daoComputer.Ajouter(computer, idCompany);				
			} else {
				message = "La date 'discontinued' donné est plus veille que celle de 'introduced'.";
			}
		} else {
			message = "Le nombre d'arguments n'est pas le bon.";
		}
		gestionMessage(message);
	}
	
	public static void commandeMAJComputer(String argument) {
		String message = "";
		String[] argumentSplit = argument.split("::");
		if(argumentSplit.length == 5) {
			int id = 0;
			if(testArgumentInt(argumentSplit[0])) {
				id = Integer.parseInt(argumentSplit[0]);
			} else {
				message = "L'argument donné pour l'indice du computer n'est pas valide.";
				gestionMessage(message);
				return;
			}
			String name = argumentSplit[1];
			String[] introducedString = argumentSplit[2].split(":");
			String[] discontinuedString = argumentSplit[3].split(":");
			LocalDate introduced = null;
			LocalDate discontinued = null;
			int idCompany = 0;
			if(testArgumentInt(argumentSplit[4])) {
				idCompany = Integer.parseInt(argumentSplit[4]);
			} else {
				message = "L'argument donné pour l'indice de la company du computer n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(introducedString.length == 3) {
				int yearIntroduced = stringDateToInt(introducedString[0], "year");
				int monthIntroduced = stringDateToInt(introducedString[1], "month");
				int dayIntroduced = stringDateToInt(introducedString[2], "day");
				if(yearIntroduced != 0 && monthIntroduced != 0 && dayIntroduced != 0) {
					introduced = LocalDate.of(yearIntroduced,monthIntroduced, dayIntroduced);
				} else {
					message = "L'argument donné pour la date 'introduced' n'est pas valide.";
					gestionMessage(message);
					return;
				}
			} else if(introducedString != null){
				message = "L'argument donné pour la date 'introduced' n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(discontinuedString.length == 3) {
				int yearDiscontinued = stringDateToInt(discontinuedString[0], "year");
				int monthDiscontinued = stringDateToInt(discontinuedString[1], "month");
				int dayDiscontinued = stringDateToInt(discontinuedString[2], "day");
				if(yearDiscontinued != 0 && monthDiscontinued != 0 && dayDiscontinued != 0) {
					discontinued = LocalDate.of(yearDiscontinued, monthDiscontinued, dayDiscontinued);
				} else {
					message = "L'argument donné pour la date 'discontinued' n'est pas valide.";
					gestionMessage(message);
					return;
				}
			} else if(discontinuedString != null){
				message = "L'argument donné pour la date 'discontinued' n'est pas valide.";
				gestionMessage(message);
				return;
			}
			if(introduced.isBefore(discontinued)) {
			Computer computer = new Computer(id, name, introduced, discontinued, null);
			message = daoComputer.Modifier(computer, idCompany);				
			} else {
				message = "La date 'discontinued' donné est plus veille que celle de 'introduced'.";
			}
		} else {
			message = "Le nombre d'arguments n'est pas le bon.";
		}
		gestionMessage(message);
	}
	
	public static void commandeSupprimerComputer(String argument) {
		String message = "";
		if(testArgumentInt(argument)) {
			message = daoComputer.Supprimer(Integer.parseInt(argument));
		} else {
			message = "L\'argument donné à la commande n\'est pas un nombre.";
		}
		gestionMessage(message);
	}
	
	public static void commandeDetailsComputer(String argument) {
		String message = "";
		if(testArgumentInt(argument)) {
			message = daoComputer.ComputerDetails(Integer.parseInt(argument));
		} else {
			message = "L\'argument donné à la commande n\'est pas un nombre.";
		}
		gestionMessage(message);
	}
	
	public static void commandeDAide() {
		String message = "Voici les différentes commandes et leurs significations ainsi que de l'aide : \n";
		message = message + "Pour donner des arguments, il y a besoin de ' : ' entre la commande et l'argument.\n";
		message = message + "Lorsqu'il y a besoin de donner plusieurs arguments, il faut les séparer avec '::'.\n";
		message = message + "Une date doit être de type 'YYYY:MM:DD'.\n";
		message = message + "Un computer est composé dans l'ordre de son indice, son nom, sa date d'introduction, sa date de fin et de sa campany(l'indice).\n";
		message = message  + "'Help' : Affiche les différentes commandes et leurs effets.\n";
		message = message + "'Show computer details' : Affiche  les détails de l'ordinateur dont l'indice est donné en argument.\n";
		message = message + "'Delete a computer' : Supprime l'ordinateur dont l'indice est donné en argument.\n";
		message = message + "'Update a computer' : Modifie s'il existe l'ordinateur passé en argument.\n";
		message = message + "'Create a computer' : Créé un computer avec les parametres données.\n";
		message = message + "'List computer' : Affiche la liste entière de computer.\n";
		message = message + "'List company' : Affiche la liste entière de company.\n";
		gestionMessage(message);
	}
	
	public static void demandeDeCommande() {
		String message = "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	public static void demandeTropDeSplittage() {
		String message = "Votre message est eronné, vous avez saisi trop de ':'\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	public static void demandeMauvaiseCommande() {
		String message = "Votre message est eronné, vous avez saisi une mauvaise commande.\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	
	public static int stringDateToInt(String stringDate, String type) {
		switch(type) {
		case "year":
			if(stringDate.length() == 4 && testArgumentInt(stringDate)) {
				return Integer.parseInt(stringDate);
			}
			break;
		case "month":
			if(stringDate.length() == 2 
			&& testArgumentInt(stringDate) 
			&& Integer.parseInt(stringDate) >= 1 
			&& Integer.parseInt(stringDate) < 13) {
				return Integer.parseInt(stringDate);
			}
			break;
		case "day":
			if(stringDate.length() == 2 && testArgumentInt(stringDate) && Integer.parseInt(stringDate) >= 1 && Integer.parseInt(stringDate) < 32) {
				return Integer.parseInt(stringDate);
			}
			break;
		default :
			System.out.println("Mauvais paramétrage passé à stringDateToInt");
			System.exit(1);
			break;
		}
		return 0;
	}
	
	public static Boolean testArgumentInt(String argument) {		
		for(int i = 0; i < argument.length(); i++) {
			char c = argument.charAt(i);
			if(c > '9' || c < '0') {
				return false;
			}
		}
		return true;
	}
}