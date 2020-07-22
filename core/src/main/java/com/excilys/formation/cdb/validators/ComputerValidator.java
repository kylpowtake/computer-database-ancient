package com.excilys.formation.cdb.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.formation.cdb.DTO.ComputerDto;
import com.excilys.formation.cdb.model.Computer;

public class ComputerValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Computer.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	}
}