package com.excilys.formation.cdb.enumeration;

public enum Resultat {
	REUSSI("Réussi"),
	ECHOUE("échoué");
	
	private String ecriture;
	
	public String getEcriture() {
		return this.ecriture;
	}
	
	private Resultat(String string) {
		this.ecriture = string;
	}
	
}

