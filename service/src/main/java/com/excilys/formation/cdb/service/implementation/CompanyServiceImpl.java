package com.excilys.formation.cdb.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDao;
import com.excilys.formation.cdb.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	public CompanyServiceImpl(@Qualifier(value = "companyDaoHibernate") CompanyDao companyDAO) {
		this.companyDAO = companyDAO;
	}

	CompanyDao companyDAO;

	@Override
	public List<Company> all(String pOrderBy) {
		return companyDAO.all(pOrderBy);
	}

	@Override
	public List<Company> allSearch(String rechercheNom, String pOrderBy) {
		return companyDAO.allSearch(rechercheNom, pOrderBy);
	}

	@Override
	public Company find(int companyId) throws Exception {
		return companyDAO.find(companyId);
	}

	@Override
	public Resultat create(Company company) {
		return companyDAO.create(company);
	}

	@Override
	public Resultat update(Company company) {
	return companyDAO.update(company);
	}
	
	@Override
	public Resultat delete(int id) {
		return companyDAO.delete(id);
	}

	@Override
	public String modificationOrderBy(String orderBy) {
		return CompanyDao.modificationOrderBy(orderBy);
	}



}
