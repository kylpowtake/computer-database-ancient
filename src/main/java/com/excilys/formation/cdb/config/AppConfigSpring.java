package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.formation.cdb.datasource.ConnectionHikari;
import com.excilys.formation.cdb.datasource.ConnectionSQL;



@Configuration
@ComponentScan(basePackages= {"com.excilys.formation.cdb.persistence", "com.excilys.formation.cdb.service", "com.excilys.formation.cdb.servlets", "com.excilys.formation.cdb.validation"})
public class AppConfigSpring extends AbstractContextLoaderInitializer{
	
	public static AnnotationConfigWebApplicationContext rootContext;
	
	@Bean
	public ConnectionSQL TheConnection() {
		ConnectionHikari connectionHikari = new ConnectionHikari("/datasource.properties");
		return connectionHikari;
	}
	
	@Override
    protected WebApplicationContext createRootApplicationContext() {
        rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfigSpring.class);
        return rootContext;
    }
}
