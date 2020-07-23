package com.excilys.formation.cdb.persistence.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionHikari implements ConnectionSQL {
	private HikariConfig config;
	private HikariDataSource dataSource;

	public ConnectionHikari(String pathProperties) {
		config = new HikariConfig(pathProperties);
		dataSource = new HikariDataSource(config);
	}

	@Override
	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public HikariDataSource getDataSource() {
		return this.dataSource;
	}
}
