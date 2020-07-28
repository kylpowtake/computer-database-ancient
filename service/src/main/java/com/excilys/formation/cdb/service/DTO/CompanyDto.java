package com.excilys.formation.cdb.service.DTO;

/**
 * Classe représentant une company avec ses différentes valeurs.
 * 
 * @author kylian
 *
 */
public class CompanyDto {

	private String id;

	private String name;

	public CompanyDto() {
	}

	public CompanyDto(String mot) {
		id = mot;
		name = "";
	}
	
	public CompanyDto(String id, String name) {
		this.id = id;
		this.name = name;
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
		CompanyDto other = (CompanyDto) obj;
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
		result = value * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = value * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "CompanyDto : " + id + " " + name;
	}

	
}
