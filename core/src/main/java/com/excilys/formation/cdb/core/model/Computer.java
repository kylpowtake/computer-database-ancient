package com.excilys.formation.cdb.core.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.formation.cdb.core.logging.Logging;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Classe représentant un computer avec ses différentes valeurs.
 * 
 * @author kylian
 * @see Company
 */
@Entity
@Table(name = "computer")
public class Computer {
	/**
	 * L'id du computer.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	/**
	 * Le nom du computer.
	 */
	@Column(name="name", nullable=false)
	private String name;
	/**
	 * La date d'introduction du computer.
	 */
	@Column(name="introduced")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate introduced;
	/**
	 * La date d'arrêt du computer.
	 */
	@Column(name="discontinued")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate discontinued;
	/**
	 * La company du computer.
	 */
	@ManyToOne
	@JoinColumn(name="company_id", nullable= true)
	private Company company;

	/**
	 * Constructeur minimal avec seulement le nom du computer.
	 * 
	 * @param name
	 */
	private Computer() {
	}

	public static class ComputerBuilder {
		private Long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;

		public ComputerBuilder(String name) {
			this.name = name;
		}

		public ComputerBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder introducedThe(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinuedThe(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public ComputerBuilder byCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
			Computer computer = new Computer();
			computer.setId(this.id);
			computer.setName(this.name);
			computer.setIntroduced(this.introduced);
			computer.setDiscontinued(this.discontinued);
			computer.setCompany(this.company);

			return computer;
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		if (this.getDiscontinued() == null || introduced == null || this.getDiscontinued().compareTo(introduced) >= 0) {
			this.introduced = introduced;
		} else if(this.getDiscontinued() != null){
			Logging.getLogger().debug("date introduced plus récente que date discontinued.");
			throw new IllegalArgumentException();
		}
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) throws IllegalArgumentException {
		if (this.introduced == null || discontinued == null || this.introduced.compareTo(discontinued) <= 0) {
			this.discontinued = discontinued;
		} else if(this.getIntroduced() != null){
			Logging.getLogger().debug("date introduced plus récente que date discontinued.");
			throw new IllegalArgumentException();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String toString() {
		String message = "index : " + this.id + " , name : " + this.name + " , introduced : " + this.introduced
				+ " , discontinued : " + this.discontinued + " , ";
		if (company != null) {
			message += company.toString();
		} else {
			message += "null\n";
		}
		return message;
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
		Computer other = (Computer) obj;
		if (this.getId() != other.getId()) {
			return false;
		}
		if (other.getName() == null || !(this.getName().equals(other.getName()))) {
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
		result = value * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = value * result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = value * result + ((this.getIntroduced() == null) ? 0 : this.getIntroduced().hashCode());
		result = value * result + ((this.getDiscontinued() == null) ? 0 : this.getDiscontinued().hashCode());
		result = value * result + ((this.getCompany() == null) ? 0 : this.getCompany().hashCode());
		return result;
	}

}
