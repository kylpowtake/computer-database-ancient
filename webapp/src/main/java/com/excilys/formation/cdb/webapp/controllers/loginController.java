package com.excilys.formation.cdb.webapp.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.User;
import com.excilys.formation.cdb.service.UserService;
import com.excilys.formation.cdb.service.DTO.UserDto;
import com.excilys.formation.cdb.service.DTO.Mappers.UserDtoMapper;
import com.excilys.formation.cdb.webapp.config.WebAppInitializer;

@Controller
public class loginController {

	private static Logger logger = Logging.getLogger();

	@Autowired
	private UserService userService;
	
	ApplicationContext applicationContext = WebAppInitializer.rootContext;
	MessageSource messageSource = applicationContext.getBean(MessageSource.class);

	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ModelAndView mv = new ModelAndView("login");
		mv.setViewName("login");
		mv.addObject("user", new UserDto());
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView addComputer(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("user") UserDto userDto, BindingResult result) throws ServletException, IOException{
		logger.debug("Start of post of login.");
		ModelAndView mv = new ModelAndView("login");
		Resultat resultat = Resultat.ECHOUE;
//		ComputerDtoValidator computerDtoValidator = new ComputerDtoValidator();
//		computerDtoValidator.validate(computerDto, result);
		User user = null;
		if (result.hasErrors()) {
			resultat = Resultat.ECHOUE;
		} else {
			user = UserDtoMapper.userDtoToUser(userDto);
			try {
				logger.debug("Création de doPost de login");
				resultat = userService.create(user);
			} catch (NumberFormatException e) {
				logger.error(e.getLocalizedMessage() + " doPost : login");
				System.exit(1);
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage() + " doPost : login");
				System.exit(1);
			}
		}
		mv.addObject("user", userDto);
		mv.addObject("resultat", resultat);
		return mv;
	}
}
