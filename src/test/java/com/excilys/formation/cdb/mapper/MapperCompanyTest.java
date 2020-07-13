package com.excilys.formation.cdb.mapper;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Company.CompanyBuilder;

import junit.framework.TestCase;

public class MapperCompanyTest extends TestCase {

	public MapperCompanyTest(String testMethodName) {
		super(testMethodName);
	}

	public static void testResultSetToCompanyNull() {
		try {
			assertTrue(MapperCompany.resultSetToCompany(null) == null);
		} catch (Exception e) {
		}
	}

	public static void testResultSetToCompanyEmpty() {
		try {
			ResultSet resultSet = mock(ResultSet.class);
			when(resultSet.isBeforeFirst()).thenReturn(true);
			when(resultSet.isAfterLast()).thenReturn(true);
			assertTrue(MapperCompany.resultSetToCompany(resultSet) == null);
		} catch (SQLException e) {
		} catch (Exception e) {
		}
	}
	
	public static void testResultSetToCompanyNoProblem() {
		try {
			ResultSet resultSet = mock(ResultSet.class);
			when(resultSet.isBeforeFirst()).thenReturn(false);
			when(resultSet.isAfterLast()).thenReturn(false);
			when(resultSet.getInt("id")).thenReturn(5);
			when(resultSet.getString("name")).thenReturn("goodName");
			Company companyExpected = new CompanyBuilder(5).withName("goodName").build();
			assertEquals(companyExpected,MapperCompany.resultSetToCompany(resultSet));
		} catch (SQLException e) {
		} catch (Exception e) {
		}
	}
	
	public static void testResultSetToCompanyWrongValue() {
		try {
			ResultSet resultSet = mock(ResultSet.class);
			when(resultSet.isBeforeFirst()).thenReturn(false);
			when(resultSet.isAfterLast()).thenReturn(false);
			when(resultSet.getInt("id")).thenReturn(5);
			when(resultSet.getString("name")).thenReturn("goodName");
			Company companyExpected = new CompanyBuilder(8).withName("badName").build();
			Assert.assertNotEquals(companyExpected,MapperCompany.resultSetToCompany(resultSet));
		} catch (SQLException e) {
		} catch (Exception e) {
		}
	}

	public static void testDataToCompanyNull() {
		Company companyExpected = new CompanyBuilder(0).withName(null).build();
		assertTrue(companyExpected.equals(MapperCompany.dataToCompany(0, null)));
	}

	public static void testDataToCompanyEmpty() {
		Company companyExpected = new CompanyBuilder(0).withName("").build();
		assertTrue(companyExpected.equals(MapperCompany.dataToCompany(0, "")));
	}

	public static void testDataToCompanyNoProblem() {
		Company companyExpected = new CompanyBuilder(5).withName("BonNom").build();
		assertTrue(companyExpected.equals(MapperCompany.dataToCompany(5, "BonNom")));
	}

	public static void testDataToCompanyWrongValue() {
		Company companyExpected = new CompanyBuilder(5).withName("BonNom").build();
		assertTrue(!companyExpected.equals(MapperCompany.dataToCompany(7, "MauvaisNom")));
	}
}
