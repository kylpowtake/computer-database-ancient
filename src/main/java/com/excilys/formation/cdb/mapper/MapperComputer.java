package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

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
