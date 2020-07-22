package com.excilys.formation.cdb.persistence.jdbc.row.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.mapper.MapperCompany;
import com.excilys.formation.cdb.model.Company;

public class CompanyRowMapper implements RowMapper<Company>{

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
			return MapperCompany.resultSetToCompany(rs);
		} catch (Exception e) {
			return null;
		}
	}

}
