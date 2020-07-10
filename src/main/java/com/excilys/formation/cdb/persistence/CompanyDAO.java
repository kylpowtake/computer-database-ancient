package com.excilys.formation.cdb.persistence;

import java.util.List;


import com.excilys.formation.cdb.enumeration.Resultat;

import com.excilys.formation.cdb.model.Company;

/**
 * Classe comprenant les méthodes pour faire des requêtes à la base de données à
 * propos de la classe Companie.
 * 
 * @author kylian
 * @see Company
 *
 */
public interface CompanyDAO {
	
	public List<Company> all(String pOrderBy);
	
	public List<Company> allSearch(String rechercheNom, String pOrderBy);

	public Company find(int companyId) throws Exception;
	
	public Resultat delete(int id);
	
	public String modificationOrderBy(String orderBy);
	
	
}
