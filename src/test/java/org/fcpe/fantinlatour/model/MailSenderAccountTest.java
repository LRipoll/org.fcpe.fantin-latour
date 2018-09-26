package org.fcpe.fantinlatour.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;

public class MailSenderAccountTest {

	private EasyMockSupport support = new EasyMockSupport();
	
	@Test
	public void testConstructor() {
		
		IMocksControl ctrl = support.createControl();
		MailSenderProperties mailSenderProperties = ctrl.createMock(MailSenderProperties.class);
		MailSenderAccount test  = new MailSenderAccount("username", mailSenderProperties);
		assertEquals("username",test.getUsername());
		assertSame(mailSenderProperties,test.getProperties());
	
	}
}
