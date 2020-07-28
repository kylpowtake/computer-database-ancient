package com.excilys.formation.cdb.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.model.User;
import com.excilys.formation.cdb.persistence.UserDao;
import com.excilys.formation.cdb.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	public UserServiceImpl(@Qualifier(value = "userDaoHibernate") UserDao userDao){
		this.userDao = userDao;
	}
	
	UserDao userDao;
	
	@Override
	public List<User> all() throws Exception{
		return userDao.all();
	}

	@Override
	public User find(int id) throws Exception {
		return userDao.find(id);
	}

}
