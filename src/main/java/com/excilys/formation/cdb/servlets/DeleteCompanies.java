package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;
import com.excilys.formation.cdb.service.Util;

@WebServlet(name = "deleteCompanies", urlPatterns = "/deleteCompanies")
public class DeleteCompanies extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String CHAMP_SEARCH = "search";
	public static final String ATT_NOMBRE_COMPANIES = "nombreCompanies";
	public static final String ATT_LIST_COMPANIES = "listCompanies";
	public static final String ATT_RESULTAT = "resultat";
	public static final String ATT_DELETE = "delete";
	public static final String PARAM_ORDER_BY = "orderby";

	private String orderByGeneral = "id";
	private String nomRechercheGeneral = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultat = "";
		String nomRecherche = req.getParameter(CHAMP_SEARCH);
		String orderBy = req.getParameter(PARAM_ORDER_BY);
		List<Company> listCompanies = null;
		try {
			this.validationOrderBy(orderBy);
			if (orderBy.equals(orderByGeneral)) {
				orderBy += " DESC";
			}
			orderByGeneral = orderBy;
		} catch (ValidationException e) {
			orderBy = "id";
		}
		try {
			this.validationSearch(nomRecherche);
			nomRechercheGeneral = nomRecherche;
		} catch (ValidationException e) {
			nomRecherche = nomRechercheGeneral;
		}
		if ("".equals(nomRecherche)) {
			listCompanies = CompanyDAO.getDAOCompany().all(orderBy);
		} else {
			listCompanies = CompanyDAO.getDAOCompany().allSearch(nomRecherche, orderBy);
		}
		resultat = "Liste de computers obtenue.";
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.setAttribute(CHAMP_SEARCH, nomRecherche);
		this.getServletContext().getRequestDispatcher("/WEB-INF/deleteCompanies.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Company> listCompanies = null;
		String resultat = "";
		String nomRecherche = nomRechercheGeneral;
		String orderBy = orderByGeneral;
		if ("".equals(nomRecherche)) {
			listCompanies = CompanyDAO.getDAOCompany().all(orderBy);
		} else {
			listCompanies = CompanyDAO.getDAOCompany().allSearch(nomRecherche, orderBy);
		}
		for (int i = 0; i < listCompanies.size(); i++) {
			String valeurBox = req.getParameter("" + i);
			System.out.println("Box présente : " + valeurBox);
			if (valeurBox != null && Util.stringIsInt(valeurBox)) {
				System.out.println("On lance la destr");
				CompanyDAO.getDAOCompany().delete(Integer.parseInt(valeurBox));
			}
		}
		if ("".equals(nomRecherche)) {
			listCompanies = CompanyDAO.getDAOCompany().all(orderBy);
		} else {
			listCompanies = CompanyDAO.getDAOCompany().allSearch(nomRecherche, orderBy);
		}
		resultat = "Liste de computers obtenue.";
		req.setAttribute(ATT_LIST_COMPANIES, listCompanies);
		req.setAttribute(CHAMP_SEARCH, nomRecherche);
		req.setAttribute(ATT_RESULTAT, resultat);
		this.getServletContext().getRequestDispatcher("/WEB-INF/deleteCompanies.jsp").forward(req, resp);
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
			throw new ValidationException("Mauvaise valeur pour l'ordre d'affichage des companies.");
		}
	}
}
