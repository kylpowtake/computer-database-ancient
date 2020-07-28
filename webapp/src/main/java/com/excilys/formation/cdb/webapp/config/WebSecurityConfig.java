package com.excilys.formation.cdb.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.persistence.config.AppConfigSpring;
import com.excilys.formation.cdb.persistence.datasource.ConnectionSQL;

@EnableWebSecurity
@Component
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	ConnectionSQL connectionSQL;

	@Autowired
	public WebSecurityConfig() {
		super();
		this.connectionSQL = AppConfigSpring.theConnection();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/login").permitAll().anyRequest()
				.authenticated().and().formLogin().loginPage("/login").loginProcessingUrl("/perform_login")
				.usernameParameter("security_username").passwordParameter("security_password")
				.defaultSuccessUrl("/dashboard", true).failureUrl("/").permitAll().and().logout().permitAll().and()
				.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(connectionSQL.getDataSource())
	    .usersByUsernameQuery("select username as principal, password as credentials, true from user where username = ?")               
	    .authoritiesByUsernameQuery("select username as principal, authority as role from authorities where username = ?").rolePrefix("ROLE_");               
	  	}
}
