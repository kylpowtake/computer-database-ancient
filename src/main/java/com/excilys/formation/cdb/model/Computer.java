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
	private Computer() {
	}
	
	public static class BuilderComputer {
		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public BuilderComputer(String name) {
			this.name = name;
		}
		
		public BuilderComputer withId(int id) {
			this.id = id;
			return this;
		}
		
		public BuilderComputer introducedThe(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		public BuilderComputer discontinuedThe(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		public BuilderComputer byCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			Computer computer = new Computer();
			computer.id = this.id;
			computer.name = this.name;
			computer.introduced = this.introduced;
			computer.discontinued = this.discontinued;
			computer.company = this.company;
			
			return computer;
		}
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
		if(this.introduced == null || this.introduced.compareTo(discontinued) <= 0) {
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
	
	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		Computer other = (Computer) o;
		if((this.getCompany() != null && !this.getCompany().equals(other.getCompany())) 
				|| (this.getDiscontinued() != null && !this.getDiscontinued().equals(other.getDiscontinued()))
				|| (this.getIntroduced() != null && !this.getIntroduced().equals(other.getIntroduced()))
				|| (!(this.getId() == other.getId()))
				|| (this.getName() != null && !this.getName().equals(other.getName()))) {
			return false;
		} else if((this.getCompany() == null && other.getCompany() != null)
				|| (this.getDiscontinued() == null && other.getDiscontinued() != null)
				|| (this.getIntroduced() == null && other.getIntroduced() != null)
				|| (this.getName() == null && other.getName() != null)){
			return false;
		}
		return true;
	}
}
