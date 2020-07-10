package com.excilys.formation.cdb.validation;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.DTO.ComputerDTO;
import com.excilys.formation.cdb.DTO.PageDTO;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.Utility;

public class Validation {
	
	private static Logger logger = Logging.getLogger();
	
	@Autowired
	public static CompanyService companyService;
	
	private static Validation validation = null;
	
	public static Validation getValidation() {
		if (validation == null) {
			return new Validation();
		} else {
			return validation;
		}
	}
	
	public static void validationPage(PageDTO pageDTO) {
		logger.debug("Start of validationPage");
		pageDTO.setNomRecherche(validationSearch(pageDTO.getNomRecherche()));
		pageDTO.setNombreParPage(validationNombreParPage(pageDTO.getNombreParPage()));
		pageDTO.setNumeroPage(validationNumeroPage(pageDTO.getNumeroPage()));
		pageDTO.setOrderBy(validationOrderBy(pageDTO.getOrderBy()));
		logger.debug("End of validationPage");
	}
	
	public static void validationComputerDTO(ComputerDTO computerDTO) throws Exception{
		validationName(computerDTO.getName());
		validationIntroduced(computerDTO.getIntroduced());
		validationDiscontinued(computerDTO.getDiscontinued());
		validationCompanyId(computerDTO.getCompany().getId());
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
			if (introducedDate != null && discontinuedDate != null && !(introducedDate.isBefore(discontinuedDate))) {
				throw new ValidationException("La date 'discontinued' est moins récente que la date 'introduced'.");
			}
		}
	}
	
	public static void validationCompanyId(String companyId) throws Exception, ValidationException {
		if (companyId != null && !(companyId.equals("0")) && !(companyId.equals("")) && !(companyId.equals("null"))
				&& com.excilys.formation.cdb.service.Utility.stringIsInt(companyId)) {
			Company company;
			try {
				company = (Company) companyService.find(Integer.parseInt(companyId));
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
	
	public static String validationNombreParPage(String nombreParPage) {
		if (nombreParPage == null || nombreParPage.equals("") || nombreParPage.equals("null")
				|| !(Utility.stringIsInt(nombreParPage))) {
			nombreParPage = "10";
		}
		return nombreParPage;
	}

	public static String validationNumeroPage(String numeroPage) {
		if (numeroPage == null || numeroPage.equals("") || numeroPage.equals("null")
				|| !(Utility.stringIsInt(numeroPage))) {
			numeroPage = "";
		}
		return numeroPage;
	}

	public static String validationSearch(String nomRecherche) {
		if (nomRecherche == null || nomRecherche.equals("")) {
			nomRecherche = "";
		}
		return nomRecherche;
	}

	public static String validationOrderBy(String orderBy) {
		if (orderBy == null || orderBy.equals("") || orderBy.equals("null")
				|| (!orderBy.equals("id") && !orderBy.equals("name") && !orderBy.equals("introduced")
						&& !orderBy.equals("discontinued") && !orderBy.equals("company"))) {
			orderBy = "";
		}
		return orderBy;
	}
}
