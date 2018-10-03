package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Properties;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.EmailSenderProtocol;
import org.fcpe.fantinlatour.model.EmailSenderProtocolProperties;
import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.fcpe.fantinlatour.model.MailSenderProperties;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class JavaMailSenderFactoryTest {

	
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	

	@Test
	public void testCreate() {
		
		
		ctrl = support.createControl();
		
		EmailSenderProtocol protocol = EmailSenderProtocol.SMTP;
	
		
		EmailSenderProtocolConverter protocolConverter = ctrl.createMock(EmailSenderProtocolConverter.class);
		EasyMock.expect(protocolConverter.convert(protocol)).andReturn("protocol");
		
		MailSenderAccount account = ctrl.createMock(MailSenderAccount.class);
		EasyMock.expect(account.getUsername()).andReturn("username");
		
		MailSenderProperties properties = ctrl.createMock(MailSenderProperties.class);
		EasyMock.expect(account.getProperties()).andReturn(properties);
		
		EasyMock.expect(properties.getDefaultEncoding()).andReturn("defaultEncoding");
		EasyMock.expect(properties.getHost()).andReturn("host");
		EasyMock.expect(properties.getPassword()).andReturn("password");
		EasyMock.expect(properties.getPort()).andReturn(0);
		EasyMock.expect(properties.getProtocol()).andReturn(protocol);
		
		EmailSenderProtocolProperties emailSenderProtocolProperties = ctrl.createMock(EmailSenderProtocolProperties.class);
		EasyMock.expect(properties.getEmailSenderProtocolProperties()).andReturn(emailSenderProtocolProperties);
		
		Properties javaProperties = ctrl.createMock(Properties.class);
		JavaMailPropertiesFactory javaMailPropertiesFactory = ctrl.createMock(JavaMailPropertiesFactory.class);
		EasyMock.expect(javaMailPropertiesFactory.create(EasyMock.anyObject(EmailSenderProtocolProperties.class))).andReturn(javaProperties);
		
		support.replayAll();
		JavaMailSenderFactory factory = new JavaMailSenderFactory(protocolConverter, javaMailPropertiesFactory);
		JavaMailSenderImpl test = (JavaMailSenderImpl) factory.create(account);
		
		support.verifyAll();
		
		assertEquals("username",test.getUsername());
		assertEquals("host",test.getHost());
		assertEquals(0,test.getPort());
		assertEquals("password",test.getPassword());
		assertEquals("defaultEncoding",test.getDefaultEncoding());
		assertEquals("protocol",test.getProtocol());
		assertSame(javaProperties, test.getJavaMailProperties());

	}
}
