package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.model.Computer;

public interface ComputerService {

	public List<Computer> some(String pOrderBy) throws Exception;
	
	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception;
	
	public List<Computer> all(String pOrderBy) throws Exception;

	public Resultat create(Computer computer) throws Exception;
	
	public Resultat modify(Computer computerObject) throws Exception;
	
	public Resultat delete(int computerId) throws Exception;
	
	public Computer find(int computerId) throws Exception;
	
	public int nombre();
}
