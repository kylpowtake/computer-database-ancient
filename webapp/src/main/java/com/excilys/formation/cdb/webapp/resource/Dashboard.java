package com.excilys.formation.cdb.webapp.resource;

public class Dashboard {
	private final long id;

	private final String testvaleur;
	
	public Dashboard(long id, String page) {
		this.id = id;
		this.testvaleur = page;
	}

	public long getId() {
		return id;
	}

	public String getTestvaleur() {
		return testvaleur;
	}
	
}
