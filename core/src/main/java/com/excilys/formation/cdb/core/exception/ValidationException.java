package com.excilys.formation.cdb.core.exception;

@SuppressWarnings("serial")
public class ValidationException extends Exception{
	private String localizedMessage = "";
	
	@Override
	public String getLocalizedMessage() {
		// TODO Auto-generated method stub
		return localizedMessage;
	}

	public ValidationException(String arg0) {
		this.localizedMessage = arg0;
	}
	
}
