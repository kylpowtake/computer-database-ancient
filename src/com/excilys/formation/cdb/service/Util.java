package com.excilys.formation.cdb.service;

import java.time.LocalDate;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DAOCompany;
import com.excilys.formation.cdb.persistence.DAOComputer;
import com.excilys.formation.cdb.ui.CLI;

/**
 * Classe s'occupant de la gestion des messages de l'utilisateur et appelant les méthodes nécessaires pour y répondre.
 * @author kylian
 * @see DAOComputer
 * @see DAOCompany
 * @see Page
 */
public class Util {
	/**
	 * Le DAOComputer permettant de faire des requêtes concernant les computers.
	 */
	public static DAOComputer daoComputer;
	/**
	 * Le DAOCompany permettant de faire des requêtes concernant les companies.
	 */
	public static DAOCompany daoCompany;
	/**
	 * La page permettant de gérer un nombre de computers.
	 */
	public static Page page;
	
	/**
	 * La méthode main, lance l'initialisation des daos et de la page puis lance la communication avec l'utilisateur..
	 * @param args les paramèters utilisés au lancement du programme, ne sont pas pris en compte.
	 */
	public static void main(String[] args) {
		ConnexionSQL.getConnexion();
		daoComputer = DAOComputer.getDAOComputer();
		daoCompany = DAOCompany.getDAOCompany();
		page = Page.getPage();
		demandeDeCommande();
	}
	
	/**
	 * Méthode intermédiare entre deux actions, passe le résultat à montrer à l'utilisateur et attend son prochain message pour l'envoyer à la fonction s'en occupant.
	 * @param message Le résultat de la dernière action.
	 */
	public static void gestionMessage(String message) {
		CLI.Ecriture(message);
		receptionGestionMessage(CLI.Lecture());
	}

	/**
	 * S'occupe de la gestion des messages de l'utilisateur, lance la méthode correspondante à la demande de l'utilisateur.
	 * @param message Demande de l'utilisateur, correspond à un mot-clé pour la commande et peut avoir des arguments pour la commande..
	 */
	public static void receptionGestionMessage(String message) {
		// les arguments sont séparés de la commande par ' : '.
		String[] messageSplit = message.split(" : ");
		if(messageSplit.length == 1) {
			// Commande sans arguments.
			switch(message) {
				case "Help":
					commandeDAide();
					break;
				case "List computers":
					commandeListComputers();
					break;
				case "List companies":
					commandeListCompanies();
					break;
				case "List computers page":
					commandeListComputersBeginning();
					break;
				default:
					demandeMauvaiseCommande();
					break;
			}
		} else if(messageSplit.length == 2){
			// commande avec des arguments.
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
	
	/**
	 * Méthode intermédiare entre deux actions concernant une page de computer, passe le résultat à montrer à l'utilisateur et attend son prochain message pour l'envoyer à la fonction s'en occupant.
	 * @param message Le résultat de la dernière action.
	 */
	public static void gestionMessagePage(String message) {
		CLI.Ecriture(message);
		receptionGestionMessagePage(CLI.Lecture());
	}
	
	/**
	 * S'occupe de la gestion des messages de l'utilisateur conernant une page de computer, lance la méthode correspondante à la demande de l'utilisateur.
	 * @param message Demande de l'utilisateur, correspond à un mot-clé pour la commande.
	 */
	public static void receptionGestionMessagePage(String message) {
		// les arguments sont séparés de la commande par ' : '.
		String[] messageSplit = message.split(" : ");
		if(messageSplit.length != 1) {
			demandeMauvaiseCommandePage();
		} else {
			switch(messageSplit[0]) {
			case "Help":
				commandeDAidePage();
				break;
			case "Next":
				commandeListComputersNext();
				break;
			case "Previous":
				commandeListComputersPrevious();
				break;
			case "End":
				commandeListComputersEnd();
				break;
			case "Beginning":
				commandeListComputersBeginning();
				break;
			case "Quit":
				commandeQuit();
				break;
			default :
				demandeMauvaiseCommandePage();
				break;
			}
		}
	}
	
	/**
	 * Méthode affichant l'aide pour les commandes à propos de page.
	 */
	public static void commandeDAidePage() {
		String message = "Voici les différentes commandes et leurs significations ainsi que de l'aide : \n";
		message = message + "Pour donner des arguments, il y a besoin de ' : ' entre la commande et l'argument.\n";
		message = message + "'Help' : Affiche les différentes commandes et leurs effets.\n";
		message = message + "'Next' : Afficher les computers suivant.\n";
		message = message + "'Previous' : Afficher les computers précédants.\n";
		message = message + "'End' : Aller à la fin de la liste de computers.\n";
		message = message + "'Beginning' : Aller au début de la liste.\n";
		message = message + "'Quit' : Arrêter d'afficher les computers.\n";
		gestionMessagePage(message);
	}
	
	/**
	 * Méthode permettant d'arrêter de faire des commandes à propos de page.
	 */
	public static void commandeQuit() {
		String message = "Arrêt de l'affichage des computers.\n";
		gestionMessage(message);
	}
	
	/**
	 * Méthode en cas d'erreur de commande de la part de l'utilisateur.
	 */
	public static void demandeMauvaiseCommandePage() {
		String message = "Votre message est eronné, vous avez saisi une mauvaise commande.\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessagePage(message);
	}
	
	/**
	 * Méthode permettant d'afficher la page suivante de computers.
	 */
	public static void commandeListComputersNext() {
		if(page.isPeutAllerNouvellePage()) {
		String message = "";
		page.setNumeroPage(page.getNumeroPage()+1);
		page.setPeutAllerAnciennePage(true);
		message = daoComputer.listerComputersPage();
		gestionMessagePage(message);
		} else {
			String message = "Vous êtes à la fin de la liste des computers, vous ne pouvez allez plus loin.\n";
			message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
			gestionMessagePage(message);
		}
	}
	
	/**
	 * Méthode permettant d'afficher la page précédante de computers.
	 */
	public static void commandeListComputersPrevious() {
		if(page.isPeutAllerAnciennePage()) {
		String message = "";
		page.setNumeroPage(page.getNumeroPage()-1);
		if(page.getNumeroPage() == 1) {
			page.setPeutAllerAnciennePage(false);
		} else {
			page.setPeutAllerAnciennePage(true);	
		}
		message = daoComputer.listerComputersPage();
		gestionMessagePage(message);
		} else {
			String message = "Vous êtes au début de la liste des computers, vous ne pouvez allez en arrière.\n";
			message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
			gestionMessagePage(message);
		}
	}
	
	/**
	 * Méthode permettant d'affiche rla dernière page de computers.
	 */
	public static void commandeListComputersEnd() {
		String message = "";
		message = daoComputer.listerComputersEnd();
		gestionMessagePage(message);
	}
	
	/**
	 * Méthode permettant d'afficher la première page de computer.
	 */
	public static void commandeListComputersBeginning() {
		String message = "";
		page.setNumeroPage(1);
		page.setPeutAllerAnciennePage(false);
		message = daoComputer.listerComputersPage();
		gestionMessagePage(message);
	}
	
	/**
	 * Méthode permettant d'afficher la liste entière de companies.
	 */
	public static void commandeListCompanies() {
		String message = "";
		message = daoCompany.listerCompanies();
		gestionMessage(message);
	}
	
	/**
	 * Méthode permettant d'afficher la liste entière de computers.
	 */
	public static void commandeListComputers() {
		String message = "";
		message = daoComputer.listerComputers();
		gestionMessage(message);
	}
	
	/**
	 * Méthode prenant les données d'un computer en String en paramètre et appelant une méthode de daoComputer pour faire la requête de sa création.
	 * @param argument Les données d'un computer à créer en String.
	 */
	public static void commandeCreateComputer(String argument) {
		String message = "";
		String[] argumentSplit = argument.split("::");
		if(argumentSplit.length == 4) {
			String name = verificationString(argumentSplit[0]);
			if(name.equals("")) {
				message = "L'argument donné pour le nom du computer est vide, ce n'est autorisé.";
				gestionMessage(message);
				return;
			}
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
			message = daoComputer.ajouter(computer, idCompany);				
			} else {
				message = "La date 'discontinued' donné n'est pas plus récente que celle de 'introduced'.";
			}
		} else {
			message = "Le nombre d'arguments n'est pas le bon.";
		}
		gestionMessage(message);
	}
	
	/**
	 * Méthode prenant les données d'un computer en String en paramètre et appelant une méthode de daoComputer pour faire la requête de sa modification.
	 * @param argument Les données d'un computer à créer en String.
	 */
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
			String name = verificationString(argumentSplit[1]);
			if(name.equals("")) {
				message = "L'argument donné pour le nom du computer est vide, ce n'est autorisé.";
				gestionMessage(message);
				return;
			}
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
			message = daoComputer.modifier(computer, idCompany);				
			} else {
				message = "La date 'discontinued' donné n'est pas plus récente que celle de 'introduced'.";
			}
		} else {
			message = "Le nombre d'arguments n'est pas le bon.";
		}
		gestionMessage(message);
	}
	
	/**
	 * Méthode prenant l'identifiant d'un computer en String en paramètre et appelant une méthode de daoComputer pour faire la requête de sa suppression.
	 * @param argument L'identifiant d'un computer à supprimer en String.
	 */
	public static void commandeSupprimerComputer(String argument) {
		String message = "";
		if(testArgumentInt(argument)) {
			message = daoComputer.supprimer(Integer.parseInt(argument));
		} else {
			message = "L\'argument donné à la commande n\'est pas un nombre.";
		}
		gestionMessage(message);
	}
	
	/**
	 * Méthode prenant les données d'un computer en String en paramètre et appelant une méthode de daoComputer pour faire la requête de ses informations.
	 * @param argument L'identifiant d'un computer dont les informations sont demandées.
	 */
	public static void commandeDetailsComputer(String argument) {
		String message = "";
		if(testArgumentInt(argument)) {
			message = daoComputer.computerDetails(Integer.parseInt(argument));
		} else {
			message = "L\'argument donné à la commande n\'est pas un nombre.";
		}
		gestionMessage(message);
	}
	
	/**
	 * Méthode permettant d'afficher l'aide pour les commandes.
	 */
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
		message = message + "'List computers' : Affiche la liste entière de computer.\n";
		message = message + "'List companies' : Affiche la liste entière de company.\n";
		message = message + "'List computers page' : affiche les computers par page de " + page.getNombreParPage() + " et ajoute de nouvelles commandes pour changer de place.\n";
		gestionMessage(message);
	}
	
	/**
	 * Méthode utilisé pour commencer l'échange avec l'utilisateur.
	 */
	public static void demandeDeCommande() {
		String message = "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	/**
	 * Méthode appelé lorsqu'il y a trop d'arguments dans un message de l'utilisateur.
	 */
	public static void demandeTropDeSplittage() {
		String message = "Votre message est eronné, vous avez saisi trop de ':'\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	/**
	 * Méthode appelé lorsque l'utilisateur ne donne pas une commande valide.
	 */
	public static void demandeMauvaiseCommande() {
		String message = "Votre message est eronné, vous avez saisi une mauvaise commande.\n";
		message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
		gestionMessage(message);
	}
	
	/**
	 * Méthode utilisé pour vérifier que la partie de Date en String passé en paramètre soit vraiment une partie de Date et valide puis la changé en int.
	 * @param stringDate La partie de Date en String.
	 * @param type String désignant quelle partie de Date est concernée (année/mois/jour).
	 * @return La partie de Date en int.
	 */
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
	
	/**
	 * Méthode vérifiant si le String passé en argument est constitué seulement de chiffre.
	 * @param argument String devant être vérifié.
	 * @return Booléan représentant la vérification.
	 */
	public static Boolean testArgumentInt(String argument) {		
		for(int i = 0; i < argument.length(); i++) {
			char c = argument.charAt(i);
			if(c > '9' || c < '0') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Méthode vérifiant que les antislash dans le String passé en paramètre soient antislasher.
	 * @return Le String avec ses antislash antishlashés.
	 */
	public static String verificationString(String message) {
		message = message.replaceAll("\\\\", "\\\\\\\\");
		return message;
	}
}