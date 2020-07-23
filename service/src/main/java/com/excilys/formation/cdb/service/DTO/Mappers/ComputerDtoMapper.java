package com.excilys.formation.cdb.service.DTO.Mappers;

import java.time.LocalDate;

import com.excilys.formation.cdb.service.DTO.ComputerDto;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.core.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.service.Utility;

public class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	public static Computer computerDTOToComputer(ComputerDto computerDto) {
		Computer computer = null;
		if (computerDto == null) {
			return computer;
		}
		ComputerBuilder builderComputer = null;
		if (Utility.stringIsSomething(computerDto.getName())) {
			builderComputer = new ComputerBuilder(computerDto.getName());
		} else {
			return null;
		}
		if(Utility.stringIsSomething(computerDto.getId())) {
			builderComputer.withId((long) Integer.parseInt(computerDto.getId()));
		}
		if (Utility.stringIsSomething(computerDto.getIntroduced())) {
			builderComputer.introducedThe(LocalDate.parse(computerDto.getIntroduced()));
		}
		if (Utility.stringIsSomething(computerDto.getDiscontinued())) {
			builderComputer.discontinuedThe(LocalDate.parse(computerDto.getDiscontinued()));
		}
		if (computerDto.getCompany() != null) {
			builderComputer.byCompany(CompanyDtoMapper.companyDtoToCompany(computerDto.getCompany()));
		}
		computer = builderComputer.build();
		return computer;
	}

	public static ComputerDto computerToComputerDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();
		if (computer == null) {
			return null;
		}
		computerDto.setId("" + computer.getId());
		if (computer.getName() != null) {
			computerDto.setName(computer.getName());
		}
		if (computer.getIntroduced() != null) {
			computerDto.setIntroduced("" + computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
			computerDto.setDiscontinued("" + computer.getDiscontinued());
		}
		if (computer.getCompany() != null) {
			computerDto.setCompany(CompanyDtoMapper.companyToCompanyDto(computer.getCompany()));
		}
		return computerDto;
	}

}
