package com.excilys.formation.cdb.persistence;

import java.util.List;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.model.User;

public interface UserDao {

	static final String REQUETEALL = "SELECT user.id, user.pseudo, user.password from user";

	static final String REQUETEUSER = "SELECT user.id, user.pseudo, user.password from user where user.id = :id";

	static final String REQUETEDELETE = "DELETE FROM user WHERE user.id = :id";

	static final String REQUETEUPDATE = "update user set user.pseudo = :pseudo, user.password = :password where user.id = :id;";

	static final String REQUETECREATE = "INSERT INTO user (pseudo, password) VALUES (:pseudo, :password);";

	
	public List<User> all() throws Exception;

	public User find(int id) throws Exception;
}
