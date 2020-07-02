package com.excilys.formation.cdb.Pageable;

import java.util.ArrayList;

import com.excilys.formation.cdb.model.Computer;

/**
 * Classe singleton représentant une page de Computer.
 * 
 * @author kylian
 *
 */
public class Page {
	/**
	 * La page singleton.
	 */
	private static Page page = null;
	/**
	 * ArrayList de computer
	 */
	private static ArrayList<Computer> computers;
	/**
	 * Nombre de computer maximum pour la page. {@value}
	 */
	private static int nombreParPage = 10;
	/**
	 * Le numéro de page permettant de se positionner parmi tous les computer.
	 */
	private static int numeroPage;
	/**
	 * Le nom recherché pour chaque computer.
	 */
	private static String nomRecherche = "";

	private static int nombreComputers = 0;
	/**
	 * Booléan donnant la présence de page antérieure.
	 */
	private boolean peutAllerNouvellePage;
	/**
	 * Booléan donnant la précense de page postérieure.
	 */
	private boolean peutAllerAnciennePage;

	/**
	 * Constructeur privée de la classe sans paramètre.
	 */
	private Page() {
		computers = null;
		numeroPage = 0;
		nomRecherche = "";
		peutAllerAnciennePage = false;
		peutAllerNouvellePage = false;
	}

	/**
	 * Méthode donnant la page et si elle n'existe pas la créant avant.
	 * 
	 * @return la page
	 */
	public static Page getPage() {
		if (page == null) {
			page = new Page();
		}
		return page;
	}

	public ArrayList<Computer> getComputers() {
		return computers;
	}

	public void setComputers(ArrayList<Computer> pComputers) {
		computers = pComputers;
	}

	public int getNombreParPage() {
		return nombreParPage;
	}

	public void setNombreParPage(int pNombreParPage) {
		nombreParPage = pNombreParPage;
	}

	public int getNumeroPage() {
		return numeroPage;
	}

	public void setNumeroPage(int pNumeroPage) {
		if (pNumeroPage < 0) {
			numeroPage = 0;
		} else if (pNumeroPage * this.getNombreParPage() < this.getNombreComputers()) {
			numeroPage = pNumeroPage;
		}
	}

	public String getNomRecherche() {
		return nomRecherche;
	}

	public void setNomRecherche(String nomRecherche) {
		Page.nomRecherche = nomRecherche;
	}

	public boolean isPeutAllerNouvellePage() {
		return peutAllerNouvellePage;
	}

	public int getNombreComputers() {
		return nombreComputers;
	}

	public static void setNombreComputers(int nombreComputers) {
		Page.nombreComputers = nombreComputers;
	}

	public void setPeutAllerNouvellePage(boolean peutAllerNouvellePage) {
		this.peutAllerNouvellePage = peutAllerNouvellePage;
	}

	public boolean isPeutAllerAnciennePage() {
		return peutAllerAnciennePage;
	}

	public void setPeutAllerAnciennePage(boolean peutAlleranciennePage) {
		this.peutAllerAnciennePage = peutAlleranciennePage;
	}

	public void setPeutAllerAncienneEtNouvellePage(int nombreComputers) {
		this.setPeutAllerNouvellePage(nombreComputers > this.getNumeroPage() * this.getNombreParPage());
		this.setPeutAllerAnciennePage(this.getNumeroPage() != 1);
	}

}
