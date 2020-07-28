package com.excilys.formation.cdb.persistence.hibernate;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.QUser;
import com.excilys.formation.cdb.core.model.User;
import com.excilys.formation.cdb.persistence.UserDao;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public class UserDaoHibernate implements UserDao {
	private static Logger logger = Logging.getLogger();

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<User> all() throws Exception {
		logger.debug("Start of all.");
		QUser user = QUser.user;
		JPAQuery<User> query = new JPAQuery<User>(entityManager);
		return query.from(user).fetch();
	}

	@Override
	public User find(int id) throws Exception {
		logger.debug("Start of find.");
		QUser qUser = QUser.user;
		JPAQuery<User> query = new JPAQuery<User>(entityManager);
		User user = query.from(qUser).where(qUser.id.eq(Long.valueOf(id))).fetchOne();
		return user;
	}
}
