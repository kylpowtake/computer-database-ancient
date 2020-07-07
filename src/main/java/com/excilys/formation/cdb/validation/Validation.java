package com.excilys.formation.cdb.validation;

import java.time.LocalDate;

import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

public class Validation {
	
	private static Validation validation = null;
	
	public static Validation getValidation() {
		if (validation == null) {
			return new Validation();
		} else {
			return validation;
		}
	}
	
	public static void validationIntroduced(String introduced) throws ValidationException {
		if (introduced != null && !(introduced.equals("")) && !(introduced.equals("null"))
				&& !MapperDate.validatorStringIsDate(introduced)) {
			throw new ValidationException(
					"La date 'introduced' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	public static void validationDiscontinued(String discontinued) throws ValidationException {
		if (discontinued != null && !(discontinued.equals("")) && !(discontinued.equals("null")) && !MapperDate.validatorStringIsDate(discontinued)) {
			throw new ValidationException("La date 'discontinued' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	public static void validationChronologieDates(String introduced, String discontinued) throws ValidationException {
		if (introduced != null && discontinued != null) {
			LocalDate introducedDate = MapperDate.StringToLocalDate(introduced);
			LocalDate discontinuedDate = MapperDate.StringToLocalDate(discontinued);
			if (introducedDate != null && discontinuedDate != null && introducedDate.isAfter(discontinuedDate)) {
				throw new ValidationException("La date 'discontinued' est moins récente que la date 'introduced'.");
			}
		}
	}
	
	public static void validationCompanyId(String companyId) throws Exception, ValidationException {
		if (companyId != null && !(companyId.equals("0")) && !(companyId.equals("")) && !(companyId.equals("null"))
				&& com.excilys.formation.cdb.service.Util.stringIsInt(companyId)) {
			Company company;
			try {
				company = (Company) CompanyDAO.getDAOCompany().find(Integer.parseInt(companyId));
			} catch (NumberFormatException e) {
				throw new Exception(e.getLocalizedMessage() + " validationCompanyId");
			} catch (Exception e) {
				throw new Exception(e.getLocalizedMessage() + " validationCompanyId");
			}
			if (company == null) {
				throw new ValidationException("La company selon l'id donné n'existe pas : validationCompanyId");
			}
		}
	}
	
	public static void validationName(String name) throws ValidationException {
		if (name == null || name.equals("")) {
			throw new ValidationException("Le computer doit nécessairement avoir un nom.");
		}
	}
}
