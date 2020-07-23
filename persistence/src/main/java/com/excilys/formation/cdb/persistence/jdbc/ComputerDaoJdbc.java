package com.excilys.formation.cdb.persistence.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.core.Pageable.Page;
import com.excilys.formation.cdb.persistence.datasource.ConnectionSQL;
import com.excilys.formation.cdb.core.enumeration.Resultat;
import com.excilys.formation.cdb.core.logging.Logging;
import com.excilys.formation.cdb.core.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDao;
import com.excilys.formation.cdb.persistence.jdbc.row.mapper.ComputerRowMapper;

@Repository
public class ComputerDaoJdbc implements ComputerDao {

	private static Logger logger = Logging.getLogger();

	ConnectionSQL connectionSQL;

	/**
	 * Constructeur de la classe, en privé pour le singleton.
	 */
	@Autowired
	public ComputerDaoJdbc(ConnectionSQL connectionSQL) {
		this.connectionSQL = connectionSQL;
	}

	public ConnectionSQL getConnection() {
		return connectionSQL;
	}

	public void setConnection(ConnectionSQL connectionSQL) {
		this.connectionSQL = connectionSQL;
	}

	@Override
	public int nombre() {
		logger.debug("Start of nombre of ComputerDaoJdbc.");
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return namedParameterJdbcTemplate.queryForObject("SELECT COUNT(name) FROM computer", namedParameters,
				Integer.class);
	}

	@Override
	public boolean findcomputerById(Long computerId) throws Exception {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", computerId);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return (namedParameterJdbcTemplate.queryForObject(REQUETEFINDCOMPUTERBYID, namedParameters,
				new ComputerRowMapper()) != null);
	}

	@Override
	public int faireRequeteSansResultat(String requete) {
		// TODO Auto-generated method stub
		return 0;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Computer> listerComputersEnd() throws Exception {
//		Page page = Page.getPage();
//		int nombreComputers = nombre();
//		page.setNumeroPage(((nombreComputers - (nombreComputers % 10)) / 10) + 1);
//		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
//		int indice = (page.getNumeroPage() - 1) * page.getNombreParPage();
//		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("orderBy", "computer.id")
//				.addValue("computerParPage", page.getNombreParPage()).addValue("positionComputers", indice);
//		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
//				connectionSQL.getDataSource());
//		return (List<Computer>) namedParameterJdbcTemplate.queryForObject(REQUETEPAGECOMPUTERS, namedParameters,
//				new ComputerRowMapper());
//	}

	@Override
	public List<Computer> some(String orderBy){
		Page page = Page.getPage();
		String requete = "";
		if(orderBy.equals("company.name")) {
			requete = REQUETEPAGECOMPUTERSSTART + " company.name " + page.getOrder().getValue()  + REQUETEPAGECOMPUTERSEND;
		} else {
			requete = REQUETEPAGECOMPUTERSSTART + "computer." + orderBy + " " + page.getOrder().getValue()  + REQUETEPAGECOMPUTERSEND;
		}
		logger.debug("Start of some of Computer : " + page.toString());
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("computerParPage", page.getNombreParPage()).addValue("positionComputers", indice);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return (List<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters,
				new ComputerRowMapper());
	}

	@Override
	public List<Computer> someSearch(String motRecherche, String orderBy){
		Page page = Page.getPage();
		int nombreComputers = nombre();
		page.setPeutAllerAncienneEtNouvellePage(nombreComputers);
		String requete = "";
		if(orderBy.equals("company.name")) {
			requete = REQUETEPAGECOMPUTERSSEARCH + " company.name " + page.getOrder().getValue()  + REQUETEPAGECOMPUTERSEND;
		} else {
			requete = REQUETEPAGECOMPUTERSSEARCH + "computer." + orderBy + " " + page.getOrder().getValue()  + REQUETEPAGECOMPUTERSEND;
		}
		int indice = (page.getNumeroPage()) * page.getNombreParPage();
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("motRecherche", motRecherche)
				.addValue("orderBy", orderBy + page.getOrder().getValue())
				.addValue("computerParPage", page.getNombreParPage()).addValue("positionComputers", indice);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return (List<Computer>) namedParameterJdbcTemplate.query(requete, namedParameters,
				new ComputerRowMapper());
	}

	@Override
	public List<Computer> all() throws Exception {
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return (List<Computer>) namedParameterJdbcTemplate.query(REQUETECOMPUTERS, namedParameters,
				new ComputerRowMapper());
	}

	@Override
	public Resultat verificationFonctionnementRequêteNonSelectUnique(int nombreLignes) throws Exception {
		if (nombreLignes == 1) {
			return Resultat.REUSSI;
		} else if (nombreLignes == 0) {
			throw new Exception("Un problème a occuré, l'opération de création de computer n'a pas été effectué.");
		} else {
			throw new Exception("Un problème a occuré, l'opération de création de computer a été efectué trop de fois ("
					+ nombreLignes + ").");
		}
	}

	@Override
	public Resultat create(Computer computer) throws Exception {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", computer.getName());
		parameters.put("introduced", computer.getIntroduced());
		parameters.put("discontinued", computer.getDiscontinued());
		if(computer.getCompany().getId() != 0) {
			parameters.put("company_id", computer.getCompany().getId());
		} else {
		}
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(connectionSQL.getDataSource())
				.withTableName("computer").usingGeneratedKeyColumns("id");
		Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
		if (id == null) {
			return Resultat.ECHOUE;
		} else {
			return Resultat.REUSSI;
		}
	}

	@Override
	public Resultat modify(Computer computer) throws Exception {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", computer.getId())
				.addValue("introduced", computer.getIntroduced()).addValue("discontinued", computer.getDiscontinued())
				.addValue("companyId", computer.getCompany().getId()).addValue("id", computer.getId());
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		int numberRow = namedParameterJdbcTemplate.update(REQUETEUPDATE, namedParameters);
		return this.verificationFonctionnementRequêteNonSelectUnique(numberRow);
	}

	@Override
	public Resultat delete(int computerId) throws Exception {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", computerId);
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		int numberRow = namedParameterJdbcTemplate.update(REQUETEDELETE, namedParameters);
		return this.verificationFonctionnementRequêteNonSelectUnique(numberRow);
	}

	@Override
	public Computer find(int computerId) throws Exception {
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("orderBy", "computer.id");
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
				connectionSQL.getDataSource());
		return namedParameterJdbcTemplate.queryForObject(REQUETEFINDCOMPUTERBYID, namedParameters,
				new ComputerRowMapper());
	}

	@Override
	public List<Computer> all(String pOrderBy) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> allSearch(String orderby, String search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
