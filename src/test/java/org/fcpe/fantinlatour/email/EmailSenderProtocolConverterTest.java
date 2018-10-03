package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;

import org.fcpe.fantinlatour.model.EmailSenderProtocol;
import org.junit.Test;

public class EmailSenderProtocolConverterTest {

	@Test
	public void testConvert() {
		EmailSenderProtocolConverter converter = new EmailSenderProtocolConverter();
		EmailSenderProtocol protocol = EmailSenderProtocol.SMTP;
		assertEquals("smtp", converter.convert(protocol));
	}
}
