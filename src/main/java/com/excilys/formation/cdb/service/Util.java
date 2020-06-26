package com.excilys.formation.cdb.service;

import org.slf4j.Logger;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperComputer;
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
	
	public static Logger logger = Logging.getLogger();
	/**
	 * La méthode main, lance l'initialisation des daos et de la page puis lance la communication avec l'utilisateur..
	 * @param args les paramèters utilisés au lancement du programme, ne sont pas pris en compte.
	 */
	public static void main(String[] args) {
		logger.trace("Début du programme.");
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
				case "Quit":
					commandeArretApplication();
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
//		if(page.isPeutAllerNouvellePage()) {
//		String message = "";
//		page.setNumeroPage(page.getNumeroPage()+1);
//		page.setPeutAllerAnciennePage(true);
//		try {
//			message = daoComputer.listerComputersPage();
//		} catch (ParametresException e) {
//			message = e.getLocalizedMessage();
//		}
//		gestionMessagePage(message);
//		} else {
//			String message = "Vous êtes à la fin de la liste des computers, vous ne pouvez allez plus loin.\n";
//			message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
//			gestionMessagePage(message);
//		}
	}
	
	/**
	 * Méthode permettant d'afficher la page précédante de computers.
	 */
	public static void commandeListComputersPrevious() {
//		if(page.isPeutAllerAnciennePage()) {
//		String message = "";
//		page.setNumeroPage(page.getNumeroPage()-1);
//		if(page.getNumeroPage() == 1) {
//			page.setPeutAllerAnciennePage(false);
//		} else {
//			page.setPeutAllerAnciennePage(true);	
//		}
//		try {
//			message = daoComputer.listerComputersPage();
//		} catch (ParametresException e) {
//			message = e.getLocalizedMessage();
//		}
//		gestionMessagePage(message);
//		} else {
//			String message = "Vous êtes au début de la liste des computers, vous ne pouvez allez en arrière.\n";
//			message = message + "Que voulez vous faire ? Si besoin  d'aide faites la commande 'Help'";
//			gestionMessagePage(message);
//		}
	}
	
	/**
	 * Méthode permettant d'affiche rla dernière page de computers.
	 */
	public static void commandeListComputersEnd() {
		String message = "";
		try {
			message = daoComputer.listerComputersEnd();
		} catch (ParametresException e) {
			message = e.getLocalizedMessage();
		}
		gestionMessagePage(message);
	}
	
	/**
	 * Méthode permettant d'afficher la première page de computer.
	 */
	public static void commandeListComputersBeginning() {
//		String message = "";
//		page.setNumeroPage(1);
//		page.setPeutAllerAnciennePage(false);
//		try {
//			message = daoComputer.listerComputersPage();
//		} catch (ParametresException e) {
//			message = e.getLocalizedMessage();
//		}
//		gestionMessagePage(message);
	}
	
	/**
	 * Méthode arrêtant l'application.
	 */
	public static void commandeArretApplication() {
		Logger logger = Logging.getLogger();
		ConnexionSQL.finirConnexion();
		logger.trace("Fin du programme");
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
		try {
			message = daoComputer.listerComputers();
		} catch (ParametresException e) {
			message = e.getLocalizedMessage();
		}
		gestionMessage(message);
	}
	
	/**
	 * Méthode prenant les données d'un computer en String en paramètre et appellant une méthode de daoComputer pour faire la requête de sa création.
	 * @param argument Les données d'un computer à créer en String.
	 */
	public static void commandeCreateComputer(String argument) {
		Computer computer = null;
		String message = "";
		String[] argumentSplit = argument.split("::");
		if(argumentSplit.length == 4) {
			try {
			computer = MapperComputer.stringToComputer(argumentSplit[0], argumentSplit[1], argumentSplit[2], argumentSplit[3]);
			} catch(ParametresException e) {
				gestionMessage(e.getLocalizedMessage());
			}
			if(computer != null) {
				message = daoComputer.ajouter(computer, Integer.parseInt(argumentSplit[3]));
			} else {
				message = "Au moins un des arguments n'est pas valide.";
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
		Computer computer = null;
		String message = "";
		String[] argumentSplit = argument.split("::");
		if(argumentSplit.length == 5) {
			try {
			computer = MapperComputer.stringToComputerAvecId(argumentSplit[0], argumentSplit[1], argumentSplit[2], argumentSplit[3], argumentSplit[4]);
			} catch(ParametresException e) {
				gestionMessage(e.getLocalizedMessage());
			}
			if(computer != null) {
				message = daoComputer.modifier(computer, Integer.parseInt(argumentSplit[4]));
			} else {
				message = "Au moins un des paramètres n'est pas valide.";
			}
			gestionMessage(message);
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
		if(stringIsInt(argument)) {
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
		if(stringIsInt(argument)) {
			try {
				message = daoComputer.computerDetails(Integer.parseInt(argument));
			} catch (NumberFormatException e) {
				message = "L\'argument donné à la commande n\'est pas un nombre.";
			} catch (ParametresException e) {
				message = e.getLocalizedMessage();
			}
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
		message = message + "'Help' : Affiche les différentes commandes et leurs effets.\n";
		message = message + "'Show computer details' : Affiche  les détails de l'ordinateur dont l'indice est donné en argument.\n";
		message = message + "'Delete a computer' : Supprime l'ordinateur dont l'indice est donné en argument.\n";
		message = message + "'Update a computer' : Modifie s'il existe l'ordinateur passé en argument.\n";
		message = message + "'Create a computer' : Créé un computer avec les parametres données.\n";
		message = message + "'List computers' : Affiche la liste entière de computer.\n";
		message = message + "'List companies' : Affiche la liste entière de company.\n";
		message = message + "'List computers page' : affiche les computers par page de " + page.getNombreParPage() + " et ajoute de nouvelles commandes pour changer de place.\n";
		message = message + "'Quit' : Ferme l'application.";
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
	 * Méthode vérifiant si le String passé en argument est constitué seulement de chiffre.
	 * @param argument String devant être vérifié.
	 * @return Booléan représentant la vérification.
	 */
	public static Boolean stringIsInt(String argument) {	
		if(argument != null && argument.length() != 0) {
		for(int i = 0; i < argument.length(); i++) {
			char c = argument.charAt(i);
			if(c > '9' || c < '0') {
				return false;
			}
		}
		return true;
		}
		return false;
	}
}