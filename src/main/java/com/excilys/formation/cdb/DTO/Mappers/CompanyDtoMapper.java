package com.excilys.formation.cdb.DTO.Mappers;

import com.excilys.formation.cdb.DTO.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.BuilderCompany;

public class CompanyDtoMapper {
	private CompanyDtoMapper() {
	}

	public static Company companyDtoToCompany(CompanyDTO companyDto) {
		Company company = null;
		if(companyDto == null) {
			return null;
		}
		BuilderCompany builderCompany = new Company.BuilderCompany(Integer.parseInt(companyDto.getId()));
		if(companyDto.getName() != null) {
			builderCompany.withName(companyDto.getName());
		}
		company = builderCompany.build();
		return company;
	}
	
	public static CompanyDTO companyToCompanyDto(Company company) {
		if(company == null) {
			return null;
		}
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId("" + company.getId());
		if(company.getName() != null) {
			companyDTO.setName(company.getName());
		}
		return companyDTO;
	}
}
