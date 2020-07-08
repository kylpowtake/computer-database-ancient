package com.excilys.formation.cdb.datasource;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionSQL {
	
	public abstract Connection getConnection() throws SQLException;
}
