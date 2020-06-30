package com.excilys.formation.cdb.model;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class CompanyTest extends TestCase {
	private static Company company;

	public CompanyTest(String testMethodName) {
		super(testMethodName);
	}

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		company = new Company(5, "Company number one ou cinq.");
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
		company = null;
	}

	@Test
	public static void testEqualsFalse() {
		Company company2 = new Company(5, "Company number one ou cinq, plop.");
		Assert.assertNotEquals(company, company2
				);
	}

	@Test
	public static void testEqualsTrue() {
		Company company2 = new Company(5, "Company number one ou cinq.");
		assertEquals(company.getId(), company2.getId());
		assertEquals(company.getName(), company2.getName());
	}

}
