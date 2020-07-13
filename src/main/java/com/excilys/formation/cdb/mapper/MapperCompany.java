package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;

public class MapperCompany {
	public static Company resultSetToCompany(ResultSet resultSetNextise) throws Exception{
		Company company = null;
		if(resultSetNextise != null && !resultSetNextise.isAfterLast() && !resultSetNextise.isBeforeFirst()) {
			CompanyBuilder buildercompany = new CompanyBuilder(resultSetNextise.getInt("id")).withName(resultSetNextise.getString("name"));
			company = buildercompany.build();
		}
		return company;
	}
	
	public static Company dataToCompany(int id, String name) {
		Company company = new CompanyBuilder(id).withName(name).build();
		return company;
	}
}
