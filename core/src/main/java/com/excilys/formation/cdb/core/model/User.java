package com.excilys.formation.cdb.core.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "pseudo", nullable = false)
	private String pseudo;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	private User() {
		super();
	}

	private User(String pseudo, String password) {
		super();
		this.pseudo = pseudo;
		this.password = password;
	}

	private User(Long id, String pseudo, String password) {
		super();
		this.id = id;
		this.pseudo = pseudo;
		this.password = password;
	}
	
	public static class UserBuilder {
		private Long id;
		private String pseudo;
		private String password;

		public UserBuilder(String pseudo, String password) {
			this.pseudo = pseudo;
			this.password = password;
		}

		public UserBuilder withId(Long id) {
			this.id = id;
			return this;
		}

		public User build() {
			User user = new User();
			user.setId(this.id);
			user.setPseudo(this.pseudo);
			user.setPassword(this.password);
			return user;
		}
	}

	@Override
	public boolean equals(Object obj) {
		try {
			if(obj == null || !Class.forName(this.getClass().getName()).isInstance(obj)) {
				return false;
			}
		} catch (ClassNotFoundException e) {
			return false;
		}
		User other = (User) obj;
		if((this.getId() != null && !this.getId().equals(other.getId())) 
				|| (this.getPseudo() != null && !this.getPseudo().equals(other.getPseudo()))
				|| (this.getPassword() != null && !this.getPassword().equals(other.getPassword()))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int value = 25;
		int result = 1;
		result = value * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
		result = value * result + ((this.getPseudo() == null) ? 0 : this.getPseudo().hashCode());
		result = value * result + ((this.getPassword() == null) ? 0 : this.getPassword().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return this.getId() + " " + this.getPseudo() + " " + this.getPassword();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
