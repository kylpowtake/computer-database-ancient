package com.excilys.formation.cdb.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.DAO.ComputerDAO;
import com.excilys.formation.cdb.enumeration.Resultat;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.ComputerService;

@Service
public class ComputerServiceImplementation implements ComputerService{
	
	@Autowired
	ComputerDAO computerDAO;
	
	@Override
	public List<Computer> some(String pOrderBy) throws Exception {
		return computerDAO.some(pOrderBy);
	}

	@Override
	public List<Computer> someSearch(String motRecherche, String pOrderBy) throws Exception {
		return computerDAO.someSearch(motRecherche, pOrderBy);
	}

	@Override
	public List<Computer> all(String pOrderBy) throws Exception {
		return computerDAO.all(pOrderBy);
	}

	@Override
	public Resultat create(Computer computer) throws Exception {
		Resultat resultat =  computerDAO.create(computer);
		return resultat;
	}

	@Override
	public Resultat modify(Computer computerObject) throws Exception {
		return computerDAO.modify(computerObject);
	}

	@Override
	public Resultat delete(int computerId) throws Exception {
		return computerDAO.delete(computerId);
	}

	@Override
	public Computer find(int computerId) throws Exception {
		return computerDAO.find(computerId);
	}
	
	@Override
	public int nombre() {
		System.out.println("plopop");
		return computerDAO.nombre();
	}

}
