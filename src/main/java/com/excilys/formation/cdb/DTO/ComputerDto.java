package com.excilys.formation.cdb.DTO;

import com.excilys.formation.cdb.model.Company;

/**
 * Classe représentant un computer avec ses différentes valeurs.
 * 
 * @author kylian
 * @see Company
 */
public class ComputerDto {

	private String id;

	private String name;

	private String introduced;

	private String discontinued;
	
	private CompanyDto company;
	
	public ComputerDto() {
	}

	public ComputerDto(String id, String name, String introduced, String discontinued, CompanyDto company ) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto company) {
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
		ComputerDto other = (ComputerDto) obj;
		if (this.getId() != other.getId()) {
			return false;
		}
		if (other.getName() == null || this.getName().equals(other.getName())) {
			return false;
		}
		if ((this.getIntroduced() == null && other.getIntroduced() != null)
				|| (this.getIntroduced() != null && other.getIntroduced() == null)
				|| (this.getIntroduced() != null && !this.getIntroduced().equals(other.getIntroduced()))) {
			return false;
		}
		if ((this.getDiscontinued() == null && other.getDiscontinued() != null)
				|| (this.getDiscontinued() != null && other.getDiscontinued() == null)
				|| (this.getDiscontinued() != null && !this.getDiscontinued().equals(other.getDiscontinued()))) {
			return false;
		}
		if((this.company == null && other.getCompany() != null)
			|| (other.getCompany() == null && this.getCompany() != null)
			|| (this.getCompany() != null && !this.getCompany().equals(other.getCompany()))) {
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
		result = value * result + ((this.getIntroduced() == null) ? 0 : this.getIntroduced().hashCode());
		result = value * result + ((this.getDiscontinued() == null) ? 0 : this.getDiscontinued().hashCode());
		result = value * result + ((this.getCompany() == null) ? 0 : this.getCompany().hashCode());
		return result;
	}

	public String toString() {
		String message = "index : " + this.id + " , name : " + this.name + " , introduced : " + this.introduced
				+ " , discontinued : " + this.discontinued + " , " + this.company;
		return message;
	}

}