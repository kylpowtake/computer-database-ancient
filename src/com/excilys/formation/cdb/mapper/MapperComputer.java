package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.time.LocalDate;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * Classe gérant les différentes méthodes de mappage concernant les computers.
 * @author kylian
 * @see Computer
 */
public class MapperComputer {
	
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
			company = new Company(companyId, companyName);
		}
		if(name == "null") {
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
}
