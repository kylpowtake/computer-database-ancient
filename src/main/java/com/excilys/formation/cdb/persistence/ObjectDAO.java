package com.excilys.formation.cdb.persistence;

import java.util.List;

import com.excilys.formation.cdb.enumeration.Resultat;

public interface ObjectDAO<T> {
	
	public List<T> all(String orderBy) throws Exception;
	
	public List<T> allSearch(String orderby, String search) throws Exception;
	
	public List<T> some(String orderBy) throws Exception;
	
	public List<T> someSearch(String orderBy, String search) throws Exception;
	
	public Resultat delete(int id) throws Exception;
	
	public Resultat modify(T object) throws Exception;
	
	public Resultat create(T object) throws Exception;
	
	public T find(int id) throws Exception;
}
