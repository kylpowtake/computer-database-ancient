package com.excilys.formation.cdb.persistence.datasource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public interface ConnectionSQL {
	
	public abstract Connection getConnection() throws SQLException;
	
	public abstract DataSource getDataSource();
}
