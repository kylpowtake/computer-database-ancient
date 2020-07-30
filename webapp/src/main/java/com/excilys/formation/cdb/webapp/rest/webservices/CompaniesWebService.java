package com.excilys.formation.cdb.webapp.rest.webservices;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.Utility;
import com.excilys.formation.cdb.service.DTO.CompanyDto;
import com.excilys.formation.cdb.service.DTO.Mappers.CompanyDtoMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CompaniesWebService {
	
	@Autowired
	private CompanyService companyService;
	
	static private final Logger logger = Logging.getLogger();

	@GetMapping(value = "/rest/getCompany/id/")
	public ResponseEntity<String> getCompany(@RequestParam(value = "id", defaultValue = "-1") String id) {
		if (Utility.stringIsSomething(id) && !id.equals("-1")) {
			Company company;
			try {
				company = companyService.find(Integer.parseInt(id));
			} catch (Exception e1) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
			ObjectMapper mapper = new ObjectMapper();
			String companyJSon = "";
			try {
				companyJSon = mapper.writeValueAsString(company);
			} catch (JsonProcessingException e) {
				logger.error("Problème de JSon.");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
			}
			return ResponseEntity.status(HttpStatus.OK).body(companyJSon);
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("FAILURE");
		}
	}

	@GetMapping(value = "/rest/getCompanies")
	public ResponseEntity<String> getCompanies() {
		List<CompanyDto> companiesDtoList = companyService.all("").stream()
				.map(x -> CompanyDtoMapper.companyToCompanyDto(x)).collect(Collectors.toList());
		String listCompaniesJSon = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			listCompaniesJSon = mapper.writeValueAsString(companiesDtoList);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
		}
		return ResponseEntity.status(HttpStatus.OK).body(listCompaniesJSon);
	}
	
	@PostMapping(value = "/rest/addCompany")
	public ResponseEntity<String> addCompany(@RequestBody CompanyDto companyDto) {
		Company company = CompanyDtoMapper.companyDtoToCompany(companyDto);
		String companyString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			companyString = mapper.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
		}
		try {
			Resultat resultat = companyService.create(company);
			if (resultat.equals(Resultat.REUSSI)) {
				return ResponseEntity.status(HttpStatus.OK).body(companyString);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
		} catch (Exception e) {
			logger.error("A réparé.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
		}
	}
	
	@PostMapping(value = "/rest/updateCompany")
	public ResponseEntity<String> updateCompany(@RequestBody CompanyDto companyDto) {
		Company company = CompanyDtoMapper.companyDtoToCompany(companyDto);
		String companyString = "";
		try {
			ObjectMapper mapper = new ObjectMapper();
			companyString = mapper.writeValueAsString(company);
		} catch (JsonProcessingException e) {
			logger.error("Problème de JSon.");
		}
		try {
			Resultat resultat = companyService.update(company);
			if (resultat.equals(Resultat.REUSSI)) {
				return ResponseEntity.status(HttpStatus.OK).body(companyString);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("FAILURE");
			}
		} catch (Exception e) {
			logger.error("A réparé.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("FAILURE");
		}
	}
	
	@DeleteMapping(value = "/rest/deleteCompany/id/")
	public ResponseEntity<String> deleteCompany(@RequestParam(value = "id", defaultValue = "-1") String id) {
		if (!"-1".equals(id) && Utility.stringIsSomething(id)) {
			try {
				Resultat resultat = companyService.delete(Integer.parseInt(id));
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
}
