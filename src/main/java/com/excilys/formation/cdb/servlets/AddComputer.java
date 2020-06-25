package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.connectiviteSQL.ConnexionSQL;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.mapper.MapperComputer;
import com.excilys.formation.cdb.mapper.MapperDate;
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
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultat = "";
		Map<String, String> erreurs = new HashMap<String, String>();
		// On prend les paramètres pour la création du computer.
		String computerName = (String) req.getParameter(CHAMP_NAME);
		String computerIntroduced = (String) req.getParameter(CHAMP_INTRODUCED);
		String computerDiscontinued = (String) req.getParameter(CHAMP_DISCONTINUED);
		String companyId = req.getParameter(CHAMP_COMPANY_ID);
		
		ConnexionSQL.getConnexion();

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
		} catch (ValidationException e) {
			erreurs.put(CHAMP_COMPANY_ID, e.getLocalizedMessage());
		}

		System.out.println("plop :" + companyId + ":plop");
		
		req.setAttribute(CHAMP_NAME, computerName);
		req.setAttribute(CHAMP_INTRODUCED, computerIntroduced);
		req.setAttribute(CHAMP_DISCONTINUED, computerDiscontinued);
		req.setAttribute(CHAMP_COMPANY_ID, companyId);
		req.setAttribute(ATT_ERREURS, erreurs);
		
		if (!erreurs.isEmpty()) {
			resultat = "La création du computer n'a pas été autorisé.";
		} else {
			Computer computer = null;
			try {
				computer = MapperComputer.stringToComputer(computerName, computerIntroduced, computerDiscontinued,
						companyId);
				String message = DAOComputer.getDAOComputer().ajouter(computer, Integer.parseInt(companyId));
				if (message.equals("L'opération a été effectué")) {
					// en cas d'échouage de la création du computer on renvoie les données déjà
					// remplies.
					resultat = "Création du computer réussie.";
				} else {
					resultat = "Création du computer échouée.";
				}
			} catch (ParametresException e) {
				resultat = "Création du computer échouée.";
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
