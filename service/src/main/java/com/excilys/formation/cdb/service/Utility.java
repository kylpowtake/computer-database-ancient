package com.excilys.formation.cdb.service;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.persistence.CompanyDao;
import com.excilys.formation.cdb.persistence.normal.ComputerDaoNormal;

/**
 * Classe s'occupant de la gestion des messages de l'utilisateur et appelant les méthodes nécessaires pour y répondre.
 * @author kylian
 * @see ComputerDaoNormal
 * @see CompanyDao
 * @see Page
 */
public class Utility {
	
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
	
	public static boolean stringIsSomething(String string) {
		if(string != null && !string.equals("")) {
			return true;
		} else {
			return false;
		}
	}
}