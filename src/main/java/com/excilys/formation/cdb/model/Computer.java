package com.excilys.formation.cdb.model;


import java.time.LocalDate;

/**
 * Classe représentant un computer avec ses différentes valeurs.
 * @author kylian
 * @see Company
 */
public class Computer {
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
	
	/**
	 * Constructeur minimal avec seulement le nom du computer.
	 * @param name
	 */
	public Computer(String name) {
		this.setName(name);
	}
	
	/**
	 * Constructeur maxial avec toutes ses valeurs.
	 * @param id L'id du computer.
	 * @param name Le nom du computer.
	 * @param introduced La date d'introduction du computer.
	 * @param discontinued La date d'arrêt du computer.
	 * @param company La company du computer.
	 */
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.setId(id);
		this.setName(name);
		this.setIntroduced(introduced);
		this.setDiscontinued(discontinued);
		this.setCompany(company);
	}
	
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
	
	public String toString() {
		String message = "index : " + this.id + " , name : " + this.name + " , introduced : " + this.introduced + " , discontinued : " + this.discontinued + " , ";
		if(company != null) {
			message += company.toString();
		} else {
			message += "null\n";
		}
		return message;
	}
	
}
