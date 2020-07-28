package com.excilys.formation.cdb.persistence.jdbc.row.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.binding.mapper.MapperComputer;
import com.excilys.formation.cdb.core.model.Computer;

public class ComputerRowMapper implements RowMapper<Computer> {

	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
			return MapperComputer.mapResultSetToComputer(rs);
		} catch (Exception e) {
			return null;
		}
	}
}
