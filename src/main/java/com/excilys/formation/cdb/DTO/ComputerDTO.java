package com.excilys.formation.cdb.DTO;

import java.time.LocalDate;

import com.excilys.formation.cdb.model.Company;

/**
 * Classe représentant un computer avec ses différentes valeurs.
 * 
 * @author kylian
 * @see Company
 */
public class ComputerDTO {

	private int id;

	private String name;

	private LocalDate introduced;

	private LocalDate discontinued;

	private Company company;

	public ComputerDTO() {
	}

	public ComputerDTO(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
		if (this.introduced == null || this.discontinued == null || this.introduced.compareTo(discontinued) >= 0) {
			this.discontinued = discontinued;
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

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
		ComputerDTO other = (ComputerDTO) obj;
		if (this.getId() != other.getId()) {
			return false;
		}
		if (other.getName() == null || this.getName().equals(other.getName())) {
			return false;
		}
		if ((this.getIntroduced() == null && other.getIntroduced() != null)
				|| (this.getIntroduced() != null && other.getIntroduced() == null)
				|| !(this.getIntroduced().equals(other.getIntroduced()))) {
			return false;
		}
		if ((this.getDiscontinued() == null && other.getDiscontinued() != null)
				|| (this.getDiscontinued() != null && other.getDiscontinued() == null)
				|| !(this.getDiscontinued().equals(other.getDiscontinued()))) {
			return false;
		}
		if ((this.getCompany() == null && other.getCompany() != null)
				|| (this.getCompany() != null && other.getCompany() == null)
				|| !(this.getCompany().equals(other.getCompany()))) {
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
		result = value * result + ((this.getIntroduced() == null) ? 0 : this.getIntroduced().hashCode());
		result = value * result + ((this.getDiscontinued() == null) ? 0 : this.getDiscontinued().hashCode());
		result = value * result + ((this.getCompany() == null) ? 0 : this.getCompany().hashCode());
		return result;
	}

}