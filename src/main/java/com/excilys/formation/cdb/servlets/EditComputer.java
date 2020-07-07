package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.BuilderComputer;
import com.excilys.formation.cdb.persistence.CompanyDAO;
import com.excilys.formation.cdb.persistence.ComputerDAO;
import com.excilys.formation.cdb.validation.Validation;

@SuppressWarnings("serial")
@WebServlet(name = "editComputer", urlPatterns = "/editComputer")
public class EditComputer extends HttpServlet {

	public static final String COMPUTER_ID = "computerId";
	public static final String COMPUTER_NAME = "name";
	public static final String COMPUTER_INTRODUCED = "introduced";
	public static final String COMPUTER_DISCONTINUED = "discontinued";
	public static final String COMPANY_ID = "companyId";
	public static final String ATT_ERREUR_CHRONOLOGIE = "erreurChronologie";
	public static final String ATT_COMPUTER = "computer";
	public static final String ATT_ERREURS = "erreurs";
	public static final String ATT_LIST_COMPANIES = "listcompanies";
	public static final String ATT_ORDER_BY = "orderby";

	private static Logger logger = Logging.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String computerId = req.getParameter(COMPUTER_ID);
		ConnexionSQL.getConnection();
		Computer computer = null;
		try {
			computer = (Computer) ComputerDAO.getDAOComputer().find(Integer.parseInt(computerId));
		} catch (NumberFormatException e) {
			logger.error(e.getLocalizedMessage() + " doGet : EditComputer");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + " doGet : EditComputer");

		}
		List<Company> listCompanies = CompanyDAO.getDAOCompany().all("");
		req.setAttribute(COMPUTER_ID, computerId);
		req.setAttribute(COMPUTER_NAME, computer.getName());
		if (computer.getIntroduced() != null) {
			req.setAttribute(COMPUTER_INTRODUCED, computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
			req.setAttribute(COMPUTER_DISCONTINUED, computer.getDiscontinued());
		}
		if (computer.getCompany() != null) {
			req.setAttribute(COMPANY_ID, computer.getCompany().getId());
		}
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(ATT_COMPUTER, computer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("Début du doPost de EditComputer");
		String computerId = req.getParameter(COMPUTER_ID);
		String name = (String) req.getParameter(COMPUTER_NAME);
		String introduced = req.getParameter(COMPUTER_INTRODUCED);
		String discontinued = req.getParameter(COMPUTER_DISCONTINUED);
		String companyId = req.getParameter(COMPANY_ID);

		Map<String, String> erreurs = new HashMap<String, String>();
		ConnexionSQL.getConnection();
		Computer computer = null;
		try {
			computer = (Computer) ComputerDAO.getDAOComputer().find(Integer.parseInt(computerId));
		} catch (NumberFormatException e) {
			erreurs.put(COMPUTER_ID, "L'id du computer n'est pas valide");
		} catch (Exception e) {
			erreurs.put(COMPUTER_ID, "L'id du computer n'est pas valide");
		}
		if (computer == null) {
			erreurs.put(COMPUTER_ID, "L'id du computer n'est pas valide");
		}
		try {
			Validation.validationIntroduced(introduced);
		} catch (ValidationException e) {
			erreurs.put(COMPUTER_INTRODUCED, "Introduced n'est pas valide.");
		}
		try {
			Validation.validationDiscontinued(discontinued);
		} catch (ValidationException e) {
			erreurs.put(COMPUTER_DISCONTINUED, "Discontinued n'est pas valide.");
		}
		if (computer != null) {
			try {
				if (introduced != null && !introduced.equals("") && discontinued != null && !discontinued.equals("")) {
					Validation.validationChronologieDates(introduced, discontinued);
				} else if (discontinued != null && !discontinued.equals("") && computer.getIntroduced() != null
						&& !computer.getIntroduced().toString().equals("")) {
					Validation.validationChronologieDates(computer.getIntroduced().toString(), discontinued);
				} else if (introduced != null && !introduced.equals("") && computer.getDiscontinued() != null
						&& !computer.getDiscontinued().toString().equals("")) {
					Validation.validationChronologieDates(introduced, computer.getDiscontinued().toString());
				}
			} catch (ValidationException e) {
				erreurs.put(ATT_ERREUR_CHRONOLOGIE,
						"Le résultat de cette édition donnerait une date introduced plus récente que la date discontinued.");
			}
		}
		Company company = null;
		try {
			Validation.validationCompanyId(companyId);
			company = (Company) CompanyDAO.getDAOCompany().find(Integer.parseInt(companyId));
			if (company != null) {
				computer.setCompany(company);
			}
		} catch (ValidationException e) {
			erreurs.put(COMPANY_ID, "L'id de la companie n'est pas valide.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + " validationCompanyId/doPost/Editcomputer");
			this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
		}
		if (companyId == null) {
			companyId = "0";
		}
		req.setAttribute(COMPUTER_ID, computerId);
		req.setAttribute(COMPUTER_NAME, name);
		req.setAttribute(COMPUTER_INTRODUCED, introduced);
		req.setAttribute(COMPUTER_DISCONTINUED, discontinued);
		req.setAttribute(COMPANY_ID, companyId);
		req.setAttribute(ATT_ERREURS, erreurs);
		if (erreurs.isEmpty()) {
			if (name != null && !(name.equals("")) && !(name.equals("null"))) {
				computer.setName(name);
			}
			if (introduced != null && !introduced.equals("")) {
				computer.setIntroduced(MapperDate.StringToLocalDate(introduced));
			}
			if (discontinued != null && !discontinued.equals("")) {
				computer.setDiscontinued(MapperDate.StringToLocalDate(discontinued));
			}
			try {
				ComputerDAO.getDAOComputer().modify(computer);
			} catch (NumberFormatException e) {
				logger.error(e.getLocalizedMessage() + " modifierComputer/modifierComputer/doPost/EditComputer.");
			} catch (Exception e) {
				logger.error(
						e.getLocalizedMessage() + "   " + " modifierComputer/modifierComputer/doPost/EditComputer.");
			}
		}
		logger.debug("Fin du doPost de EditComputer");
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
	}
}
