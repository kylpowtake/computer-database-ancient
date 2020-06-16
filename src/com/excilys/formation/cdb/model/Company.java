package com.excilys.formation.cdb.model;


public class Company {
	private int id;
	private String name;
	
	public Company() {}
	
	public Company(int id, String name) {
		this.setId(id);
		if(name == "null") {
			this.setName("");
		} else {
			this.setName(name);
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
	
	public String toString() {
		System.out.println("yop");
		return "Company index : " + id + " , name " + name + "\n";
	}
}
