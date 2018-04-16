package org.fcpe.fantinlatour.model;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class AssembleeTest {

	@Test
	public void testBean() {

		JavaBeanTester.builder(Assemblee.class).test();
		
	}

}
