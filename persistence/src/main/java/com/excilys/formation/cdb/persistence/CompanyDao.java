package com.excilys.formation.cdb.persistence;

import java.util.List;


import com.excilys.formation.cdb.core.enumeration.Resultat;

import com.excilys.formation.cdb.core.model.Company;

/**
 * Classe comprenant les méthodes pour faire des requêtes à la base de données à
 * propos de la classe Companie.
 * 
 * @author kylian
 * @see Company
 *
 */
public interface CompanyDao {
	static final String REQUETELISTECOMPANIES = "SELECT company.id, company.name FROM company ORDER BY :orderBy;";
	
	static final String REQUETELISTECOMPANIESSEARCH = "SELECT company.id, company.name FROM company WHERE company.name = :name ORDER BY :orderBy;";
	
	static final String REQUETEFINDBYID = "SELECT company.id, company.name FROM company WHERE company.id = :id;";

	static final String REQUETEDELETECOMPUTERS = "DELETE FROM computer WHERE computer.companyId = :id";

	static final String REQUETEDELETECOMPANY = "DELETE FROM company WHERE company.id = :id";

	public List<Company> all(String pOrderBy);
	
	public List<Company> allSearch(String rechercheNom, String pOrderBy);

	public Company find(int companyId) throws Exception;
	
	public Resultat delete(int id);
	
	public Resultat update(Company company);
	
	public Resultat create(Company company);
		
	public static String modificationOrderBy(String orderBy) {
		String message = "";
		String[] orderBies = orderBy.split(" ");
		if (orderBies.length == 2) {
			message = " " + orderBies[1];
		}
		switch (orderBies[0]) {
		case "id":
			return "company.id" + message;
		case "name":
			return "company.name" + message;
		default:
			return "company.id" + message;
		}
	}
}
