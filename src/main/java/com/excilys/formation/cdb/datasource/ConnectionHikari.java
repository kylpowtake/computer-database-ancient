package com.excilys.formation.cdb.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionHikari implements ConnectionSQL {
	private static HikariConfig config;
	private static HikariDataSource dataSource;

	public ConnectionHikari() {
		 config = new HikariConfig("/datasource.properties");
		 dataSource = new HikariDataSource(config);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}