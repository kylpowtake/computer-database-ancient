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
 * @author kylian
 *
 */
public class ConnexionSQL {
	/**
	 * La connexion à la base de données permettant de faire des requêtes.
	 */
	private static Connection connexion;
	
//	@Resource(name="jdbc/computer-database-db")
	public static DataSource dataSource;
	
//	private static final String FICHIERPROPERTIES = "config.properties";
	
	private static Logger logger = Logging.getLogger();
//	private static String dbUser = "";
//	private static String dbPassword = "";
//	private static String dbURL = "";
//	private static final String DBUSERKEY = "dbUser";
//	private static final String DBPASSWORDKEY = "dbPassword";
//	private static final String DBURLKEY = "dbURL";
	
	/**
	 * Méthode initialisant la connexion à la base de données.
	 * @exception SQLException S'il y a eu un problème lors de la création de la connexion à la base de données.
	 */
	private static void connexionSQL() {
//		readFichierProperties();
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
//		MysqlDataSource mysqlDataSource = new MysqlDataSource();
//		mysqlDataSource.setURL(dbURL);
//		mysqlDataSource.setUser(dbUser);
//		mysqlDataSource.setPassword(dbPassword);
//		try {
//			connexion = mysqlDataSource.getConnection();
//		} catch (SQLException e) {
//			logger.error("Problème lors de la connexion à la base de données : " + e.getLocalizedMessage());
//			System.exit(1);
//		}
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
			logger.error("Problème lors de la fin de la connexion avec la base de données : " + e.getLocalizedMessage());
		}
	}
	
	/**
	 * Méthode lisant le fichier config.properties pour obtenir les informations permettant de se connecter à la base de données.
	 */
//	public static void readFichierProperties() {
//		try {
//			InputStream inputStream = ConnexionSQL.class.getResourceAsStream("/config.properties");
//			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//			Stream<String> stream = bufferedReader.lines();
//			List<String> list = stream.map(s -> s.split("=")).map(ts -> ts[1]).collect(Collectors.toList());
//			inputStreamReader.close();
//			inputStream.close();
//			dbUser = list.get(0);
//			dbPassword = list.get(1);
//			dbURL = list.get(2);			
//		} catch (IOException e) {
//			logger.error("Problème lors de la lecture du fichier config.properties de la base de données : " + e.getLocalizedMessage());
//		}
//	}
}



//			FileReader fileReader = new FileReader(FICHIERPROPERTIES);
//			Properties properties = new Properties();
//			if(fileReader != null){
//				properties.load(fileReader);
//				dbUser = properties.getProperty(DBUSERKEY);
//				dbPassword = properties.getProperty(DBPASSWORDKEY);
//				dbURL = properties.getProperty(DBURLKEY);
//			}
//			fileReader.close();
