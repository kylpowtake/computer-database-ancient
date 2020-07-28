package com.excilys.formation.cdb.core.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import javax.persistence.JoinColumn;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "account_non_expired")
	private boolean accountNonExpired;
	
	@Column(name = "account_non_locked")
	private boolean accountNonLocked;
	
	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;
	
	@Column(name = "enabled")
	private boolean enabled;
	
//	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinTable(name = "user_authority", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
//			@JoinColumn(name = "authority_id") })
//	private Set<Role> authorities = new HashSet<>();
	
	public User() {
		this.setAccountNonExpired(true);
		this.setAccountNonLocked(true);
		this.setCredentialsNonExpired(true);
		this.setEnabled(true);
	}

	public User(String pseudo, String password) {
		this.username = pseudo;
		this.password = password;
		this.setAccountNonExpired(true);
		this.setAccountNonLocked(true);
		this.setCredentialsNonExpired(true);
		this.setEnabled(true);
	}

	public User(Long id, String pseudo, String password) {
		this.id = id;
		this.username = pseudo;
		this.password = password;
		this.setAccountNonExpired(true);
		this.setAccountNonLocked(true);
		this.setCredentialsNonExpired(true);
		this.setEnabled(true);
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
				|| (this.getPassword() != null && !this.getPassword().equals(other.getPassword()))
				|| (this.isAccountNonExpired() != other.isAccountNonExpired())
				|| (this.isAccountNonLocked() != other.isAccountNonLocked())
				|| (this.isCredentialsNonExpired() != other.isCredentialsNonExpired())
				|| (this.isEnabled() != other.isEnabled())) {
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
		result = value * result + ((this.isAccountNonExpired()) ? 0 : 1);
		result = value * result + ((this.isAccountNonLocked()) ? 0 : 1);
		result = value * result + ((this.isCredentialsNonExpired()) ? 0 : 1);
		result = value * result + ((this.isEnabled()) ? 0 : 1);
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

	public void setUsername(String pseudo) {
		this.username = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

//	public Set<Role> getAuthorities() {
//		return authorities;
//	}
//
//	public void setAuthorities(Set<Role> authorities) {
//		this.authorities = authorities;
//	}
}
