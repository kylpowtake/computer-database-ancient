package com.excilys.formation.cdb.enumeration;

public enum ComputerOrderBy {
	ID_ASC("computer.id ASC"),
	ID_DESC("computer.id DESC"),
	NAME_ASC("computer.name ASC"),
	NAME_DESC("computer.name DESC"),
	INTRODUCED_ASC("computer.introduced ASC"),
	INTRODUCED_DESC("computer.introduced DESC"),
	DISCONTINUED_ASC("computer.discontinued ASC"),
	DISCONTINUED_DESC("computer.discontinued DESC"),
	COMPANY_ASC("company.name ASC"),
	COMPANY_DESC("company.name DESC");
	
	private String ecriture;
	
	public String getEcriture() {
		return this.ecriture;
	}
	
	private ComputerOrderBy(String string) {
		this.ecriture = string;
	}
	
}
