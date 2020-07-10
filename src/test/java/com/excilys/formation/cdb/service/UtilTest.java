package com.excilys.formation.cdb.service;

import junit.framework.TestCase;

public class UtilTest extends TestCase{    
	public UtilTest(String testMethodName) {
		super(testMethodName);
	}
	
	
	
	public static void testStringIsIntLettres() {
		assertFalse(Utility.stringIsInt("TestLettres"));
	}
	
	public static void testStringIsIntNull() {
		assertFalse(Utility.stringIsInt(null));
	}
	
	public static void testStringIsIntTrue() {
		assertTrue(Utility.stringIsInt("12574"));
	}
}
