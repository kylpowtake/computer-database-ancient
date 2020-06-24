package com.excilys.formation.cdb.servlets;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class AddComputer extends HttpServlet{

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( req, resp );
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String computerName = (String) req.getParameter("computerName");
		String computerIntroduced = (String) req.getParameter("introduced");
		String computerDiscontinued = (String) req.getParameter("discontinued");
		String companyId = req.getParameter("companyId");
		
		if(computerName == null || computerName.trim().isEmpty()) {
			
		}
		System.out.println("Nom du computer : " + computerName);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( req, resp );
		//super.doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}

}
