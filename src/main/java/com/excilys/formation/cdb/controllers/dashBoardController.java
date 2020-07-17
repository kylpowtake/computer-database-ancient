package com.excilys.formation.cdb.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.DTO.PageDTO;
import com.excilys.formation.cdb.Pageable.Page;
import com.excilys.formation.cdb.Pageable.Page.OrderEnum;
import com.excilys.formation.cdb.config.WebAppInitializer;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.Validation;

@Controller
public class dashBoardController {
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

	@Autowired
	private Validation validation;

	@Autowired
	private ComputerService computerService;
	
	ApplicationContext applicationContext = WebAppInitializer.rootContext;

	MessageSource messageSource = applicationContext.getBean(MessageSource.class);


//	private String orderByGeneral = "id";

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Début du doGet de Dashboard");
		Page page = Page.getPage();
		logger.debug(page.toString());
		ModelAndView mv = new ModelAndView("dashboard");
		mv.setViewName("dashboard");
		gestionModificationPage(page, req);
		gestionListeComputers();
		mv.addObject("page", page);
		logger.debug("Fin du doGet de Dashboard");
		return mv;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.POST)
	protected ModelAndView doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Début du doPost de Dashboard");
		ModelAndView mv = new ModelAndView("dashboard");
		mv.setViewName("dashboard");
		Page page = Page.getPage();
		for (int i = 0; i < page.getNombreParPage(); i++) {
			String valeurBox = req.getParameter("" + i);
			if (valeurBox != null) {
				try {
					computerService.delete(Integer.parseInt(valeurBox));
				} catch (NumberFormatException e) {
					logger.error(e.getLocalizedMessage() + " doPost : DashBoard.");
				} catch (Exception e) {
				}
			}
		}
		gestionListeComputers();
		mv.addObject("page", page);
		logger.debug("Fin du doPost de Dashboard");
		return mv;
	}
	
	
	public void gestionModificationPage(Page page, HttpServletRequest req) {
		String nomRecherche = req.getParameter(CHAMP_SEARCH);
		String numeroPage = req.getParameter(CHAMP_NUMERO_PAGE);
		String nombreParPage = req.getParameter(CHAMP_NOMBRE_PAR_PAGE);
		String orderBy = req.getParameter(PARAM_ORDER_BY);
		PageDTO pageDto = new PageDTO(nomRecherche, numeroPage, nombreParPage, orderBy);
		if (validation.validationPage(pageDto)) {
			if (nomRecherche != null) {
				page.setNomRecherche(nomRecherche);
			}
			if (numeroPage != null) {
				page.setNumeroPage(Integer.parseInt(numeroPage));
			}
			if (nombreParPage != null) {
				page.setNombreParPage(Integer.parseInt(nombreParPage));
			}
			if (orderBy != null && page.getOrderBy().equals(orderBy)) {
				page.setOrderBy(orderBy);
				page.setOrder(OrderEnum.change(page.getOrder()));
			} else if (orderBy != null) {
				page.setOrderBy(orderBy);
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
	}

	public void gestionListeComputers() {
		Page page = Page.getPage();
		logger.debug("Start of gestionListeComputers");
		try {
			page.setNombreComputers(computerService.nombre());
			List<Computer> listComputers = null;
			if ("".equals(page.getNomRecherche())) {
				try {
					listComputers = computerService.some(page.getOrderBy());
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage() + " doGet : DashBoard");
				}
			} else {
				try {
					listComputers = computerService.someSearch(page.getNomRecherche(), page.getOrderBy());
				} catch (Exception e) {
					logger.error(e.getLocalizedMessage() + " doGet : DashBoard");
				}
			}
			page.setComputers(listComputers);
			logger.debug("The computers's list is composed of " + page.getComputers().size() + " computers     "
					+ page.getNomRecherche());
//			resultat = "Liste de computers obtenue.";
		} catch (Exception e) {
//			resultat = "Liste de résultat non obtenue : " + e.getLocalizedMessage();
			System.exit(1);
		}
		logger.debug("End of gestionListeComputers");
	}
}
