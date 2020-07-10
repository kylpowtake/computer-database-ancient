package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.BuilderCompany;

public class MapperCompany {
	public static Company resultSetToCompany(ResultSet resultSetNextise) throws Exception{
		Company company = null;
		if(resultSetNextise != null && !resultSetNextise.isAfterLast() && !resultSetNextise.isBeforeFirst()) {
			BuilderCompany buildercompany = new BuilderCompany(resultSetNextise.getInt("id")).withName(resultSetNextise.getString("name"));
			company = buildercompany.build();
		}
		return company;
	}
	
	public static Company dataToCompany(int id, String name) {
		Company company = new BuilderCompany(id).withName(name).build();
		return company;
	}
}
