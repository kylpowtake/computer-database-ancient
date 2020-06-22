package com.excilys.formation.cdb.exception;

public class ParametresException extends Exception{
	private static final long serialVersionUID = 1L;

	public ParametresException() {
		System.out.println("Problème avec les paramèetres.");
	}
	
	public ParametresException(String raison) {
		System.out.println("Problème avec les paramètres \n" + raison);
	}
}
