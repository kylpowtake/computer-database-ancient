package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.mapper.MapperDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DAOCompany;
import com.excilys.formation.cdb.persistence.DAOComputer;

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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String computerId = req.getParameter(COMPUTER_ID);
		ConnexionSQL.getConnexion();
		Computer computer = null;
		try {
			computer = DAOComputer.getDAOComputer().computerDetails(Integer.parseInt(computerId));
		} catch (NumberFormatException e) {
		} catch (ParametresException e) {
		}
		List<Company> listCompanies = DAOCompany.getDAOCompany().listerCompanies();
		req.setAttribute(COMPUTER_ID, computerId);
		req.setAttribute(COMPUTER_NAME, computer.getName());
		if(computer.getIntroduced() != null) {
		req.setAttribute(COMPUTER_INTRODUCED, computer.getIntroduced());
		}
		if(computer.getDiscontinued() != null) {
		req.setAttribute(COMPUTER_DISCONTINUED, computer.getDiscontinued());
		}
		if(computer.getCompany() != null) {
		req.setAttribute(COMPANY_ID, computer.getCompany().getId());
		}
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(ATT_COMPUTER, computer);
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String computerId = req.getParameter(COMPUTER_ID);
		String name = (String) req.getParameter(COMPUTER_NAME);
		String introduced = req.getParameter(COMPUTER_INTRODUCED);
		String discontinued = req.getParameter(COMPUTER_DISCONTINUED);
		String companyId = req.getParameter(COMPANY_ID);

		System.out.println(computerId + "   " + name + "   " + introduced + "   " + discontinued + "   " + companyId);

		Map<String, String> erreurs = new HashMap<String, String>();
		ConnexionSQL.getConnexion();
		Computer computer = null;
		try {
			computer = DAOComputer.getDAOComputer().computerDetails(Integer.parseInt(computerId));
		} catch (NumberFormatException e) {
			erreurs.put(COMPUTER_ID, "L'id du computer n'est pas valide");
		} catch (ParametresException e) {
			erreurs.put(COMPUTER_ID, "L'id du computer n'est pas valide");
		}
		try {
			validationIntroduced(introduced);
		} catch (ValidationException e) {
			erreurs.put(COMPUTER_INTRODUCED, "Introduced n'est pas valide.");
		}
		try {
			validationDiscontinued(discontinued);
		} catch (ValidationException e) {
			erreurs.put(COMPUTER_DISCONTINUED, "Discontinued n'est pas valide.");
		}
		try {
			System.out.println("discontinued : " + discontinued);
			if (introduced != null && !introduced.equals("") && discontinued != null && !discontinued.equals("")) {
				validationChronologieDates(introduced, discontinued);
			} else if (discontinued != null && !discontinued.equals("") && computer.getIntroduced() != null && !computer.getIntroduced().toString().equals("")) {
				validationChronologieDates(computer.getIntroduced().toString(), discontinued);
			} else if (introduced != null && !introduced.equals("") && computer.getDiscontinued() != null && !computer.getDiscontinued().toString().equals("")) {
				validationChronologieDates(introduced, computer.getDiscontinued().toString());
			}
		} catch (ValidationException e) {
			erreurs.put(ATT_ERREUR_CHRONOLOGIE,
					"Le résultat de cette édition donnerait une date introduced plus récente que la date discontinued.");
		}
		try {
			validationCompanyId(companyId);
		} catch (ValidationException e) {
			erreurs.put(COMPANY_ID, "L'id de la company n'est pas valide.");
		}
		System.out.println(erreurs.size());
		
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
			DAOComputer.getDAOComputer().modifier(computer, Integer.parseInt(companyId));
		} else {
			

		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/editComputer.jsp").forward(req, resp);
	}

	private Computer validationComputerId(String id) throws ValidationException {
		if (id != null && !(id.equals("0")) && !(id.equals("")) && !(id.equals("null"))
				&& com.excilys.formation.cdb.service.Util.stringIsInt(id)) {
			Computer computer;
			try {
				computer = DAOComputer.getDAOComputer().computerDetails(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				throw new ValidationException("");
			} catch (ParametresException e) {
				throw new ValidationException("");
			}
			if (computer == null) {
				throw new ValidationException("Il n'y a pas de computer avec l'id donné");
			} else {
				return computer;
			}
		} else {
			throw new ValidationException("L'id de computer n'est pas valide.");
		}
	}

	private void validationIntroduced(String introduced) throws ValidationException {
		if (introduced != null && !(introduced.equals("")) && !(introduced.equals("null"))
				&& !MapperDate.validatorStringIsDate(introduced)) {
			throw new ValidationException(
					"La date 'introduced' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
		}
	}

	private void validationDiscontinued(String discontinued) throws ValidationException {
		if (discontinued != null && !(discontinued.equals("")) && !(discontinued.equals("null"))
				&& !MapperDate.validatorStringIsDate(discontinued)) {
			throw new ValidationException(
					"La date 'discontinued' n'est pas constitué selon le format d'une date : YYYY-MM-JJ");
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

	private void validationCompanyId(String companyId) throws ValidationException {
		if (companyId != null && !(companyId.equals("0")) && !(companyId.equals("")) && !(companyId.equals("null"))
				&& com.excilys.formation.cdb.service.Util.stringIsInt(companyId)) {
			ResultSet resultSet = DAOCompany.getDAOCompany().findCompanybyId(Integer.parseInt(companyId));
			try {
				if (resultSet == null || !resultSet.next()) {
					throw new ValidationException("La company n'existe pas.");
				}
			} catch (SQLException e) {
				throw new ValidationException(
						"Un problème a eu lieu lors la vérification de l'existance de la company : "
								+ e.getLocalizedMessage());
			}
		}
	}
}
