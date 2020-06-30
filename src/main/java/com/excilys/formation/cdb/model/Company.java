package com.excilys.formation.cdb.model;

/**
 * Classe représentant une company avec ses différentes valeurs.
 * @author kylian
 *
 */
public class Company {
	/**
	 * L'id de la company.
	 */
	private int id;
	/**
	 * Le nom de la company.
	 */
	private String name;
	
	/**
	 * Constructeur vide d'une company.
	 */
	private Company() {}
	
	public static class BuilderCompany {
		private int id;
		private String name;
		public BuilderCompany(int id) {
			this.id = id;
		}
		
		public BuilderCompany withName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build(){
			Company company = new Company();
			company.id = this.id;
			company.name = this.name;
			return company;
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
		return "Company index : " + id + " , name " + name + "\n";
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		if(obj.getClass().equals(this.getClass())) {
				Company other = (Company) obj;
				if(this.getId() == other.getId() && this.getName() != null && this.getName().equals(other.getName())) {
					return true;
				} else if(this.getName() == null && other.getName() == null){
					return true;
				}
		}
		return false;
	}
	
}
