package com.excilys.formation.cdb.core.exception;

public class DateException extends Exception{
	private static final long serialVersionUID = 1L;
	
	private static String raison = "";
	
	@Override
	public String getLocalizedMessage() {
		return DateException.raison;
	}

	public DateException() {
		DateException.raison = "Exception en lien avec une date.";
	}
	
	public DateException(String raison) {
		DateException.raison = raison;
	}

}
