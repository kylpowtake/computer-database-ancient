package com.excilys.formation.cdb.service.validation;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.service.DTO.CompanyDto;
import com.excilys.formation.cdb.service.DTO.ComputerDto;
import com.excilys.formation.cdb.service.DTO.PageDTO;
import com.excilys.formation.cdb.core.exception.ValidationException;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.binding.mapper.MapperDate;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.Utility;

@Component
public class Validation {
	private static final String CHAMP_NAME = "computerName";
	private static final String CHAMP_INTRODUCED = "introduced";
	private static final String CHAMP_DISCONTINUED = "discontinued";
	private static final String CHAMP_COMPANY_ID = "companyId";
	private static final String CHAMP_CHRONOLOGY = "chronology";

	private static Logger logger = Logging.getLogger();

	public CompanyService companyService;

	@Autowired
	public Validation(CompanyService companyservice) {
		this.companyService = companyservice;
	}

	public boolean validationPage(PageDTO pageDTO) {
		logger.debug("Start of validationPage");
		if (validationSearch(pageDTO.getNomRecherche()) && validationNombreParPage(pageDTO.getNombreParPage())
				&& validationNumeroPage(pageDTO.getNumeroPage()) && validationOrderBy(pageDTO.getOrderBy())) {
			logger.debug("End of validationPage : true.");
			return true;
		} else {
			logger.debug("End of validationPage : false.");
			return false;
		}
	}

	public void validationComputerDTO(ComputerDto computerDTO, Map<String, String> erreurs) {
		logger.debug("Start of validationComputerDto.");
		validationName(computerDTO.getName(), erreurs);
		validationIntroduced(computerDTO.getIntroduced(), erreurs);
		validationDiscontinued(computerDTO.getDiscontinued(), erreurs);
		validationChronologieDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued(), erreurs);
		validationCompany(computerDTO.getCompany(), erreurs);
	}

	public void validationName(String name, Map<String, String> erreurs) {
		logger.debug("Start of validationName.");
		if (name == null || name.equals("")) {
			erreurs.put(CHAMP_NAME,
					new ValidationException("Le computer doit nécessairement avoir un nom.").getLocalizedMessage());
		}
	}

	public void validationIntroduced(String introduced, Map<String, String> erreurs) {
		logger.debug("Start of validationIntroduced.");
		if (introduced != null && !(introduced.equals("")) && !(introduced.equals("null"))
				&& !MapperDate.validatorStringIsDate(introduced)) {
			erreurs.put(CHAMP_INTRODUCED,
					new ValidationException(
							"La date 'introduced' n'est pas constitué selon le format d'une date : YYYY-MM-JJ")
									.getLocalizedMessage());
		}
	}

	public void validationDiscontinued(String discontinued, Map<String, String> erreurs) {
		logger.debug("Start of validationDiscontinued.");
		if (discontinued != null && !(discontinued.equals("")) && !(discontinued.equals("null"))
				&& !MapperDate.validatorStringIsDate(discontinued)) {
			erreurs.put(CHAMP_DISCONTINUED,
					new ValidationException(
							"La date 'discontinued' n'est pas constitué selon le format d'une date : YYYY-MM-JJ")
									.getLocalizedMessage());
		}
	}

	public void validationChronologieDates(String introduced, String discontinued, Map<String, String> erreurs) {
		logger.debug("Start of validationChronologie.");
		if (introduced != null && discontinued != null) {
			LocalDate introducedDate = MapperDate.StringToLocalDate(introduced);
			LocalDate discontinuedDate = MapperDate.StringToLocalDate(discontinued);
			if (introducedDate != null && discontinuedDate != null && !(introducedDate.isBefore(discontinuedDate))) {
				erreurs.put(CHAMP_CHRONOLOGY,
						new ValidationException("La date 'discontinued' est moins récente que la date 'introduced'.")
								.getLocalizedMessage());
			}
		}
	}

	public void validationCompany(CompanyDto companyDto, Map<String, String> erreurs) {
		logger.debug("Start of validationCompanyId.");
		if (companyDto.getId() != null && !(companyDto.getId().equals("0")) && !(companyDto.getId().equals("")) && !(companyDto.getId().equals("null"))
				&& com.excilys.formation.cdb.service.Utility.stringIsInt(companyDto.getId())) {
			Company company = null;
			try {
				company = (Company) companyService.find(Integer.parseInt(companyDto.getId()));
			} catch (NumberFormatException e) {
				erreurs.put(CHAMP_COMPANY_ID, new ValidationException(e.getLocalizedMessage() + " validationCompanyId")
						.getLocalizedMessage());
			} catch (Exception e) {
				erreurs.put(CHAMP_COMPANY_ID, new ValidationException(e.getLocalizedMessage() + " validationCompanyId")
						.getLocalizedMessage());
			}
			if (company == null) {
				erreurs.put(CHAMP_COMPANY_ID,
						new ValidationException("La company selon l'id donné n'existe pas : validationCompanyId")
								.getLocalizedMessage());
			}
		}
	}

	public boolean validationNombreParPage(String nombreParPage) {
		if (nombreParPage == null) {
			return true;
		}
		if (nombreParPage.equals("") || nombreParPage.equals("null") || !(Utility.stringIsInt(nombreParPage))) {
			return false;
		}
		return true;
	}

	public boolean validationNumeroPage(String numeroPage) {
		if (numeroPage == null) {
			return true;
		}
		if (numeroPage.equals("") || numeroPage.equals("null") || !(Utility.stringIsInt(numeroPage))) {
			return false;
		}
		return true;
	}

	public boolean validationSearch(String nomRecherche) {
		if (nomRecherche == null) {
			return true;
		}
		if (nomRecherche.equals("") || nomRecherche.equals("null")) {
			return false;
		}
		return true;
	}

	public boolean validationOrderBy(String orderBy) {
		if (orderBy == null) {
			return true;
		}
		if (orderBy.equals("") || orderBy.equals("null") || (!orderBy.equals("id") && !orderBy.equals("name")
				&& !orderBy.equals("introduced") && !orderBy.equals("discontinued") && !orderBy.equals("company.name"))) {
			return false;
		}
		return true;
	}
}
