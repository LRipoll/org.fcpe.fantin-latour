package org.fcpe.fantinlatour.dao.files;

import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.junit.Test;

public class PropertiesFactoryTest {

	@Test
	public void testCreate() {

		PropertiesFactory propertiesFactory = new PropertiesFactory();
		assertTrue( (propertiesFactory.create() instanceof Properties));
	}

	
	

}
