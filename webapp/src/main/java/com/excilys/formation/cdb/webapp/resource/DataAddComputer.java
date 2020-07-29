package com.excilys.formation.cdb.webapp.resource;

import java.util.List;

import org.springframework.validation.BindingResult;

import com.excilys.formation.cdb.service.DTO.CompanyDto;
import com.excilys.formation.cdb.service.DTO.ComputerDto;

public class DataAddComputer {
	private final ComputerDto computerDto;
	private final List<CompanyDto> companiesList;
	private final BindingResult result;
	
	public DataAddComputer(ComputerDto computerDto, List<CompanyDto> companiesList, BindingResult result) {
		this.computerDto = computerDto;
		this.companiesList = companiesList;
		this.result = result;
	}

	public ComputerDto getComputerDto() {
		return computerDto;
	}

	public List<CompanyDto> getCompaniesList() {
		return companiesList;
	}

	public BindingResult getResult() {
		return result;
	}
}
