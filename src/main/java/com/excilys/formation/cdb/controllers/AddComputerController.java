
package com.excilys.formation.cdb.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.validation.Validation;

@Controller
public class AddComputerController {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private Validation validation;
	private static Logger logger = Logging.getLogger();
	private static final String CHAMP_COMPANY_ID = "companyId";

	@RequestMapping(value = "/addComputer", method = RequestMethod.GET)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("addComputer");
		mv.setViewName("addComputer");
		mv.getModel().put("data", "Welcome home man");	
		mv.addObject("computer", new ComputerDto());
		List<CompanyDto> companiesDtoList = companyService.all("").stream().map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		mv.addObject("companiesList", companiesDtoList);
		return mv;
	}

	@RequestMapping(value = "/addComputer", method = RequestMethod.POST)
	public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("computer") ComputerDto computerDto) {
		Map<String, String> erreurs = new HashMap<String, String>();
		ModelAndView mv = new ModelAndView("addComputer");
		Resultat resultat = Resultat.ECHOUE;

		try {
			validation.validationComputerDTO(computerDto, erreurs);
		} catch (Exception e1) {
			logger.error("Erreur à faire en faite.");
		}
		
		Company company = null;
		Computer computer = null;
		if(!erreurs.containsKey(CHAMP_COMPANY_ID)) {
			try {
				company = (Company) companyService.find(Integer.parseInt(computerDto.getCompanyId()));
				computerDto.setCompanyName(company.getName());
			} catch (NumberFormatException e) {
				logger.error(e.getLocalizedMessage() + " à porpos de companyId dans addComputer.");
			} catch (Exception e) {
				logger.error("Problème de requête.");
			}
		}
		if (!erreurs.isEmpty()) {
			resultat = Resultat.ECHOUE;
		} else {
			computer = ComputerDtoMapper.computerDTOToComputer(computerDto);
			if (company != null) {
				computer.setCompany(company);
			}
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
		List<CompanyDto> companiesDtoList = companyService.all("").stream().map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		mv.addObject("companiesList", companiesDtoList);
		mv.addObject("errors", erreurs);
		mv.addObject("resultat", resultat);
		return mv;
	}
}