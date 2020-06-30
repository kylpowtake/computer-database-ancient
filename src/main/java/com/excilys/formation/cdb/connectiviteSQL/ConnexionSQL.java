package com.excilys.formation.cdb.connectiviteSQL;

import java.sql.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;

import com.excilys.formation.cdb.logging.Logging;

/**
 * Classe singleton utilisé pour obtenir la connexion à la base de données.
 * 
 * @author kylian
 *
 */
public class ConnexionSQL {
	/**
	 * La connexion à la base de données permettant de faire des requêtes.
	 */
	private static Connection connexion;

	public static DataSource dataSource;

	private static Logger logger = Logging.getLogger();

	/**
	 * Méthode initialisant la connexion à la base de données.
	 * 
	 * @exception SQLException S'il y a eu un problème lors de la création de la
	 *                         connexion à la base de données.
	 */
	private static void connexionSQL() {
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/computer-database-db");
		} catch (NamingException e) {
			System.out.println("erreur lors de la connexion : " + e.getLocalizedMessage());
		}
		try {
			connexion = dataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("erreur lors de la connexion : " + e.getLocalizedMessage());
		}
	}

	/**
	 * Méthode renvoyant la connexion et si elle n'existe pas appelle
	 * connexionSQL();
	 * 
	 * @return la connexion à la base de données.
	 */
	public static Connection getConnexion() {
		if (connexion == null) {
			connexionSQL();
		}
		return connexion;
	}

	/**
	 * Méthode terminant la connexion à la base de données.
	 */
	public static void finirConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			logger.error(
					"Problème lors de la fin de la connexion avec la base de données : " + e.getLocalizedMessage());
		}
	}
}