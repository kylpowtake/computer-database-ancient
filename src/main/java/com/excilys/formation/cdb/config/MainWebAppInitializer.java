package com.excilys.formation.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(final ServletContext sc) throws ServletException {
 
        AnnotationConfigWebApplicationContext root = 
          new AnnotationConfigWebApplicationContext();
        root.register(WebConfig.class);
        root.refresh();
        root.setServletContext(sc);
        
        sc.addListener(new ContextLoaderListener(root));
        
        DispatcherServlet servlet = new DispatcherServlet(new GenericWebApplicationContext());
        
        ServletRegistration.Dynamic registration = sc.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
        registration.addMapping("/Accueil");
    }
}