package com.excilys.formation.cdb.webapp.config;



import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	public static AnnotationConfigWebApplicationContext rootContext;

	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { WebSecurityConfig.class };
	}

//	@Override
//	public void onStartup(ServletContext container) throws ServletException {
//		rootContext = new AnnotationConfigWebApplicationContext();
//		rootContext.register(AppConfigSpring.class);
//
//		container.addListener(new ContextLoaderListener(rootContext));
//
//		AnnotationConfigWebApplicationContext dispatcherServlet = new AnnotationConfigWebApplicationContext();
//
//		ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher",
//				(Servlet) new DispatcherServlet(dispatcherServlet));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//	}

//	@Override
//	protected javax.servlet.Filter[] getServletFilters() {
//		DelegatingFilterProxy delegateFilterProxy = new DelegatingFilterProxy();
//		delegateFilterProxy.setTargetBeanName("loggingFilter");
//		return new Filter[] { delegateFilterProxy };
//	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { AppConfigSpring.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
}
