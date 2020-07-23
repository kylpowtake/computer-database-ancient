package com.excilys.formation.cdb.console.cli;

import java.util.Scanner;

/**
 * Classe gérant les entrées / sorties avec l'utilisateur avec la console.
 * @author kylian
 *
 */
public abstract class CLI {
	/**
	 * Le scanner permettant à l'utilisateur de donner des commandes.
	 */
	private static Scanner scanner;
	
	/**
	 * Méthode écrivant un message pour l'utilisateur dans la console.
	 * @param message Le message à afficher.
	 */
	public static void Ecriture(String message) {
		System.out.println(message);
	}
	
	/**
	 * Méthode lisant un message écrit par l'utilisateur dans la console.
	 * @return Le message écrit par l'utilisateur.
	 */
	public static String Lecture() {
		scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		return message;
	}
}
