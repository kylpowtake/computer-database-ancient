package com.excilys.formation.cdb.model;


import java.time.LocalDate;

public class Computer {
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public Computer(String name) {
		this.setName(name);
	}
	
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
		String message = "index : " + this.id + " , name : " + this.name + " , introduced : " + this.introduced + " , discontinued : " + this.discontinued + " , company : ";
		if(company != null) {
			message += company.toString();
		} else {
			message += "null\n";
		}
		return message;
	}
	
}
