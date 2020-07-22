package com.excilys.formation.cdb.datasource;

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
public class ConnectionNormal implements ConnectionSQL {
	/**
	 * La connexion à la base de données permettant de faire des requêtes.
	 */
	private static Connection connection;

	public static DataSource dataSource;

	private static Logger logger = Logging.getLogger();

	public ConnectionNormal() {
	}

	/**
	 * Méthode initialisant la connexion à la base de données.
	 * 
	 * @exception SQLException S'il y a eu un problème lors de la création de la
	 *                         connexion à la base de données.
	 */
	private static void connection() throws SQLException{
		try {
			Context ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/computer-database-db");
		} catch (NamingException e) {
			logger.error(e.getLocalizedMessage());
		}
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * Méthode renvoyant la connexion et si elle n'existe pas appelle
	 * connexionSQL();
	 * 
	 * @return la connexion à la base de données.
	 */
	@Override
	public Connection getConnection() throws SQLException{
		if (connection == null) {
			try {
				connection();
			} catch (SQLException e) {
				throw e;
			}
		}
		return connection;
	}

	@Override
	public DataSource getDataSource() {
		return null;
	}
}