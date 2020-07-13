package com.excilys.formation.cdb.validation;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.DTO.ComputerDTO;
import com.excilys.formation.cdb.DTO.PageDTO;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.Utility;

@Component
public class Validation {
	
	private static Logger logger = Logging.getLogger();
	
	public CompanyService companyService;
	
	@Autowired
	public Validation(CompanyService companyservice) {
		this.companyService = companyservice;
	}
	
	
	public void validationPage(PageDTO pageDTO) {
		logger.debug("Start of validationPage");
		pageDTO.setNomRecherche(validationSearch(pageDTO.getNomRecherche()));
		pageDTO.setNombreParPage(validationNombreParPage(pageDTO.getNombreParPage()));
		pageDTO.setNumeroPage(validationNumeroPage(pageDTO.getNumeroPage()));
		pageDTO.setOrderBy(validationOrderBy(pageDTO.getOrderBy()));
		logger.debug("End of validationPage");
	}
	
	public void validationComputerDTO(ComputerDTO computerDTO) throws Exception{
		logger.debug("Start of validationComputerDto.");
		validationName(computerDTO.getName());
		validationIntroduced(computerDTO.getIntroduced());
		validationDiscontinued(computerDTO.getDiscontinued());
		validationChronologieDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued());
		validationCompanyId(computerDTO.getCompany().getId());
	}
	
	public void validationIntroduced(String introduced) throws ValidationException {
		logger.debug("Start of validationIntroduced.");
		if (introduced != null && !(introduced.equals("")) && !(introduced.equals("null"))
				&& !MapperDate.validatorStringIsDate(introduced)) {
			throw new ValidationException(
					"La date 'introduced' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	public void validationDiscontinued(String discontinued) throws ValidationException {
		logger.debug("Start of validationDiscontinued.");
		if (discontinued != null && !(discontinued.equals("")) && !(discontinued.equals("null")) && !MapperDate.validatorStringIsDate(discontinued)) {
			throw new ValidationException("La date 'discontinued' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	public void validationChronologieDates(String introduced, String discontinued) throws ValidationException {
		logger.debug("Start of validationChronologie.");
		if (introduced != null && discontinued != null) {
			LocalDate introducedDate = MapperDate.StringToLocalDate(introduced);
			LocalDate discontinuedDate = MapperDate.StringToLocalDate(discontinued);
			if (introducedDate != null && discontinuedDate != null && !(introducedDate.isBefore(discontinuedDate))) {
				throw new ValidationException("La date 'discontinued' est moins récente que la date 'introduced'.");
			}
		}
	}
	
	public void validationCompanyId(String companyId) throws Exception, ValidationException {
		logger.debug("Start of validationCompanyId.");
		if (companyId != null && !(companyId.equals("0")) && !(companyId.equals("")) && !(companyId.equals("null"))
				&& com.excilys.formation.cdb.service.Utility.stringIsInt(companyId)) {
			Company company;
			try {
				logger.debug("Start 1 of validationCompanyId.  :  " + (companyService != null));
				company = (Company) companyService.find(Integer.parseInt(companyId));
			} catch (NumberFormatException e) {
				logger.debug("Start 2 of validationCompanyId.");
				throw new Exception(e.getLocalizedMessage() + " validationCompanyId");
			} catch (Exception e) {
				logger.debug("Start 3 of validationCompanyId.");
				throw new Exception(e.getLocalizedMessage() + " validationCompanyId");
			}
			if (company == null) {
				logger.debug("Start 4 of validationCompanyId.");
				throw new ValidationException("La company selon l'id donné n'existe pas : validationCompanyId");
			}
		}
	}
	
	public void validationName(String name) throws ValidationException {
		logger.debug("Start of validationName.");
		if (name == null || name.equals("")) {
			throw new ValidationException("Le computer doit nécessairement avoir un nom.");
		}
	}
	
	public String validationNombreParPage(String nombreParPage) {
		if (nombreParPage == null || nombreParPage.equals("") || nombreParPage.equals("null")
				|| !(Utility.stringIsInt(nombreParPage))) {
			nombreParPage = "10";
		}
		return nombreParPage;
	}

	public String validationNumeroPage(String numeroPage) {
		if (numeroPage == null || numeroPage.equals("") || numeroPage.equals("null")
				|| !(Utility.stringIsInt(numeroPage))) {
			numeroPage = "";
		}
		return numeroPage;
	}

	public String validationSearch(String nomRecherche) {
		if (nomRecherche == null || nomRecherche.equals("")) {
			nomRecherche = "";
		}
		return nomRecherche;
	}

	public String validationOrderBy(String orderBy) {
		if (orderBy == null || orderBy.equals("") || orderBy.equals("null")
				|| (!orderBy.equals("id") && !orderBy.equals("name") && !orderBy.equals("introduced")
						&& !orderBy.equals("discontinued") && !orderBy.equals("company"))) {
			orderBy = "";
		}
		return orderBy;
	}
}
