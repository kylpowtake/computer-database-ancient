package com.excilys.formation.cdb.DTO.Mappers;

import com.excilys.formation.cdb.DTO.ComputerDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.BuilderComputer;

public class ComputerDtoMapper {

	private ComputerDtoMapper() {
	}

	public static Computer computerDTOToComputer(ComputerDTO computerDTO) {
		Computer computer = null;
		if (computerDTO == null) {
			return computer;
		}
		BuilderComputer builderComputer = null;
		if (computerDTO.getName() != null) {
			builderComputer = new BuilderComputer(computerDTO.getName());
		} else {
			return null;
		}
		builderComputer.withId(computerDTO.getId());
		if (computerDTO.getIntroduced() != null) {
			builderComputer.introducedThe(computerDTO.getIntroduced());
		}
		if (computerDTO.getDiscontinued() != null) {
			builderComputer.discontinuedThe(computerDTO.getDiscontinued());
		}
		if (computerDTO.getCompany() != null) {
			builderComputer.byCompany(computerDTO.getCompany());
		}
		computer = builderComputer.build();
		return computer;
	}

	public static ComputerDTO computerToComputerDto(Computer computer) {
		ComputerDTO computerDTO = new ComputerDTO();
		if (computer == null) {
			return null;
		}
		computerDTO.setId(computer.getId());
		if (computer.getName() != null) {
			computerDTO.setName(computer.getName());
		}
		if (computer.getIntroduced() != null) {
			computerDTO.setIntroduced(computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
			computerDTO.setDiscontinued(computer.getDiscontinued());
		}
		if (computer.getCompany() != null) {
			computerDTO.setCompany(computer.getCompany());
		}
		return computerDTO;
	}

}
