package com.excilys.formation.cdb.webapp.rest.webservices;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.core.Pageable.Page;
import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.core.model.QueryParams;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.Utility;
import com.excilys.formation.cdb.service.DTO.ComputerDto;
import com.excilys.formation.cdb.service.DTO.Mappers.ComputerDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ComputersWebService {

	static private final Logger logger = Logging.getLogger();

	@Autowired
	private ComputerService computerService;

	@GetMapping("/rest/getComputer")
	public ResponseEntity<String> getComputer(@RequestParam(value = "id", defaultValue = "-1") String id) {
		if (Utility.stringIsSomething(id) && !id.equals("-1")) {
			Computer computer;
			try {
				computer = computerService.find(Integer.parseInt(id));
			} catch (Exception e1) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
			ObjectMapper mapper = new ObjectMapper();
			String computerJSon = "";
			try {
				computerJSon = mapper.writeValueAsString(computer);
			} catch (JsonProcessingException e) {
				logger.error("Problème de JSon.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
			}
			return ResponseEntity.status(HttpStatus.OK).body(computerJSon);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAILURE");
		}
	}
	
	@GetMapping("/rest/getComputers")
	public String getComputers(@RequestParam(value = "orderBy", defaultValue = "id asc") String orderBy,
			@RequestParam(value = "offSet", defaultValue = "0") String offSet,
			@RequestParam(value = "limit", defaultValue = "100") String limit,
			@RequestParam(value = "search", defaultValue = "") String search) {
		QueryParams queryParams = new QueryParams(orderBy, offSet, limit, search);
		List<Computer> computersList = computerService.someUltimateSearch(queryParams);
		ObjectMapper mapper = new ObjectMapper();
		String computersListJSon = "";
		try {
			computersListJSon = mapper.writeValueAsString(computersList);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
		}
		return computersListJSon;
	}

	@PostMapping("/rest/deleteComputer/id")
	public ResponseEntity<String> deleteComputers(@RequestBody String id) {
		if (!"-1".equals(id) && Utility.stringIsSomething(id)) {
			try {
				Resultat resultat = computerService.delete(Integer.parseInt(id));
				if (resultat.equals(Resultat.REUSSI)) {
					return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
				}
			} catch (Exception e) {
				logger.error("A réparé.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
			}
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAILURE");
		}
	}
	
	@PostMapping("/rest/addcomputer")
	public ResponseEntity<String> addComputer(@RequestBody ComputerDto computerDto) {
		Computer computer = ComputerDtoMapper.computerDTOToComputer(computerDto);
		String computerString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			computerString = mapper.writeValueAsString(computer);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
		}
		try {
			Resultat resultat = computerService.create(computer);
			if (resultat.equals(Resultat.REUSSI)) {
				return ResponseEntity.status(HttpStatus.OK).body("SUCCESS : " + computerString);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
		} catch (Exception e) {
			logger.error("A réparé.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
		}
	}
	
	@PostMapping("/rest/updateComputer")
	public ResponseEntity<String> updateComputer(@RequestBody ComputerDto computerDto) {
		Computer computer = ComputerDtoMapper.computerDTOToComputer(computerDto);
		String computerString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			computerString = mapper.writeValueAsString(computer);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
		}
		try {
			Resultat resultat = computerService.modify(computer);
			if (resultat.equals(Resultat.REUSSI)) {
				return ResponseEntity.status(HttpStatus.OK).body("SUCCESS : " + computerString);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
		} catch (Exception e) {
			logger.error("A réparé.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
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
//			resultat = "Liste de computers obtenue.";
		} catch (Exception e) {
//			resultat = "Liste de résultat non obtenue : " + e.getLocalizedMessage();
			System.exit(1);
		}
		logger.debug("End of gestionListeComputers");
	}
}
