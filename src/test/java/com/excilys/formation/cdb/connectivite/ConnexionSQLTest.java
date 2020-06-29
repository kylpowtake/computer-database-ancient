package com.excilys.formation.cdb.connectivite;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;

import com.excilys.formation.cdb.logging.Logging;

import junit.framework.TestCase;

public class ConnexionSQLTest extends TestCase {
	private static Connection connexion;
	
	public static DataSource dataSource;
	
	private static Logger logger = Logging.getLogger();
	
	private static void connexionSQL() {
		  try {
				Context ctx = new InitialContext();
				dataSource = (DataSource)ctx.lookup("java:comp/env/computer-database-db");
			  } catch (NamingException e) {
					System.out.println("erreur lors de la connexion : " + e.getLocalizedMessage());
			  }
		try {
			connexion = dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("erreur lors de la connexion : " + e.getLocalizedMessage());
		}
	}

	public static void finirConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			logger.error("Problème lors de la fin de la connexion avec la base de données : " + e.getLocalizedMessage());
		}
	}
}
