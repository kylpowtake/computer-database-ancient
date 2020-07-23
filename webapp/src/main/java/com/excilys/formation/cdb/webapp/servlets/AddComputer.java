/*
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

import com.excilys.formation.cdb.DTO.CompanyDto;
import com.excilys.formation.cdb.DTO.ComputerDto;
import com.excilys.formation.cdb.DTO.Mappers.ComputerDtoMapper;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.logging.Logging;
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
	private Validation validation;
	
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
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
		String name = (String) req.getParameter(CHAMP_NAME);
		String introduced = (String) req.getParameter(CHAMP_INTRODUCED);
		String discontinued = (String) req.getParameter(CHAMP_DISCONTINUED);
		String companyId = req.getParameter(CHAMP_COMPANY_ID);

		CompanyDto companyDTO = new CompanyDto(companyId, null);
		ComputerDto computerDTO = new ComputerDto(null, name, introduced, discontinued, companyDTO.getId(), companyDTO.getName());

		try {
			validation.validationComputerDTO(computerDTO, erreurs);
		} catch (Exception e1) {
			logger.error("Erreur à faire en faite.");
		}

		Company company = null;
		Computer computer = null;
		if(!erreurs.containsKey(CHAMP_COMPANY_ID)) {
			try {
				company = (Company) companyService.find(Integer.parseInt(companyId));
			} catch (NumberFormatException e) {
				logger.error(e.getLocalizedMessage() + " à porpos de companyId dans addComputer.");
			} catch (Exception e) {
				logger.error("Problème de requête.");
			}
		}

		logger.debug("Fin validation de doPost de AddComputer");

		listCompanies = companyService.all("");

		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(CHAMP_NAME, name);
		req.setAttribute(CHAMP_INTRODUCED, introduced);
		req.setAttribute(CHAMP_DISCONTINUED, discontinued);
		req.setAttribute(CHAMP_COMPANY_ID, companyId);
		req.setAttribute(ATT_ERREURS, erreurs);

		if (!erreurs.isEmpty()) {
			resultat = Resultat.ECHOUE;
		} else {
			computer = ComputerDtoMapper.computerDTOToComputer(computerDTO);
			if (company != null) {
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
*/