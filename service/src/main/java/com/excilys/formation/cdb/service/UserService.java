package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.model.User;

public interface UserService {

	public List<User> all() throws Exception;
	
	public User find(int id) throws Exception;
}