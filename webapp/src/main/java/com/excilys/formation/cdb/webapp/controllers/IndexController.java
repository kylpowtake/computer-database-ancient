package com.excilys.formation.cdb.webapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.core.logging.Logging;

@Controller
public class IndexController {
	
	Logger logger = Logging.getLogger();
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	protected ModelAndView doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("Début du doGet de Index");
		ModelAndView mv = new ModelAndView("index");
		mv.setViewName("index");
		logger.debug("Fin du doGet de Index");
		return mv;
	}

}
