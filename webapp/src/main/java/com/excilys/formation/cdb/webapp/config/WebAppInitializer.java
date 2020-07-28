package com.excilys.formation.cdb.webapp.config;



import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.excilys.formation.cdb.persistence.config.AppConfigSpring;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static AnnotationConfigWebApplicationContext rootContext;

	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebSecurityConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfigSpring.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
