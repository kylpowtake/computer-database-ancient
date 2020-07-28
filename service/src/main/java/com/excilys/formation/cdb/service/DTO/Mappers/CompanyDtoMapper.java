package com.excilys.formation.cdb.service.DTO.Mappers;

import com.excilys.formation.cdb.service.DTO.CompanyDto;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.core.model.Company.CompanyBuilder;

public class CompanyDtoMapper {
	private CompanyDtoMapper() {
	}

	public static Company companyDtoToCompany(CompanyDto companyDto) {
		Company company = null;
		if(companyDto == null) {
			return null;
		}
		CompanyBuilder builderCompany = new Company.CompanyBuilder((long) Integer.parseInt(companyDto.getId()));
		if(companyDto.getName() != null) {
			builderCompany.withName(companyDto.getName());
		}
		company = builderCompany.build();
		return company;
	}
	
	public static CompanyDto companyToCompanyDto(Company company) {
		if(company == null) {
			return null;
		}
		CompanyDto companyDTO = new CompanyDto();
		companyDTO.setId("" + company.getId());
		if(company.getName() != null) {
			companyDTO.setName(company.getName());
		}
		return companyDTO;
	}
	
	public static Company stringToCompany(String companyId, String companyName) {
		return companyDtoToCompany(new CompanyDto(companyId, companyName));
	}
}
