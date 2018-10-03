package org.fcpe.fantinlatour.model;

import org.junit.Test;

import com.codebox.bean.JavaBeanTester;

public class SMTPPropertiesTest {

	@Test
	public void testBean() {

		JavaBeanTester.builder(SMTPProperties.class).test();
		

	}
}
