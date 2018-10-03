package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailSenderProtocolPropertiesFactoryTest {

	@Test
	public void testCreate() {
		EmailSenderProtocolPropertiesFactory factory = new EmailSenderProtocolPropertiesFactory();
		EmailSenderProtocol protocol = EmailSenderProtocol.SMTP;
		assertTrue( factory.create(protocol) instanceof SMTPProperties);
	}
}
