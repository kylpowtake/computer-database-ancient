package com.excilys.formation.cdb.enumeration;

import com.excilys.formation.cdb.model.QComputer;
import com.querydsl.core.types.OrderSpecifier;

public enum ComputerOrderBy {
	ID_ASC("computer.id ASC"), ID_DESC("computer.id DESC"), NAME_ASC("computer.name ASC"),
	NAME_DESC("computer.name DESC"), INTRODUCED_ASC("computer.introduced ASC"),
	INTRODUCED_DESC("computer.introduced DESC"), DISCONTINUED_ASC("computer.discontinued ASC"),
	DISCONTINUED_DESC("computer.discontinued DESC"), COMPANY_ASC("company.name ASC"), COMPANY_DESC("company.name DESC");

	private String ecriture;

	public String getEcriture() {
		return this.ecriture;
	}

	private ComputerOrderBy(String string) {
		this.ecriture = string;
	}

	public static OrderSpecifier<?> getOrder(String string) {
		switch(string) {
		case "id asc":
			return QComputer.computer.id.asc();
		case "id desc":
			return QComputer.computer.id.desc();
		case "name asc":
			return QComputer.computer.name.asc();
		case "name desc":
			return QComputer.computer.name.desc();
		case "introduced asc":
			return QComputer.computer.introduced.asc();
		case "introduced desc":
			return QComputer.computer.introduced.desc();
		case "discontinued asc":
			return QComputer.computer.discontinued.asc();
		case "discontinued desc":
			return QComputer.computer.discontinued.desc();
		case "company.name asc":
			return QComputer.computer.company.name.asc();
		case "company.id desc":
			return QComputer.computer.company.name.desc();
		default:
			return QComputer.computer.id.asc();
		}
	}
}
