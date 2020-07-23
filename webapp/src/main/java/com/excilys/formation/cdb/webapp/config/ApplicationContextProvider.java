package com.excilys.formation.cdb.webapp.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ApplicationContextProvider.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ApplicationContextProvider.applicationContext;
	}
}
