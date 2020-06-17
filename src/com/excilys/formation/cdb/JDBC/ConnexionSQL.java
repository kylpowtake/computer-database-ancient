package com.excilys.formation.cdb.JDBC;

import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Classe singleton utilisé pour obtenir la connexion à la base de données.
 * @author kylian
 *
 */
public class ConnexionSQL {
	/**
	 * La connexion à la base de données permettant de faire des requêtes.
	 */
	private static Connection connexion;
	
	/**
	 * Méthode initialisant la connexion à la base de données.
	 * @exception SQLException S'il y a eu un problème lors de la création de la connexion à la base de données.
	 */
	private static void connexionSQL() {
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL("jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC");
		mysqlDataSource.setUser("admincdb");
		mysqlDataSource.setPassword("qwerty1234");
		try {
			connexion = mysqlDataSource.getConnection();
		} catch (SQLException e) {
			System.out.println("Problème lors du lancement de la connection avec mySQL." + e.getLocalizedMessage());
			System.exit(1);
		}
	}
	
	/**
	 * Méthode renvoyant la connexion et si elle n'existe pas appelle connexionSQL();
	 * @return la connexion à la base de données.
	 */
	public static Connection getConnexion() {
		if(connexion == null) {
			connexionSQL();
		}
		return connexion;
	}
}
