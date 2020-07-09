package com.excilys.formation.cdb.mapper;

import junit.framework.TestCase;

public class MapperComputerTest extends TestCase{
	public MapperComputerTest(String testMethodName) {
		super(testMethodName);
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
