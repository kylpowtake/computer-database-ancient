package com.excilys.formation.cdb.binding.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.core.model.Computer;

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
	public static List<Computer> mapResultSetToListComputer(ResultSet resultSet) throws IllegalArgumentException, SQLException{
		List<Computer> listComputers = new ArrayList<Computer>();
		try {
			while(resultSet.next()) {
				Computer computer = null;
				Long idCompany = resultSet.getLong("computer.company_id");
				String nameCompany = resultSet.getString("company.name");
				Long indice = resultSet.getLong("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				Company company = MapperCompany.dataToCompany(idCompany, nameCompany);
				try {
					computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, company);
				} catch (IllegalArgumentException e) {
					throw e;
				}
				listComputers.add(computer);
			}
		} catch (SQLException e) {
			throw e;
		}
		return listComputers;	
	}
	
	
	/**
	 * Méthode mappant un ResultSet en une liste de Computers.
	 * @param resultSet Le resultSet à passé en Computers.
	 * @return Liste de Computers venant du ResultSet.
	 */
	public static Computer mapResultSetToComputer(ResultSet resultSet) throws IllegalArgumentException, SQLException{
		Computer computer = null;
		try {
			if(!resultSet.isAfterLast() && !resultSet.isBeforeFirst()) {
				Long idCompany = resultSet.getLong("computer.company_id");
				String nameCompany = resultSet.getString("company.name");
				Long indice = resultSet.getLong("computer.id");
				String name = resultSet.getString("computer.name");
				Date introducedDate = resultSet.getDate("computer.introduced");
				Date discontinuedDate = resultSet.getDate("computer.discontinued");
				Company company = MapperCompany.dataToCompany(idCompany, nameCompany);
				try {
					computer = MapperComputer.dataToComputer(indice, name, introducedDate, discontinuedDate, company);
				} catch (IllegalArgumentException e) {
					throw e;
				}
			}
		} catch (SQLException e) {
			throw e;
		}
		return computer;	
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
	public static Computer dataToComputer(Long id, String name, Date introducedDate, Date discontinuedDate, Company company) throws IllegalArgumentException{
		Computer computer = null;
		if(name == null || "".equals(name)) {
			logger.error("Problème causé par le nom d'un computer étant soit vide soit null.");
			throw new IllegalArgumentException("Le parametre pour le nom du computer est vide, ce n'est pas valide.");
		}
		LocalDate introduced = null;
		LocalDate discontinued = null;				
		if(introducedDate != null) {
			introduced = introducedDate.toLocalDate();
		}
		if(discontinuedDate != null) {
			discontinued = discontinuedDate.toLocalDate();
		}	
		computer = new Computer.ComputerBuilder(name).withId(id).introducedThe(introduced).discontinuedThe(discontinued).byCompany(company).build();
		return computer;
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
