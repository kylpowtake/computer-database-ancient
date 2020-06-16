package com.excilys.formation.cdb.ui;

import java.util.Scanner;

public abstract class CLI {
	private static Scanner scanner;
	
	public static void Ecriture(String message) {
		System.out.println(message);
	}
	
	public static String Lecture() {
		scanner = new Scanner(System.in);
		String message = scanner.nextLine();
		return message;
	}
}
