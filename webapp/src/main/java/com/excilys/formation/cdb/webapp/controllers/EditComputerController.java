package com.excilys.formation.cdb.webapp.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.service.DTO.ComputerDto;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class EditComputerController {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private static Logger logger = Logging.getLogger();
	
	@RequestMapping(value = "/editComputer", method = RequestMethod.GET)
	public ModelAndView editComputer(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("computerId") int computerId) {
		ModelAndView mv = new ModelAndView("editComputer");
		mv.setViewName("editComputer");
		Computer computer = null;
		try {
			computer = (Computer) computerService.find(computerId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + " doGet : EditComputer");
		}
		List<Company> companiesList = companyService.all("");
		
		mv.addObject("companiesList", companiesList);
		return mv;
	}
	
	@RequestMapping(value = "/editComputer", method = RequestMethod.POST)
	public ModelAndView editComputer(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("computer") ComputerDto computerDto, BindingResult result) {
		ModelAndView mv = new ModelAndView("editComputer");
		mv.setViewName("editComputer");
		return mv;
	}
}
