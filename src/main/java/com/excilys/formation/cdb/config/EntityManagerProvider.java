package com.excilys.formation.cdb.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {
	private static EntityManager entityManager;

	public static EntityManager getEntityManager() {
		if (entityManager == null) {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("computer-database-db");
			entityManager = entityManagerFactory.createEntityManager();
			return entityManager;
		} else {
			return entityManager;
		}
	}
}
