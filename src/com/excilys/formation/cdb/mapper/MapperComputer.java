package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.Util;

/**
 * Classe gérant les différentes méthodes de mappage concernant les computers.
 * @author kylian
 * @see Computer
 */
public class MapperComputer {
	
	/**
	 * Méthode mappant un ResultSet en une liste de Computers.
	 * @param resultSet Le resultSet à passé en Computers.
	 * @return Liste de Computers venant du ResultSet.
	 */
	public static List<Computer> mapResultSetToListComputer(ResultSet resultSet) {
		List<Computer> listComputers = new ArrayList<Computer>();
		try {
			while(resultSet.next()) {
				Computer computer = null;
				int idCompany = resultSet.getInt("computer.company_id");
				String nameCompany = resultSet.getString("company.name");
				int indice = resultSet.getInt("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, idCompany, nameCompany);
				listComputers.add(computer);
				
			}
		} catch (SQLException e) {
			System.out.println("Il y a une erreur avec le retour d'une requête à la base de données : " + e.getLocalizedMessage());
		}
		return listComputers;	
	}
	
	
	/**
	 * Méthode créant un computer à partir des différentes données formant un computer tout en vérifiant si les valeurs sont valables.
	 * @param id L'idée du computer.
	 * @param name Le nom du computer.
	 * @param introducedDate La date de début du computer.
	 * @param discontinuedDate La date d'arrêt du computer.
	 * @param companyId L'id de la company du computer.
	 * @param companyName Le nom de la company du compiter.
	 * @return Le computer.
	 */
	public static Computer dataToComputer(int id, String name, Date introducedDate, Date discontinuedDate, int companyId, String companyName) {
		Computer computer = null;
		Company company = null;
		if(companyId != 0) {
			if("null".equals(companyName)) {
				companyName = "";
			}
			company = new Company(companyId, companyName);
		}
		if("null".equals(name)) {
			name = "";
		}
		LocalDate introduced = null;
		LocalDate discontinued = null;				
		if(introducedDate != null) {
			introduced = introducedDate.toLocalDate();
		}
		if(discontinuedDate != null) {
			discontinued = discontinuedDate.toLocalDate();
		}	
		computer = new Computer(id,
				name,
				introduced,
				discontinued,
				company);
		return computer;
	}
	
	
	
	public static Computer stringToComputerAvecId(String idString, String name, String introducedString, String discontinuedString, String companyIdString) {
		Computer computer = null;
		int id = 0;
		if(Util.testArgumentInt(idString)) {
			id = Integer.parseInt(idString);
		} else {
			//message = "L'argument donné pour l'indice du computer n'est pas valide.";
			//gestionMessage(message);
			return computer;
		}
		computer =  stringToComputer(name, introducedString,  discontinuedString, companyIdString);
		if(computer != null) {
			computer.setId(id); 
		}
		return computer;
	}
	
	public static Computer stringToComputer(String name, String introducedString, String discontinuedString, String companyIdString) {
		name = Util.verificationString(name);
		if(name.equals("")) {
			//message = "L'argument donné pour le nom du computer est vide, ce n'est autorisé.";
			//gestionMessage(message);
			return null;
		}
		LocalDate introduced = null;
		LocalDate discontinued = null;
		String[] introducedDateStringSplit = Util.dateStringToTableauString(introducedString);
		String[] discontinuedDateStringSplit = Util.dateStringToTableauString(discontinuedString);
		if(introducedDateStringSplit == null) {
			//message = "L'argument donné pour la date 'introduced' n'est pas valide.";
			//gestionMessage(message);
			return null;
		} else {
			introduced = Util.tableauStringToLocalDate(introducedDateStringSplit);
		}
		if(discontinuedDateStringSplit == null) {
			//message = "L'argument donné pour la date 'discontinued' n'est pas valide.";
			//gestionMessage(message);
			return null;
		} else {
			discontinued = Util.tableauStringToLocalDate(discontinuedDateStringSplit);
		}
		
		int idCompany = 0;
		if(Util.testArgumentInt(companyIdString)) {
			idCompany = Integer.parseInt(companyIdString);
		} else {
			//message = "L'argument donné pour l'indice de la company du computer n'est pas valide.";
			//gestionMessage(message);
			return null;
		}			 
		if(introduced.isBefore(discontinued)) {
		Computer computer = new Computer(0, name, introduced, discontinued, null);
		return computer;
		} else {
			//message = "La date 'discontinued' donné n'est pas plus récente que celle de 'introduced'.";
			return null;
		}
	}
}
