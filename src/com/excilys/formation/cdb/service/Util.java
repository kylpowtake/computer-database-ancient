package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.JDBC.JDBC;
import com.excilys.formation.cdb.persistence.DAOComputer;
import com.excilys.formation.cdb.ui.CLI;

public class Util {
	public static DAOComputer daoComputer;

	public static void main(String[] args) {
		JDBC.connexionSQL();
		daoComputer = DAOComputer.getDAOComputer();
		DemandeDeCommande();
	}
	
	public static void GestionMessage(String message) {
		CLI.Ecriture(message);
		ReceptionGestionMessage(CLI.Lecture());
	}

	
	public static void ReceptionGestionMessage(String message) {
		String[] messageSplit = message.split(" : ");
		if(messageSplit.length == 1) {
			switch(message) {
				case "Help":
					CommandeDAide();
					break;
				default:
					DemandeMauvaiseCommande();
					break;
			}
		} else if(messageSplit.length == 2){
			String commande = messageSplit[0];
			String argument = messageSplit[1];
			switch(commande) {
			case "List computers":
				break;
			case "List companies":
				break;
			case "Create a computer":
				break;
			case "Update a computer":
				break;
			case "Delete a computer":
				break;
			case "Show computer details":
				CommandeDetailsComputer(argument);
				break;
			default:
				DemandeMauvaiseCommande();
				break;
			}
		} else {
			DemandeTropDeSplittage();
		}
	}
	
	public static void CommandeDetailsComputer(String argument) {
		String message = "";
		if(TestArgumentInt(argument)) {
			message = daoComputer.ComputerDetails(Integer.parseInt(argument));
		} else {
			message = "L\'argument donné à la commande n\'est pas un nombre.";
		}
		GestionMessage(message);
	}
	
	public static void CommandeDAide() {
		String message = "Voici les différentes commandes et leurs significations ainsi que de l'aide : \n";
		message = message + "Pour donner des arguments, placer ' : ' entre la commande et l'argument.\n";
		message = message  + "'Help' : Affiche les différentes commandes et leurs effets.\n";
		message = message + "'Show computer details' : Affiche  les détails de l'odinateur dont l'indice est donné en argument.\n";
		GestionMessage(message);
	}
	
	public static void DemandeDeCommande() {
		String message = "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		GestionMessage(message);
	}
	
	public static void DemandeTropDeSplittage() {
		String message = "Votre message est eronné, vous avez saisi trop de ':'\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		GestionMessage(message);
	}
	
	public static void DemandeMauvaiseCommande() {
		String message = "Votre message est eronné, vous avez saisi une mauvaise commande.\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		GestionMessage(message);
	}
	
	
	
	public static Boolean TestArgumentInt(String argument) {		
		for(int i = 0; i < argument.length(); i++) {
			char c = argument.charAt(i);
			if(c > '9' || c < '0') {
				return false;
			}
		}
		return true;
	}
}