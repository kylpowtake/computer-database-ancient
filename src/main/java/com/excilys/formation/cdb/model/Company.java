package com.excilys.formation.cdb.model;

import com.excilys.formation.cdb.DTO.CompanyDTO;

/**
 * Classe représentant une company avec ses différentes valeurs.
 * 
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
	private Company() {
	}

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

		public Company build() {
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
		if (obj == null) {
			return false;
		}
		try {
			if (!Class.forName(this.getClass().getName()).isInstance(obj)) {
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}
		CompanyDTO other = (CompanyDTO) obj;
		if (this.id != other.getId()) {
			return false;
		}
		if ((this.name == null && other.getName() != null) || (this.name != null && other.getName() == null)
				|| (this.name.equals(other.getName()))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int value = 17;
		int result = 1;
		result = value * result + this.id;
		result = value * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		return result;
	}
}
