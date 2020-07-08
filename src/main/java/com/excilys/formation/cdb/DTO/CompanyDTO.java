package com.excilys.formation.cdb.DTO;

/**
 * Classe représentant une company avec ses différentes valeurs.
 * 
 * @author kylian
 *
 */
public class CompanyDTO {

	private int id;

	private String name;

	public CompanyDTO() {
	}

	public CompanyDTO(int id, String name) {
		this.id = id;
		this.name = name;
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
