package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DAOCompany;
import com.excilys.formation.cdb.persistence.DAOComputer;

@SuppressWarnings("serial")
public class AddComputer extends HttpServlet {
	private static final String CHAMP_NAME = "computerName";
	private static final String CHAMP_INTRODUCED = "introduced";
	private static final String CHAMP_DISCONTINUED = "discontinued";
	private static final String CHAMP_COMPANY_ID = "companyId";
	private static final String CHAMP_CHRONOLOGY = "chronology";
	private static final String ATT_RESULTAT = "resultat";
	private static final String ATT_ERREURS = "erreurs";
	private static final String ATT_LIST_COMPANIES = "listcompanies";

	private static Logger logger = Logging.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Company> listCompanies = null;
		listCompanies = DAOCompany.getDAOCompany().listerCompanies("");
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Resultat resultat = Resultat.ECHOUE;
		List<Company> listCompanies = null;
		Map<String, String> erreurs = new HashMap<String, String>();
		// On prend les paramètres pour la création du computer.
		String computerName = (String) req.getParameter(CHAMP_NAME);
		String computerIntroduced = (String) req.getParameter(CHAMP_INTRODUCED);
		String computerDiscontinued = (String) req.getParameter(CHAMP_DISCONTINUED);
		String companyId = req.getParameter(CHAMP_COMPANY_ID);
		
		ConnexionSQL.getConnection();

		try {
			this.validationName(computerName);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_NAME, e.getLocalizedMessage());
		}
		try {
			this.validationIntroduced(computerIntroduced);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_INTRODUCED, e.getLocalizedMessage());
		}
		try {
			this.validationDiscontinued(computerDiscontinued);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_DISCONTINUED, e.getLocalizedMessage());
		}
		try {
			this.validationChronologieDates(computerIntroduced, computerDiscontinued);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_CHRONOLOGY, e.getLocalizedMessage());
		}
		try {
			this.validationCompanyId(companyId);
		} catch (Exception e) {
			erreurs.put(CHAMP_COMPANY_ID, e.getLocalizedMessage());
		}
		
		listCompanies = DAOCompany.getDAOCompany().listerCompanies("");
		
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(CHAMP_NAME, computerName);
		req.setAttribute(CHAMP_INTRODUCED, computerIntroduced);
		req.setAttribute(CHAMP_DISCONTINUED, computerDiscontinued);
		req.setAttribute(CHAMP_COMPANY_ID, companyId);
		req.setAttribute(ATT_ERREURS, erreurs);
		
		if (!erreurs.isEmpty()) {
			resultat = Resultat.ECHOUE;
		} else {
			Computer computer = null;
			try {
				computer = MapperComputer.stringToComputer(computerName, computerIntroduced, computerDiscontinued,
						companyId);
				try {
					resultat = DAOComputer.getDAOComputer().ajouter(computer, Integer.parseInt(companyId));
				} catch (NumberFormatException e) {
					logger.error(e.getLocalizedMessage() + " doPost : AddComputer");
					System.exit(1);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage() + " doPost : AddComputer");
					System.exit(1);
				}
			} catch (ParametresException e) {
				logger.error(e.getLocalizedMessage() + " doPost : AddComputer.");
				System.exit(1);
			}
		}
		req.setAttribute(ATT_RESULTAT, resultat);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}

	private void validationName(String name) throws ValidationException {
		if (name == null || name.equals("")) {
			throw new ValidationException("Le computer doit nécessairement avoir un nom.");
		}
	}

	private void validationIntroduced(String introduced) throws ValidationException {
		if (introduced != null && !(introduced.equals("")) && !(introduced.equals("null")) && !MapperDate.validatorStringIsDate(introduced)) {
			throw new ValidationException("La date 'introduced' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	private void validationDiscontinued(String discontinued) throws ValidationException {
		if (discontinued != null && !(discontinued.equals("")) && !(discontinued.equals("null")) && !MapperDate.validatorStringIsDate(discontinued)) {
			throw new ValidationException("La date 'discontinued' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	private void validationChronologieDates(String introduced, String discontinued) throws ValidationException {
		if (introduced != null && discontinued != null) {
			LocalDate introducedDate = MapperDate.StringToLocalDate(introduced);
			LocalDate discontinuedDate = MapperDate.StringToLocalDate(discontinued);
			if (introducedDate != null && discontinuedDate != null && introducedDate.isAfter(discontinuedDate)) {
				throw new ValidationException("La date 'discontinued' est moins récente que la date 'introduced'.");
			}
		}
	}

	private void validationCompanyId(String companyId) throws Exception {
		if (companyId != null && !(companyId.equals("0")) && !(companyId.equals("")) && !(companyId.equals("null"))
				&& com.excilys.formation.cdb.service.Util.stringIsInt(companyId)) {
			try {
				Company company = DAOCompany.getDAOCompany().findCompanybyId(Integer.parseInt(companyId));
				if(company == null) {
					throw new Exception("La company reçu selon l'id est nulle : validationCompanyId");
				}
			} catch (NumberFormatException e1) {
				throw new Exception(e1.getLocalizedMessage() + " validationCompanyId");
			} catch (Exception e1) {
				throw new Exception(e1.getLocalizedMessage() + " validationCompanyId");
			}
		}
	}
}
