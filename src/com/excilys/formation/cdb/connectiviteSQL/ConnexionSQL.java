package com.excilys.formation.cdb.connectiviteSQL;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

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
	
	private static final String FICHIERPROPERTIES = "config.properties";
	
	private static String dbUser = "";
	private static String dbPassword = "";
	private static String dbURL = "";
	private static final String DBUSERKEY = "dbUser";
	private static final String DBPASSWORDKEY = "dbPassword";
	private static final String DBURLKEY = "dbURL";
	
	/**
	 * Méthode initialisant la connexion à la base de données.
	 * @exception SQLException S'il y a eu un problème lors de la création de la connexion à la base de données.
	 */
	private static void connexionSQL() {
		readFichierProperties();
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL(dbURL);
		mysqlDataSource.setUser(dbUser);
		mysqlDataSource.setPassword(dbPassword);
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
	
	/**
	 * Méthode terminant la connexion à la base de données.
	 */
	public static void finirConnexion() {
		try {
			connexion.close();
		} catch (SQLException e) {
			System.out.println("Une erreur s'est produite lors de la tentative de fermeture de la connexion à la base de données : " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Méthode lisant le fichier config.properties pour obtenir les informations permettant de se connecter à la base de données.
	 */
	public static void readFichierProperties() {
		try {
			FileReader fileReader = new FileReader(FICHIERPROPERTIES);
			Properties properties = new Properties();
			if(fileReader != null){
				properties.load(fileReader);
				dbUser = properties.getProperty(DBUSERKEY);
				dbPassword = properties.getProperty(DBPASSWORDKEY);
				dbURL = properties.getProperty(DBURLKEY);
			}
			fileReader.close();
		} catch (IOException e) {
			System.out.println("Erreur lors de la lectures des propriétés de la connexion à la base de données : " + e.getLocalizedMessage());
		}
	}
}
