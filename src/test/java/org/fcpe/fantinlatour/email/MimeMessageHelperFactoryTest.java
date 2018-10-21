package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Test;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MimeMessageHelperFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	
	
	@Test
	public void testCreate() throws MessagingException {
		ctrl = support.createControl();
		MimeMessage mimeMessage =  ctrl.createMock(MimeMessage.class);
		mimeMessage.setContent(EasyMock.anyObject( Multipart.class));
				
		MimeMessageHelperFactory mimeMessageHelperFactory = new MimeMessageHelperFactory();
		support.replayAll();
		
		MimeMessageHelper mimeMessageHelper = mimeMessageHelperFactory.create(mimeMessage, "UTF8");
		
		support.verifyAll();
		assertNotNull(mimeMessageHelper);
		assertSame(mimeMessage,mimeMessageHelper.getMimeMessage());
		assertEquals("UTF8",mimeMessageHelper.getEncoding());
		assertSame(mimeMessageHelper,mimeMessageHelperFactory.create(mimeMessage, "UTF8"));
		
	}
}
