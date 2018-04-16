package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class TypeEnseignementTest {

	@Test
	public void testBean() {

		JavaBeanTester.builder(TypeEnseignement.class).test();
	}
	
	@Test
	public void testToString() {

		assertEquals("org.fcpe.fantinlatour.enseignement.elementaire", TypeEnseignement.ELEMENTAIRE.toString());
	}

}
