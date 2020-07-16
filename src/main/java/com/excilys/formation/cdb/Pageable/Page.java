package com.excilys.formation.cdb.Pageable;

import java.util.List;

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
	private static List<Computer> computers = null;
	/**
	 * Nombre de computer maximum pour la page. {@value}
	 */
	private static int nombreParPage = 10;
	/**
	 * Le numéro de page permettant de se positionner parmi tous les computer.
	 */
	private static int numeroPage = 0;
	/**
	 * Le nom recherché pour chaque computer.
	 */
	private static String nomRecherche = "";

	private static int nombreComputers = 0;
	/**
	 * Booléan donnant la présence de page antérieure.
	 */
	private static boolean peutAllerNouvellePage = false;
	/**
	 * Booléan donnant la précense de page postérieure.
	 */
	private static boolean peutAllerAnciennePage = false;

	private static String orderBy = "id";
	
	private static OrderEnum order = OrderEnum.ASC;
	public enum OrderEnum{
		ASC("ASC"),
		DESC("DESC");
		
		String value;

		OrderEnum(String string) {
			value = string;
		}
		
		public String getValue() {
			return value;
		}

		public static OrderEnum change(OrderEnum orderEnum) {
			if(orderEnum.equals(OrderEnum.ASC)) {
				return OrderEnum.DESC;
			} else {
				return OrderEnum.ASC;
			}
		}
	}
	/**
	 * Constructeur privée de la classe sans paramètre.
	 */
	private Page() {
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

	public List<Computer> getComputers() {
		return Page.computers;
	}

	public void setComputers(List<Computer> listComputers) {
		Page.computers = listComputers;
	}

	public int getNombreParPage() {
		return Page.nombreParPage;
	}

	public void setNombreParPage(int nombreParPage) {
		Page.nombreParPage = nombreParPage;
	}

	public int getNumeroPage() {
		return Page.numeroPage;
	}

	public void setNumeroPage(int numeroPage) {
		if (numeroPage < 0) {
			Page.numeroPage = 0;
		} else if (numeroPage * this.getNombreParPage() < this.getNombreComputers()) {
			Page.numeroPage = numeroPage;
		}
	}

	public String getNomRecherche() {
		return Page.nomRecherche;
	}

	public void setNomRecherche(String nomRecherche) {
		Page.nomRecherche = nomRecherche;
	}

	public boolean isPeutAllerNouvellePage() {
		return Page.peutAllerNouvellePage;
	}

	public int getNombreComputers() {
		return Page.nombreComputers;
	}

	public void setNombreComputers(int nombreComputers) {
		Page.nombreComputers = nombreComputers;
	}

	public void setPeutAllerNouvellePage(boolean peutAllerNouvellePage) {
		Page.peutAllerNouvellePage = peutAllerNouvellePage;
	}

	public boolean isPeutAllerAnciennePage() {
		return Page.peutAllerAnciennePage;
	}

	public void setPeutAllerAnciennePage(boolean peutAlleranciennePage) {
		Page.peutAllerAnciennePage = peutAlleranciennePage;
	}

	public void setPeutAllerAncienneEtNouvellePage(int nombreComputers) {
		this.setPeutAllerNouvellePage(nombreComputers > this.getNumeroPage() * this.getNombreParPage());
		this.setPeutAllerAnciennePage(this.getNumeroPage() != 1);
	}

	public String getOrderBy() {
		return Page.orderBy;
	}

	public void setOrderBy(String orderBy) {
		Page.orderBy = orderBy;
	}
	
	public String toString() {
		return "La page est : " + this.getNombreParPage() + ", " + this.getNombreComputers() + ", " + this.getNomRecherche() + ", " + this.getNumeroPage() + ", " + this.getOrderBy();
	}

	public OrderEnum getOrder() {
		return order;
	}

	public  void setOrder(OrderEnum order) {
		Page.order = order;
	}

}
