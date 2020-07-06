package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.Util;

/**
 * Classe gérant les différentes méthodes de mappage concernant les computers.
 * @author kylian
 * @see Computer
 */
public class MapperComputer {
	
	private static Logger logger = Logging.getLogger();
	
	public static enum TypeDate{
		DAY,
		MONTH,
		YEAR
	}
	
	/**
	 * Méthode mappant un ResultSet en une liste de Computers.
	 * @param resultSet Le resultSet à passé en Computers.
	 * @return Liste de Computers venant du ResultSet.
	 */
	public static List<Computer> mapResultSetToListComputer(ResultSet resultSet) throws Exception{
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
				try {
					computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, idCompany, nameCompany);
				} catch (ParametresException e) {
					throw e;
				}
				listComputers.add(computer);
				
			}
		} catch (SQLException e) {
			throw new Exception("Il y a une erreur lors du passage de ResultSet en Computer : " + e.getLocalizedMessage());
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
	public static Computer dataToComputer(int id, String name, Date introducedDate, Date discontinuedDate, int companyId, String companyName) throws ParametresException{
		Computer computer = null;
		Company company = null;
		if(companyId != 0) {
			if("null".equals(companyName)) {
				companyName = "";
			}
			company = new Company.BuilderCompany(companyId).withName(companyName).build();
		}
		if(name == null || "".equals(name)) {
			logger.error("Problème causé par le nom d'un computer étant soit vide soit null.");
			throw new ParametresException("Le parametre pour le nom du computer est vide, ce n'est pas valide.");
		}
		LocalDate introduced = null;
		LocalDate discontinued = null;				
		if(introducedDate != null) {
			introduced = introducedDate.toLocalDate();
		}
		if(discontinuedDate != null) {
			discontinued = discontinuedDate.toLocalDate();
		}	
		computer = new Computer.BuilderComputer(name).withId(id).introducedThe(introduced).discontinuedThe(discontinued).byCompany(company).build();
		return computer;
	}
	
	
	
	public static Computer stringToComputerAvecId(String idString, String name, String introducedString, String discontinuedString, String companyIdString) throws ParametresException{
		Computer computer = null;
		int id = 0;
		if(Util.stringIsInt(idString)) {
			id = Integer.parseInt(idString);
		} else if(idString != null && !idString.equals("null") && !(idString.length() == 0)){
			logger.error("Problème causé par l'id d'un computer n'étant pas un nombre.");
			throw new ParametresException("Le paramètre pour l'id du computer n'est pas valide.");
		}
		try {
			computer =  stringToComputer(name, introducedString,  discontinuedString, companyIdString);
		} catch(ParametresException e) {
			throw e;
		}
		if(computer != null) {
			computer.setId(id); 
		}
		return computer;
	}
	
	public static Computer stringToComputer(String name, String introducedString, String discontinuedString, String companyIdString) throws ParametresException{
		name = doublerAntislash(name);
		if(name == null || name.equals("")) {
			logger.error("Problème causé par le nom d'un computer étant soit vide soit null.");
			throw new ParametresException("Le paramètre pour le nom du computer est vide, ce n'est pas autorisé.");
		}
		LocalDate introduced = null;	
		introduced = MapperDate.StringToLocalDate(introducedString);
		LocalDate discontinued = null;
		discontinued = MapperDate.StringToLocalDate(discontinuedString);
		if(!Util.stringIsInt(companyIdString) && companyIdString!=null && !companyIdString.equals("null") && !(companyIdString.length()!=0)) {
			logger.error("Problème causé par l'id d'une companie n'étant pas un nombre.");
			throw new ParametresException("Le paramètre pour l'indice de la company est invalide.");
		}			 
		if(discontinued == null || introduced.isBefore(discontinued)) {
			Computer computer = new Computer.BuilderComputer(name).introducedThe(introduced).discontinuedThe(discontinued).build();
		return computer;
		} else {
			logger.error("Problème causé par une date discontinued étant plus vieille que la date introduced.");
			throw new ParametresException("La date de discontinued est plus vieille que celle de introduced.");
		}
	}
	
	
	public static LocalDate dateStringToLocalDate(String dateString) throws ParametresException{
		if(dateString != null && !"".equals(dateString)) {
			System.out.println(dateString + "  " +  dateString.length());
			String[] tableauString = dateString.split(":");
			if(tableauString.length == 3) {
				try {
				LocalDate localDate = tableauStringToLocalDate(tableauString);
				return localDate;
				} catch(ParametresException e){
					throw e;
				}
			} else {
				logger.error("Problème causé lors de la manipulation d'une date passé en paramètre.");
				throw new ParametresException("Les valeurs d'une des dates passées en paramètre ne sont pas valide, le format 'année:mois:jour' n'est pas respecté.");
			}
		}
		return null;
	}
	
	public static LocalDate tableauStringToLocalDate(String[] tableauString) throws ParametresException {
		LocalDate localDate = null;
		try {
			if(tableauString != null && tableauString.length == 3) {
			int year = changerDateStringToInt(tableauString[0], TypeDate.YEAR);
			int month = changerDateStringToInt(tableauString[1], TypeDate.MONTH);
			int day = changerDateStringToInt(tableauString[2], TypeDate.DAY);
			if(year == 0 || month == 0 || day == 0) {
				throw new ParametresException();
			}
			localDate = LocalDate.of(year, month, day);
			} else {
				logger.error("Problème causé lors de la manipulation d'une date passé en paramètre.");
				throw new ParametresException("Les valeurs d'une des dates passées en paramètres ne sont pas valides, format non respecté.");
			}
		} catch(DateTimeException e) {
			logger.error("Problème causé lors de la manipulation d'une date passé en paramètre : " + e.getLocalizedMessage());
			throw new ParametresException("Les valeurs d'une date passé en paramètre sont invalides.");
		}
		return localDate;
	}
	
	/**
	 * Méthode utilisé pour vérifier que la partie de Date en String passé en paramètre soit vraiment une partie de Date et valide puis la changé en int.
	 * @param stringDate La partie de Date en String.
	 * @param type String désignant quelle partie de Date est concernée (année/mois/jour).
	 * @return La partie de Date en int.
	 */
	public static int changerDateStringToInt(String stringDate, TypeDate typeDate){
		if(stringDate != null) {
		switch(typeDate) {
		case YEAR:
			if(stringDate.length() == 4 && Util.stringIsInt(stringDate)) {
				return Integer.parseInt(stringDate);
			}
			break;
		case MONTH:
			if(stringDate.length() == 2 
			&& Util.stringIsInt(stringDate) 
			&& Integer.parseInt(stringDate) >= 1 
			&& Integer.parseInt(stringDate) < 13) {
				return Integer.parseInt(stringDate);
			}
			break;
		case DAY:
			if(stringDate.length() == 2 && Util.stringIsInt(stringDate) && Integer.parseInt(stringDate) >= 1 && Integer.parseInt(stringDate) < 32) {
				return Integer.parseInt(stringDate);
			}
			break;
		default :
			return 0;
		}
		return 0;
		} else {
			return 0;
		}
	}
	
	/**
	 * Méthode vérifiant que les antislash dans le String passé en paramètre soient antislashés.
	 * @return Le String avec ses antislash antishlashés.
	 */
	public static String doublerAntislash(String message) {
		if(message != null) {
		message = message.replaceAll("\\\\", "\\\\\\\\");
		return message;
		}
		return null;
	}
}
