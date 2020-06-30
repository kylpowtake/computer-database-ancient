package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.time.LocalDate;

import com.excilys.formation.cdb.exception.ParametresException;
import com.excilys.formation.cdb.mapper.MapperComputer.TypeDate;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

import junit.framework.TestCase;

public class MapperComputerTest extends TestCase{
	public MapperComputerTest(String testMethodName) {
		super(testMethodName);
	}

	
	
	public static void testDataToComputerTrue() {
		try {
			assertEquals(new Computer(5, "nom", LocalDate.of(2222, 12, 12), LocalDate.of(2223, 12, 12), new Company(5, "plop")), 
					MapperComputer.dataToComputer(5, "nom", Date.valueOf(LocalDate.of(2222, 12, 12)), Date.valueOf(LocalDate.of(2223, 12, 12)), 5, "plop"));
		} catch (ParametresException e) {}
	}
	
	
	
//	public static void testStringToComputerAvecIdLimite() {
//		try {
//			MapperComputer.stringToComputerAvecId("5", "nom", "1447-64-68", "1555-47-48", null);
//			fail("Absence de la levée de l'exception ParametresException - données null.");
//		} catch (ParametresException e) {}
//	} 
	public static void testStringToComputerAvecIdNull() {
		try {
			MapperComputer.stringToComputerAvecId(null, null, null, null, null);
			fail("Absence de la levée de l'exception ParametresException - données null.");
		} catch (ParametresException e) {}
	} 
	public static void testStringToComputerAvecIdTrue() {
		try {
			assertEquals(new Computer(5, "nom", LocalDate.of(1447, 12, 12), LocalDate.of(1555, 12, 12), null), 
					MapperComputer.stringToComputerAvecId("5", "nom", "1447-12-12", "1555-12-12", null));
		} catch (ParametresException e) {}
	} 
	
	
	
	public static void testStringToComputerLimite() {
		try {
			assertEquals(new Computer(0, "nom", null, null, null), MapperComputer.stringToComputer("nom", "1447-64-68", "1555-47-48", null));
			
		} catch (ParametresException e) {}
	} 
	public static void testStringToComputerNull() {
		try {
			assertEquals(new Computer(0, "nom", LocalDate.of(1447, 12, 12), LocalDate.of(1555, 12, 12), null), 
					MapperComputer.stringToComputer(null, null, null, null));
			fail("Absence de la levée de l'exception ParametresException - données null.");
		} catch (ParametresException e) {}
	} 
	public static void testStringToComputer() {
		try {
			assertEquals(new Computer(0, "nom", LocalDate.of(1447, 12, 12), LocalDate.of(1555, 12, 12), null), 
					MapperComputer.stringToComputer("nom", "1447-12-12", "1555-12-12", null));
		} catch (ParametresException e) {}
	} 
	
	
	
	public static void testDateStringToLocalDateLimite() {
		try {
			MapperComputer.dateStringToLocalDate("1487-60-54");
			fail("Absence de la levée de l'exception ParametresException - Mois impossible");
		} catch (ParametresException e) {}
	} 	 
	public static void testDateStringToLocalDateTrop() {
		try {
			MapperComputer.dateStringToLocalDate("1487-08-11-11");
			fail("Absence de la levée de l'exception ParametresException - Trop d'éléments dans la liste.");
		} catch (ParametresException e) {}
	} 	 
	public static void testDateStringToLocalDateNull() {
		try {
			assertEquals(MapperComputer.dateStringToLocalDate(null), null);
		} catch (ParametresException e) {
		}
	} 	 
	public static void testDateStringToLocalDateLettres() {
		try {
			MapperComputer.dateStringToLocalDate("Test-op-lk");
			fail("Absence de la levée de l'exception ParametresException - les lettres ne sont pas acceptées.");
		} catch (ParametresException e) {}
	} 	 
	public static void testDateStringToLocalDateTrue() {
		try {
			assertEquals(LocalDate.of(1498, 8, 11), MapperComputer.dateStringToLocalDate("1498-08-11"));
		} catch (ParametresException e) {}
	} 
	
	
	
	public static void testTableauStringToLocalDateLimite() {
		try {
			MapperComputer.tableauStringToLocalDate(new String[] {"1444", "60", "12"});
			fail("Absence de la levée de l'exception ParametresException - Mois impossible.");
		} catch (ParametresException e) {}
	} 
	public static void testTableauStringToLocalDateTrop() {
		try {
			MapperComputer.tableauStringToLocalDate(new String[] {"1444", "12", "12", "12"});
			fail("Absence de la levée de l'exception ParametresException - Trop d'éléments dans le tableau.");
		} catch (ParametresException e) {}
	} 
	public static void testTableauStringToLocalDateNull() {
		try {
			MapperComputer.tableauStringToLocalDate(null);
			fail("Absence de la levée de l'exception ParametresException - l'argument est null.");
		} catch (ParametresException e) {}
	} 
	public static void testTableauStringToLocalDateLettres() {
		try {
			MapperComputer.tableauStringToLocalDate(new String[] {"chch", "11", "11"});
			fail("Absence de la levée de l'exception ParametresException - l'argument contient des lettres.");
		} catch (ParametresException e) {}
	} 
	public static void testTableauStringToLocalDateTrue() {
		try {
			assertEquals(LocalDate.of(1454, 11, 11), MapperComputer.tableauStringToLocalDate(new String[] {"1454", "11", "11"}));
		} catch (ParametresException e) {}
	} 
	
	

	public static void testChangerDateStringToIntTrop() {
		assertEquals(0, MapperComputer.changerDateStringToInt("19478", TypeDate.YEAR));
	} 
	public static void testChangerDateStringToIntNull() {
		assertEquals(0, MapperComputer.changerDateStringToInt(null, TypeDate.YEAR));
	} 	
	public static void testChangerDateStringToIntLettres() {
		assertEquals(0, MapperComputer.changerDateStringToInt("test", TypeDate.YEAR));
	} 	
	public static void testChangerDateStringToIntTrue() {
		assertEquals(1999, MapperComputer.changerDateStringToInt("1999", TypeDate.YEAR));
	}
	
	
	
	public static void testDoublerAntislashVide() {
		assertEquals(MapperComputer.doublerAntislash(""), "");
	}
	public static void testDoublerAntislashNull() {
		assertEquals(MapperComputer.doublerAntislash(null), null);
	}
	public static void testDoublerAntislashTrue() {
		assertEquals(MapperComputer.doublerAntislash("La \\ ca \n ici \\\\"), "La \\\\ ca \n ici \\\\\\\\");
	}
}
