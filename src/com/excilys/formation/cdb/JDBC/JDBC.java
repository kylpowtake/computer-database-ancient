package com.excilys.formation.cdb.JDBC;

import java.sql.*;
import com.mysql.cj.jdbc.MysqlDataSource;

public class JDBC {
	public static Connection con;
	
	public static void connexionSQL() {
		MysqlDataSource mysqlDataSource = new MysqlDataSource();
		mysqlDataSource.setURL("jdbc:mysql://localhost:3306/computer-database-db?serverTimezone=UTC");
		mysqlDataSource.setUser("admincdb");
		mysqlDataSource.setPassword("qwerty1234");
		try {
			con = mysqlDataSource.getConnection();
		} catch (SQLException e1) {
			System.out.println("Problème lors du lancement de la connection avec mySQL." + e1.getLocalizedMessage());
			System.exit(1);
		}
	}
}
