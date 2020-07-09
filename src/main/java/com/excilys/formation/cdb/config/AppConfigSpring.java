package com.excilys.formation.cdb.config;

import org.slf4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.formation.cdb.datasource.ConnectionSQL;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.datasource.ConnectionHikari;

@Configuration
@ComponentScan(basePackages="com.excilys.formation.cdb")
public class AppConfigSpring extends AbstractContextLoaderInitializer{
	
	@Bean
	public ConnectionSQL TheConnection() {
		ConnectionHikari connectionHikari = new ConnectionHikari();
		return connectionHikari;
	}
	
	@Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext rootContext
          = new AnnotationConfigWebApplicationContext();
        rootContext.register(AppConfigSpring.class);
        return rootContext;
    }
}
