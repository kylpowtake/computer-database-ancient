package com.excilys.formation.cdb.service;

import junit.framework.TestCase;

public class UtilTest extends TestCase{    
	public UtilTest(String testMethodName) {
		super(testMethodName);
	}
	
	
	
	public static void testStringIsIntLettres() {
		assertFalse(Util.stringIsInt("TestLettres"));
	}
	
	public static void testStringIsIntNull() {
		assertFalse(Util.stringIsInt(null));
	}
	
	public static void testStringIsIntTrue() {
		assertTrue(Util.stringIsInt("12574"));
	}
}
