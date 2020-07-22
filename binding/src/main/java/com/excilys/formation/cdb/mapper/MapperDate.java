package com.excilys.formation.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MapperDate {
	
	
	public static LocalDate StringToLocalDate(String dateString) {
		if(validatorStringIsDate(dateString)) {
			return LocalDate.parse(dateString);
		}
		return null;
	}
	
	public static boolean validatorStringIsDate(String dateString) {
		try {
			LocalDate.parse(dateString);
		}catch(DateTimeParseException e) {
			return false;
		}
		return true;
	}
}
