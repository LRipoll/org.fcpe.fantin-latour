package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;


public class MailSenderPropertiesTest {

	private EasyMockSupport support = new EasyMockSupport();
	
	@Test
	public void testBean() {
		
		IMocksControl ctrl = support.createControl();
		
		EmailSenderProtocolPropertiesFactory emailSenderProtocolPropertiesFactory = ctrl.createMock(EmailSenderProtocolPropertiesFactory.class);
		EmailSenderProtocol protocol = EmailSenderProtocol.SMTP;
		
		IEmailSenderProtocolProperties emailSenderProtocolProperties = ctrl.createMock(IEmailSenderProtocolProperties.class);
		
		EasyMock.expect(emailSenderProtocolPropertiesFactory.create(protocol)).andReturn(emailSenderProtocolProperties).anyTimes();
		support.replayAll();
		
		MailSenderProperties test  = new MailSenderProperties(protocol, "host", 0, "password", "defaultEncoding",emailSenderProtocolPropertiesFactory);
		support.verifyAll();
		
		assertSame(emailSenderProtocolProperties, test.getEmailSenderProtocolProperties());
		assertEquals(protocol,test.getProtocol());
		assertEquals("host",test.getHost());
		assertEquals(0,test.getPort());
		assertEquals("password",test.getPassword());
		assertEquals("defaultEncoding",test.getDefaultEncoding());
		
		
	}
}
