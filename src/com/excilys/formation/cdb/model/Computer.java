package com.excilys.formation.cdb.model;


import java.time.*;

import java.util.Date;

import com.excilys.formation.cdb.model.Company;
public class Computer {
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private Company company;
	
	public Computer(String name) {
		this.setName(name);
	}
	
	public Computer(int id, String name, Date introduced, Date discontinued, Company company) {
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
	public Date getIntroduced() {
		return introduced;
	}
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}
	public Date getDiscontinued() {
		return discontinued;
	}
	public void setDiscontinued(Date discontinued) {
		if(this.introduced == null || this.introduced.compareTo(discontinued) >= 0) {
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
		return "index : " + this.id + " , name : " + this.name + " , introduced : " + this.introduced + " , discontinued : " + this.discontinued + " , company : " + this.company;
	}
	
}
