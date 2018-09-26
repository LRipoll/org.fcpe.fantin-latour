package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Paramètres")
public class MailSenderPropertiesTest {

	@Test
	public void testBean() {
		MailSenderProperties test  = new MailSenderProperties("protocol", "host", 0, "password", "defaultEncoding");
		assertEquals("protocol",test.getProtocol());
		assertEquals("host",test.getHost());
		assertEquals(0,test.getPort());
		assertEquals("password",test.getPassword());
		assertEquals("defaultEncoding",test.getDefaultEncoding());
	}
}
