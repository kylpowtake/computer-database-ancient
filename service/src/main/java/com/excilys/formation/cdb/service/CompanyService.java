package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.model.Company;

public interface CompanyService {
	public List<Company> all(String pOrderBy);
	
	public List<Company> allSearch(String rechercheNom, String pOrderBy);
	
	public Company find(int companyId) throws Exception;
	
	public Resultat create(Company company);
	
	public Resultat update(Company company);
	
	public Resultat delete(int id);
	
	public String modificationOrderBy(String orderBy);
}
