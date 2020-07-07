package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDAO;
import com.excilys.formation.cdb.service.Util;

@SuppressWarnings("serial")
public class DashBoard extends HttpServlet {
	public static final String CHAMP_NUMERO_PAGE = "numeroPage";
	public static final String CHAMP_NOMBRE_PAR_PAGE = "nombreParPage";
	public static final String CHAMP_SEARCH = "search";
	public static final String ATT_NOMBRE_COMPUTERS = "nombreComputers";
	public static final String ATT_LIST_COMPUTERS = "listComputers";
	public static final String ATT_RESULTAT = "resultat";
	public static final String ATT_ERREURS = "erreurs";
	public static final String ATT_PAGE = "page";
	public static final String PAR_PREVIOUS = "previous";
	public static final String PAR_NEXT = "next";
	public static final String ATT_DELETE = "delete";
	public static final String PARAM_ORDER_BY = "orderby";
	
	private static Logger logger = Logging.getLogger();

	private String orderByGeneral = "id";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultat = "";
		String nomRecherche = req.getParameter(CHAMP_SEARCH);
		String numeroPage = req.getParameter(CHAMP_NUMERO_PAGE);
		String nombreParPage = req.getParameter(CHAMP_NOMBRE_PAR_PAGE);
		String orderBy = req.getParameter(PARAM_ORDER_BY);
		
		Page page = Page.getPage();
		try {
			this.validationOrderBy(orderBy);
			if(orderBy.equals(orderByGeneral)) {
				orderBy += " DESC";
			}
			orderByGeneral = orderBy;
		} catch (ValidationException e) {
			if(orderByGeneral != null) {
				orderBy = orderByGeneral;
			} else {
				orderBy = "id";
			}
		}
		try {
			this.validationNombreParPage(nombreParPage);
			page.setNombreParPage(Integer.parseInt(nombreParPage));
		} catch (ValidationException e) {

		}
		try {
			this.validationNumeroPage(numeroPage);
			page.setNumeroPage(Integer.parseInt(numeroPage));
		} catch (ValidationException e) {

		}
		try {
			this.validationSearch(nomRecherche);
			page.setNomRecherche(nomRecherche);
		} catch (ValidationException e) {
			if (nomRecherche != null) {
				nomRecherche = page.getNomRecherche();
			} else {
				nomRecherche = "";
			}
		}
		String previous = req.getParameter(PAR_PREVIOUS);
		if ("true".equals(previous)) {
			page.setNumeroPage(page.getNumeroPage() - 1);
		}
		String next = req.getParameter(PAR_NEXT);
		if ("true".equals(next)) {
			page.setNumeroPage(page.getNumeroPage() + 1);
		}

		try {
			Page.setNombreComputers(ComputerDAO.getDAOComputer().DemandeNombreComputers());
			List<Computer> listComputers = null;
			if ("".equals(nomRecherche)) {
				try {
					listComputers = ComputerDAO.getDAOComputer().some(orderBy);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage() + " doGet : DashBoard");
				}
			} else {
				try {
					listComputers  = ComputerDAO.getDAOComputer().someSearch(nomRecherche, orderBy);
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage() + " doGet : DashBoard");
				}
			}
			page.setComputers(listComputers);
			resultat = "Liste de computers obtenue.";
		} catch (Exception e) {
			resultat = "Liste de résultat non obtenue : " + e.getLocalizedMessage();
			System.exit(1);
		}
		req.setAttribute(ATT_RESULTAT, resultat);
		req.setAttribute(ATT_PAGE, page);
		req.setAttribute(CHAMP_SEARCH, nomRecherche);
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String resultat = "";
		Page page = Page.getPage();
		String orderBy = orderByGeneral;
		page.setNumeroPage(0);
		for (int i = 0; i < page.getNombreParPage(); i++) {
			String valeurBox = req.getParameter("" + i);
			if (valeurBox != null) {
				try {
					ComputerDAO.getDAOComputer().delete(Integer.parseInt(valeurBox));
				} catch (NumberFormatException e) {
					logger.error(e.getLocalizedMessage() + " doPost : DashBoard.");
				} catch (Exception e) {
				}
			}
		}
		try {
			Page.setNombreComputers(ComputerDAO.getDAOComputer().DemandeNombreComputers());
			List<Computer> listComputers = null;
			try {
				listComputers = ComputerDAO.getDAOComputer().some(orderBy);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage() + " doGet : DashBoard");
			}
			page.setComputers((ArrayList<Computer>) listComputers);
			resultat = "Liste de computers obtenue.";
		} catch (Exception e) {
			resultat = "Liste de résultat non obtenue : " + e.getLocalizedMessage();
		}
		req.setAttribute(ATT_PAGE, page);
		req.setAttribute(ATT_RESULTAT, resultat);
		this.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp").forward(req, resp);
	}

	private void validationNombreParPage(String nombreParPage) throws ValidationException {
		if (nombreParPage == null || nombreParPage.equals("") || nombreParPage.equals("null")
				|| !(Util.stringIsInt(nombreParPage))) {
			throw new ValidationException("Le nombre de page n'est pas valide : " + nombreParPage);
		}
	}

	private void validationNumeroPage(String numeroPage) throws ValidationException {
		if (numeroPage == null || numeroPage.equals("") || numeroPage.equals("null")
				|| !(Util.stringIsInt(numeroPage))) {
			throw new ValidationException("Le nombre de page n'est pas valide : " + numeroPage);
		}
	}

	private void validationSearch(String nomRecherche) throws ValidationException {
		if (nomRecherche == null || nomRecherche.equals("")) {
			throw new ValidationException("Le mot recherché est null : " + nomRecherche);
		}
	}

	private void validationOrderBy(String orderBy) throws ValidationException {
		if (orderBy == null || orderBy.equals("") || orderBy.equals("null")
				|| (!orderBy.equals("id") && !orderBy.equals("name") && !orderBy.equals("introduced")
						&& !orderBy.equals("discontinued") && !orderBy.equals("company"))) {
			throw new ValidationException("Mauvaise valeur pour l'ordre d'affichage des computers.");
		}
	}
}
