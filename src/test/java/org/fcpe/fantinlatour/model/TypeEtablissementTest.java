package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class TypeEtablissementTest {

	@Test
	public void testBean() {

		JavaBeanTester.builder(TypeEtablissement.class).test();
	}
	
	@Test
	public void testToString() {

		assertEquals("org.fcpe.fantinlatour.etablissement.elementaire", TypeEtablissement.ELEMENTAIRE.toString());
	}

}
