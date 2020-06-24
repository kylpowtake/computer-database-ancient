package com.excilys.formation.cdb.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class Accueil extends HttpServlet{

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doDelete(req, resp);
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		String message = "Transmission de variables : OK !";
		request.setAttribute( "test", message );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/accueil.jsp" ).forward( request, response );
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doPut(req, resp);
	}

}
