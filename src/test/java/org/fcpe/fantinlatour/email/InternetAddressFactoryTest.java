package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.junit.Test;

public class InternetAddressFactoryTest {

	@Test
	public void testCreate() throws AddressException {
		InternetAddressFactory internetAddressFactory = new InternetAddressFactory();
		InternetAddress internetAddress = internetAddressFactory.create("email");
		assertEquals("email", internetAddress.getAddress());
	}
}
