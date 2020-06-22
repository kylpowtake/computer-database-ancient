package com.excilys.formation.cdb.exception;

public class MauvaisFonctionnementException extends Exception{
	private static final long serialVersionUID = 1L;
	
	public MauvaisFonctionnementException() {
		System.out.println("Il y a eu un mauvais fonctionnemnt entre deux fonctions.");
	}
	
	public MauvaisFonctionnementException(String raison) {
		System.out.println("Il y a eu un mauvais fonctionnemnt entre deux fonctions. \n" + raison);
	}

}
