package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.Validation;

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

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private CompanyService companyService;
	
    @Override
	public void init(ServletConfig config) throws ServletException {
    	super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,config.getServletContext());
	}
    
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Company> listCompanies = null;
		listCompanies = companyService.all("");
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {		
		logger.debug("Début doPost de AddComputer");
		Resultat resultat = Resultat.ECHOUE;
		List<Company> listCompanies = null;
		Map<String, String> erreurs = new HashMap<String, String>();
		// On prend les paramètres pour la création du computer.
		String computerName = (String) req.getParameter(CHAMP_NAME);
		String computerIntroduced = (String) req.getParameter(CHAMP_INTRODUCED);
		String computerDiscontinued = (String) req.getParameter(CHAMP_DISCONTINUED);
		String companyId = req.getParameter(CHAMP_COMPANY_ID);
		logger.debug("Début validation de doPost de AddComputer");
		try {
			Validation.validationName(computerName);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_NAME, e.getLocalizedMessage());
		}
		try {
			Validation.validationIntroduced(computerIntroduced);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_INTRODUCED, e.getLocalizedMessage());
		}
		try {
			Validation.validationDiscontinued(computerDiscontinued);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_DISCONTINUED, e.getLocalizedMessage());
		}
		try {
			Validation.validationChronologieDates(computerIntroduced, computerDiscontinued);
		} catch (ValidationException e) {
			erreurs.put(CHAMP_CHRONOLOGY, e.getLocalizedMessage());
		}
		Company company = null;
		Computer computer = null;
		try {
			Validation.validationCompanyId(companyId);
			company = (Company) companyService.find(Integer.parseInt(companyId));
		} catch (Exception e) {
			erreurs.put(CHAMP_COMPANY_ID, e.getLocalizedMessage());
		}
		
		logger.debug("Fin validation de doPost de AddComputer");
		
		listCompanies = companyService.all("");
		
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(CHAMP_NAME, computerName);
		req.setAttribute(CHAMP_INTRODUCED, computerIntroduced);
		req.setAttribute(CHAMP_DISCONTINUED, computerDiscontinued);
		req.setAttribute(CHAMP_COMPANY_ID, companyId);
		req.setAttribute(ATT_ERREURS, erreurs);
		
		if (!erreurs.isEmpty()) {
			resultat = Resultat.ECHOUE;
		} else {
			try {
				computer = MapperComputer.stringToComputer(computerName, computerIntroduced, computerDiscontinued,
						companyId);
				if(company != null) {
					computer.setCompany(company);
				}
				try {
					logger.debug("Création de doPost de AddComputer");
					resultat = computerService.create(computer);
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
		logger.debug("Fin doPost de AddComputer");
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}
}
