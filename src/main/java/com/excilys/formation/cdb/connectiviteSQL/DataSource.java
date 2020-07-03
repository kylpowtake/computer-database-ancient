package com.excilys.formation.cdb.connectiviteSQL;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataSource {
	private static HikariConfig config = new HikariConfig("/datasource.properties");
	private static HikariDataSource dataSource = new HikariDataSource(config);

	private DataSource() {
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}
