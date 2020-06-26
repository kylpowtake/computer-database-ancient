package com.excilys.formation.cdb.DTO;

import java.time.LocalDate;

import com.excilys.formation.cdb.model.Company;


/**
 * Classe représentant un computer avec ses différentes valeurs.
 * @author kylian
 * @see Company
 */
public class ComputerDTO {
	/**
	 * L'id du computer.
	 */
	private int id;
	/**
	 * Le nom du computer.
	 */
	private String name;
	/**
	 * La date d'introduction du computer.
	 */
	private LocalDate introduced;
	/**
	 * La date d'arrêt du computer.
	 */
	private LocalDate discontinued;
	/**
	 * La company du computer.
	 */
	private Company company;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getIntroduced() {
		return introduced;
	}
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}
	public LocalDate getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(LocalDate discontinued) {
		if(this.introduced == null || this.discontinued == null || this.introduced.compareTo(discontinued) >= 0) {
			this.discontinued = discontinued;
		}
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
}