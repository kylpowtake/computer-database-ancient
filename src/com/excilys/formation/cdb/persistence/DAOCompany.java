package com.excilys.formation.cdb.persistence;

import java.util.List;

import com.excilys.formation.cdb.model.Company;

public class DAOCompany {
	private DAOCompany daoCompany;
	
	private DAOCompany() {}
	
	public DAOCompany getDAOCompany() {
		if(daoCompany == null) {
			return new DAOCompany();
		} else {
		return daoCompany;
		}
	}
	public List<Company> Lister(){
		// Code pour obtenir la liste de company de la base de données.
		return null;
	}
}
