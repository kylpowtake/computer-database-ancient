package com.excilys.formation.cdb.validation;

import java.time.LocalDate;

import com.excilys.formation.cdb.DAO.CompanyDAO;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.service.Util;

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
			if (introducedDate != null && discontinuedDate != null && !(introducedDate.isBefore(discontinuedDate))) {
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
	
	public static void validationNombreParPage(String nombreParPage) throws ValidationException {
		if (nombreParPage == null || nombreParPage.equals("") || nombreParPage.equals("null")
				|| !(Util.stringIsInt(nombreParPage))) {
			throw new ValidationException("Le nombre de page n'est pas valide : " + nombreParPage);
		}
	}

	public static void validationNumeroPage(String numeroPage) throws ValidationException {
		if (numeroPage == null || numeroPage.equals("") || numeroPage.equals("null")
				|| !(Util.stringIsInt(numeroPage))) {
			throw new ValidationException("Le nombre de page n'est pas valide : " + numeroPage);
		}
	}

	public static void validationSearch(String nomRecherche) throws ValidationException {
		if (nomRecherche == null || nomRecherche.equals("")) {
			throw new ValidationException("Le mot recherché est null : " + nomRecherche);
		}
	}

	public static void validationOrderBy(String orderBy) throws ValidationException {
		if (orderBy == null || orderBy.equals("") || orderBy.equals("null")
				|| (!orderBy.equals("id") && !orderBy.equals("name") && !orderBy.equals("introduced")
						&& !orderBy.equals("discontinued") && !orderBy.equals("company"))) {
			throw new ValidationException("Mauvaise valeur pour l'ordre d'affichage des computers.");
		}
	}
}
