package org.fcpe.fantinlatour.model;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class EntiteTest {

	@Test
	public void testBean() {

		JavaBeanTester.builder(Entite.class).test();
		

	}

}
