package com.excilys.formation.cdb.mapper;

import junit.framework.TestCase;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.logging.Logging;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;

public class MapperComputerTest extends TestCase {
//    @Mock
//    private static ResultSet resultSet;

	public MapperComputerTest(String testMethodName) {
		super(testMethodName);
	}

	public static void testMapResultSetToListComputerNull() {
		Logging.getLogger().debug("Start of test MapperComputer 1 Null.");
		try {
			ResultSet resultSet = null;
			MapperComputer.mapResultSetToListComputer(resultSet);
			fail("An exception should habe been thrown.");
		} catch (SQLException e) {
		} catch (Exception e) {
		}
		Logging.getLogger().debug("End of test MapperComputer 1 Null.");
	}

	public static void testMapResultSetToListComputerEmpty() {
		Logging.getLogger().debug("Start of test MapperComputer 1 Empty.");
		try {
			ResultSet resultSet = mock(ResultSet.class);
			when(resultSet.next()).thenReturn(false);
			assertTrue(MapperComputer.mapResultSetToListComputer(resultSet).size() == 0);
		} catch (SQLException e) {
			fail("An exception should not be thrown : " + e.getLocalizedMessage());
		} catch (Exception e) {
			fail("An exception should not be thrown : " + e.getLocalizedMessage());
		}
		Logging.getLogger().debug("End of test MapperComputer 1 Empty.");
	}

	public static void testMapResultSetToListComputerNoProblem() {
		Logging.getLogger().debug("Start of test MapperComputer 1 NoProblem.");
		ResultSet resultSet = mock(ResultSet.class);
		try {
			when(resultSet.next()).thenReturn(true, false);
			when(resultSet.getInt("computer.company_id")).thenReturn(5);
			when(resultSet.getString("company.name")).thenReturn("plopCompany");
			when(resultSet.getInt("computer.id")).thenReturn(2);
			when(resultSet.getString("computer.name")).thenReturn("plop");
			when(resultSet.getDate("computer.introduced")).thenReturn(Date.valueOf(LocalDate.of(1111, 11, 11)));
			when(resultSet.getDate("computer.discontinued")).thenReturn(Date.valueOf(LocalDate.of(1999, 11, 11)));
			Company company = MapperCompany.dataToCompany(5, "plopCompany");
			Computer computerExpected = MapperComputer.dataToComputer(2, "plop",
					Date.valueOf(LocalDate.of(1111, 11, 11)), Date.valueOf(LocalDate.of(1999, 11, 11)), company);
			Logging.getLogger().debug("Middle of test MapperComputer 1 NoProblem.");
			assertTrue(computerExpected.equals(MapperComputer.mapResultSetToListComputer(resultSet).get(0)));
		} catch (SQLException e) {
			fail("An exception should not be thrown : " + e.getLocalizedMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			fail("An exception should not be thrown : " + e.getLocalizedMessage());
		} catch (Exception e) {
			fail("An exception should not be thrown : " + e.getLocalizedMessage());
		}
		Logging.getLogger().debug("End of test MapperComputer 1 NoProblem.");
	}

	public static void testMapResultSetToListComputerWrongValue() {
		Logging.getLogger().debug("Start of test MapperComputer 1 WrongValue.");
		ResultSet resultSet = mock(ResultSet.class);
		try {
			when(resultSet.next()).thenReturn(true);
			when(resultSet.getInt("computer.company_id")).thenReturn(5);
			when(resultSet.getString("company.name")).thenReturn("plopCompany");
			when(resultSet.getInt("computer.id")).thenReturn(2);
			when(resultSet.getString("computer.name")).thenReturn("plop");
			when(resultSet.getDate("computer.introduced")).thenReturn(Date.valueOf(LocalDate.of(1114, 11, 11)));
			when(resultSet.getDate("computer.discontinued")).thenReturn(Date.valueOf(LocalDate.of(1111, 11, 11)));
			MapperComputer.mapResultSetToListComputer(resultSet);
			fail("An exception should be thrown.");
		} catch (SQLException e) {
		} catch (Exception e) {
		}
		Logging.getLogger().debug("End of test MapperComputer 1 WrongValue.");
	}

	public static void testDataToComputerNull() {
		Logging.getLogger().debug("Start of test MapperComputer 2 Null.");
		try {
			MapperComputer.dataToComputer(0, null, null, null, null);
			fail("Exception expected because of the null name.");
		} catch (ParametresException e) {
		}
		Logging.getLogger().debug("End of test MapperComputer 2 Null.");
	}

	public static void testDataToComputerVide() {
		Logging.getLogger().debug("Start of test MapperComputer 2 Empty.");
		try {
			MapperComputer.dataToComputer(0, "", null, null, null);
			fail("Exception expected because of the empty name.");
		} catch (ParametresException e) {
		}
		Logging.getLogger().debug("End of test MapperComputer 2 Empty.");
	}

	public static void testDataToComputerNoProblem() {
		Logging.getLogger().debug("Start of test MapperComputer 2 NoProblem.");
		try {
			Company company = new CompanyBuilder(15).withName("company").build();
			Computer computerExpected = new ComputerBuilder("Plop").byCompany(company)
					.introducedThe(LocalDate.of(1999, 12, 12)).discontinuedThe(LocalDate.of(2111, 11, 11)).withId(0)
					.build();
			Computer computer;
			computer = MapperComputer.dataToComputer(0, "Plop", Date.valueOf(LocalDate.of(1999, 12, 12)),
					Date.valueOf(LocalDate.of(2111, 11, 11)), company);
			assertTrue(computerExpected.equals(computer));
		} catch (ParametresException e) {
			fail("An exception has been thrown : " + e.getLocalizedMessage());
		}
		Logging.getLogger().debug("End of test MapperComputer 2 NoProblem.");
	}

	public static void testDataToComputerWrongValue() {
		Logging.getLogger().debug("Start of test MapperComputer 2 WrongValue.");
		try {

			Company company = new CompanyBuilder(15).withName("company").build();
			new ComputerBuilder("Plop").byCompany(company).introducedThe(LocalDate.of(1999, 12, 12))
					.discontinuedThe(LocalDate.of(1988, 11, 11)).withId(0).build();
			fail("An IllegalArgumentException should be thrown.");
		} catch (IllegalArgumentException e) {
		}
		Logging.getLogger().debug("End of test MapperComputer 2 WrongValue.");
	}

	public static void testDoublerAntislashVide() {
		Logging.getLogger().debug("Start of test MapperComputer 3 Empty.");
		assertEquals(MapperComputer.doublerAntislash(""), "");
		Logging.getLogger().debug("End of test MapperComputer 3 Empty.");
	}

	public static void testDoublerAntislashNull() {
		Logging.getLogger().debug("Start of test MapperComputer 3 Null.");
		assertEquals(MapperComputer.doublerAntislash(null), null);
		Logging.getLogger().debug("End of test MapperComputer 3 Null.");
	}

	public static void testDoublerAntislashTrue() {
		Logging.getLogger().debug("Start of test MapperComputer 3 True.");
		assertEquals(MapperComputer.doublerAntislash("La \\ ca \n ici \\\\"), "La \\\\ ca \n ici \\\\\\\\");
		Logging.getLogger().debug("End of test MapperComputer 3 True.");
	}
}
