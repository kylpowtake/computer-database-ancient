package com.excilys.formation.cdb.service.DTO;

import com.excilys.formation.cdb.core.model.User;

public class UserDto {
	Long id;
	String username;
	String password;
	
	public UserDto() {
		super();
	}
	
	public UserDto(String pseudo, String password) {
		super();
		this.username = pseudo;
		this.password = password;
	}
	
	public UserDto(Long id, String pseudo, String password) {
		super();
		this.id = id;
		this.username = pseudo;
		this.password = password;
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
				|| (this.getUsername() != null && !this.getUsername().equals(other.getUsername()))
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
		result = value * result + ((this.getUsername() == null) ? 0 : this.getUsername().hashCode());
		result = value * result + ((this.getPassword() == null) ? 0 : this.getPassword().hashCode());
		return result;
	}

	@Override
	public String toString() {
		return this.getId() + " " + this.getUsername() + " " + this.getPassword();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
