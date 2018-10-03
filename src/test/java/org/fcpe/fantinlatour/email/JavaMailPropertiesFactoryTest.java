package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.fcpe.fantinlatour.model.SMTPProperties;
import org.junit.Test;

public class JavaMailPropertiesFactoryTest {

	@Test
	public void testCreate() {
		JavaMailPropertiesFactory factory = new JavaMailPropertiesFactory();
		SMTPProperties properties = new SMTPProperties();
		properties.setAuth(true);
		properties.setStarttls(true);
		Properties test = factory.create(properties);
		
		
		assertEquals("true", test.getProperty("mail.smtp.auth"));
		assertEquals("true", test.getProperty("mail.smtp.starttls.enable"));
		assertEquals("smtp", test.getProperty("mail.transport.protocol"));
	}
}
