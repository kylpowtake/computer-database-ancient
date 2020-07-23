package com.excilys.formation.cdb.service.validators;

import java.time.LocalDate;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.formation.cdb.service.DTO.ComputerDto;

public class ComputerDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ComputerDto.class.equals(clazz);

	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "errors.name");
		ComputerDto computerDto = (ComputerDto) target;
		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;
		if(computerDto.getIntroduced() != null && computerDto.getIntroduced().matches("\\d{4}-\\d{2}-\\d{2}")) {
			introducedDate = LocalDate.parse(computerDto.getIntroduced());
		} else if(computerDto.getIntroduced() !=  null && !computerDto.getIntroduced().isEmpty()){
			errors.rejectValue("introduced", "errors.introduced");
		}
		if(computerDto.getDiscontinued() != null && computerDto.getDiscontinued().matches("\\d{4}-\\d{2}-\\d{2}")) {
			discontinuedDate = LocalDate.parse(computerDto.getDiscontinued());
		} else if(computerDto.getDiscontinued() !=  null && !computerDto.getDiscontinued().isEmpty()){
			errors.rejectValue("discontinued", "errors.discontinued");
		}
		if(introducedDate != null && discontinuedDate != null && introducedDate.isAfter(discontinuedDate)) {
			errors.rejectValue("", "errors.chronology");
		}
	}

}
