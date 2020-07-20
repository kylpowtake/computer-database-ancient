
package com.excilys.formation.cdb.controllers;

import java.util.List;
import java.util.stream.Collectors;

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

import com.excilys.formation.cdb.DTO.CompanyDto;
import com.excilys.formation.cdb.DTO.ComputerDto;
import com.excilys.formation.cdb.DTO.Mappers.CompanyDtoMapper;
import com.excilys.formation.cdb.DTO.Mappers.ComputerDtoMapper;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validators.ComputerDtoValidator;

@Controller
public class AddComputerController {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	private static Logger logger = Logging.getLogger();

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("addComputer");
		mv.setViewName("addComputer");
		mv.addObject("computer", new ComputerDto());
		List<CompanyDto> companiesDtoList = companyService.all("").stream()
				.map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		mv.addObject("companiesList", companiesDtoList);
		return mv;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("computer") ComputerDto computerDto, BindingResult result) {
		logger.debug("Start of post of addComputer.");
		ModelAndView mv = new ModelAndView("addComputer");
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