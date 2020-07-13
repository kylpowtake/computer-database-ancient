package com.excilys.formation.cdb.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

public interface ConnectionSQL {
	
	public abstract Connection getConnection() throws SQLException;
	
	public abstract DataSource getDataSource();
}
