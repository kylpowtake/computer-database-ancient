package com.excilys.formation.cdb.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.exception.ValidationException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.DAOComputer;
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
	public static final String VALEUR_PREVIOUS = "previous";
	public static final String VALEUR_NEXT = "next";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String resultat = "";
		int nombreComputers = 0;
		String numeroPage = req.getParameter(CHAMP_NUMERO_PAGE);
		String nombreParPage = req.getParameter(CHAMP_NOMBRE_PAR_PAGE);
		String nomRecherche = req.getParameter(CHAMP_SEARCH);
		System.out.println("plop :  " + Page.getPage().getNomRecherche() + "  " + nomRecherche);
		String previous = req.getParameter(VALEUR_PREVIOUS);
		String next = req.getParameter(VALEUR_NEXT);
		Map<String, String> erreurs = new HashMap<String, String>();
		Page page = Page.getPage();
		try {
			this.validationNombreParPage(nombreParPage);
			page.setNombreParPage(Integer.parseInt(nombreParPage));
		} catch (ValidationException e) {
			nombreParPage = "10";
		}
		try {
			this.validationNumeroPage(numeroPage);
			page.setNumeroPage(Integer.parseInt(numeroPage));
			if(previous != null && previous.equals("true") && page.getNumeroPage() != 0) {
				page.setNumeroPage(page.getNumeroPage()-1);
			}
			if(next != null && next.equals("true")){
				page.setNumeroPage(page.getNumeroPage()+1);
			}
		} catch (ValidationException e) {
			page.setNumeroPage(0);
		}
		try {
			this.validationSearch(nomRecherche);
			page.setNomRecherche(nomRecherche);
		} catch(ValidationException e) {
			if(nomRecherche != null) {
				nomRecherche = page.getNomRecherche();
			} else {
				nomRecherche = "";
			}
		}

		try {
			nombreComputers = DAOComputer.getDAOComputer().DemandeNombreComputers();
			List<Computer> listComputers = null;
			if("".equals(nomRecherche)) {
				listComputers = DAOComputer.getDAOComputer().listerComputersPage();
			} else {
				listComputers = DAOComputer.getDAOComputer().listerComputersPageRecherche(nomRecherche);
			}
			req.setAttribute(ATT_LIST_COMPUTERS, listComputers);
			resultat = "Liste de computers obtenue.";
		} catch (ParametresException e) {
			resultat = "Liste de résultat non obtenue : " + e.getLocalizedMessage();
		}

		req.setAttribute(CHAMP_NUMERO_PAGE, numeroPage);
		req.setAttribute(ATT_NOMBRE_COMPUTERS, nombreComputers);
		req.setAttribute(ATT_RESULTAT, resultat);
		req.setAttribute(ATT_PAGE, page);
		req.setAttribute(CHAMP_SEARCH, nomRecherche);
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
}
