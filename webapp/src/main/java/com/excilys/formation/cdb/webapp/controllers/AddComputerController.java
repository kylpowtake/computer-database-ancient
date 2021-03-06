 
package com.excilys.formation.cdb.webapp.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.service.DTO.CompanyDto;
import com.excilys.formation.cdb.service.DTO.ComputerDto;
import com.excilys.formation.cdb.service.DTO.Mappers.CompanyDtoMapper;
import com.excilys.formation.cdb.service.DTO.Mappers.ComputerDtoMapper;
import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.validators.ComputerDtoValidator;

@Controller
public class AddComputerController {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private static Logger logger = Logging.getLogger();

//	ApplicationContext applicationContext = WebAppInitializer.rootContext;
//	MessageSource messageSource = applicationContext.getBean(MessageSource.class);

	
	@RequestMapping(value = "/addcomputer", method = RequestMethod.GET)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ModelAndView mv = new ModelAndView("addcomputer");
		mv.setViewName("addcomputer");
		mv.addObject("computer", new ComputerDto());
		List<CompanyDto> companiesDtoList = companyService.all("").stream()
				.map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		mv.addObject("companiesList", companiesDtoList);
		return mv;
	}

	@RequestMapping(value = "/addcomputer", method = RequestMethod.POST)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("computer") ComputerDto computerDto, BindingResult result) throws ServletException, IOException{
		logger.debug("Start of post of addComputer.");
		ModelAndView mv = new ModelAndView("addcomputer");
		Resultat resultat = Resultat.ECHOUE;
		ComputerDtoValidator computerDtoValidator = new ComputerDtoValidator();
		computerDtoValidator.validate(computerDto, result);
		Computer computer = null;
		if (result.hasErrors()) {
			resultat = Resultat.ECHOUE;
		} else {
			computer = ComputerDtoMapper.computerDTOToComputer(computerDto);
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
		mv.addObject("computer", computerDto);
		List<CompanyDto> companiesDtoList = companyService.all("").stream()
				.map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		mv.addObject("companiesList", companiesDtoList);
		mv.addObject("resultat", resultat);
		return mv;
	}
}
