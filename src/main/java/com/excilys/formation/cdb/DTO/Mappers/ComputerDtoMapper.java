package com.excilys.formation.cdb.DTO.Mappers;

import java.time.LocalDate;

import com.excilys.formation.cdb.DTO.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.service.Utility;

public class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	public static Computer computerDTOToComputer(ComputerDTO computerDTO) {
		Computer computer = null;
		if (computerDTO == null) {
			return computer;
		}
		ComputerBuilder builderComputer = null;
		if (Utility.stringIsSomething(computerDTO.getName())) {
			builderComputer = new ComputerBuilder(computerDTO.getName());
		} else {
			return null;
		}
		if(Utility.stringIsSomething(computerDTO.getId())) {
			builderComputer.withId(Integer.parseInt(computerDTO.getId()));
		}
		if (Utility.stringIsSomething(computerDTO.getIntroduced())) {
			builderComputer.introducedThe(LocalDate.parse(computerDTO.getIntroduced()));
		}
		if (Utility.stringIsSomething(computerDTO.getDiscontinued())) {
			builderComputer.discontinuedThe(LocalDate.parse(computerDTO.getDiscontinued()));
		}
		if (computerDTO.getCompany() != null) {
			builderComputer.byCompany(CompanyDtoMapper.companyDtoToCompany(computerDTO.getCompany()));
		}
		computer = builderComputer.build();
		return computer;
	}

	public static ComputerDTO computerToComputerDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		if (computer == null) {
			return null;
		}
		computerDTO.setId("" + computer.getId());
		if (computer.getName() != null) {
			computerDTO.setName(computer.getName());
		}
		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced("" + computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued("" + computer.getDiscontinued());
		}
		if (computer.getCompany() != null) {
			computerDTO.setCompany(CompanyDtoMapper.companyToCompanyDto(computer.getCompany()));
		}
		return computerDTO;
	}

}
