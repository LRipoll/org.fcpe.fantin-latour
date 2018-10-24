package org.fcpe.fantinlatour.email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class InternetAddressFactory {

	public InternetAddress create(String email) throws AddressException {
		return new InternetAddress(email);
	}
}
