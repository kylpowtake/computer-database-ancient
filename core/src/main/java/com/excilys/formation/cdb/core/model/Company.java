package com.excilys.formation.cdb.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Classe représentant une company avec ses différentes valeurs.
 * 
 * @author kylian
 *
 */
@Entity
@Table(name = "company")
public class Company {
	/**
	 * L'id de la company.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	/**
	 * Le nom de la company.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * Constructeur vide d'une company.
	 */
	private Company() {
	}

	private Company(Company company) {
		this.id = company.getId();
		this.name = company.getName();
	}

	public static class CompanyBuilder {
		private Long id;
		private String name;

		public CompanyBuilder(Long id) {
			this.id = id;
		}

		public CompanyBuilder withName(String name) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
		Company other = (Company) obj;
		if (this.id != other.getId()) {
			return false;
		}
		if ((this.name == null && other.getName() != null)
				|| (this.name != null && !this.name.equals(other.getName()))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int value = 17;
		int result = 1;
		result = value * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = value * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		return result;
	}
}
