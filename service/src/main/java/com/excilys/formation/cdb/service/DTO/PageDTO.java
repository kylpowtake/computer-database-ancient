package com.excilys.formation.cdb.DTO;

public class PageDTO {
	
	private String nomRecherche;
	
	private String numeroPage;
	
	private String nombreParPage;
	
	private String orderBy;

	public PageDTO() {}
	
	public PageDTO(String nomRecherche, String numeroPage, String nombreParPage, String orderBy) {
		this.nomRecherche = nomRecherche;
		this.numeroPage = numeroPage;
		this.nombreParPage = nombreParPage;
		this.orderBy = orderBy;
	}

	public String getNomRecherche() {
		return nomRecherche;
	}

	public void setNomRecherche(String nomRecherche) {
		this.nomRecherche = nomRecherche;
	}

	public String getNumeroPage() {
		return numeroPage;
	}

	public void setNumeroPage(String numeroPage) {
		this.numeroPage = numeroPage;
	}

	public String getNombreParPage() {
		return nombreParPage;
	}

	public void setNombreParPage(String nombreParPage) {
		this.nombreParPage = nombreParPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
}
