package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.BuilderCompany;

public class MapperCompany {
	public static Company resultSetToCompany(ResultSet resultSetNextise) throws Exception{
		Company company = null;
		if(resultSetNextise != null) {
			BuilderCompany buildercompany = new BuilderCompany(resultSetNextise.getInt("id")).withName(resultSetNextise.getString("name"));
			company = buildercompany.build();
		} else {
			throw new Exception("Passage d'un resultSet de company null au mappeur. Méthode : resultSetToCompany");
		}
		return company;
	}
}
