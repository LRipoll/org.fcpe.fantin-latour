package org.fcpe.fantinlatour.email;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	@Test
	public void testSend() throws MessagingException {
		ctrl = support.createControl();
		
		JavaMailSenderFactory javaMailSenderFactory = ctrl.createMock(JavaMailSenderFactory.class);
		EmailTemplateEngine emailTemplateEngine = ctrl.createMock(EmailTemplateEngine.class);
		
	
		TemplatableMailPreparator preparator = ctrl.createMock(TemplatableMailPreparator.class);
		
		
		JavaMailSender mailSender  = ctrl.createMock(JavaMailSender.class);
		MailSenderAccount mailSenderAccount  = ctrl.createMock(MailSenderAccount.class);
		EasyMock.expect(javaMailSenderFactory.create(mailSenderAccount)).andReturn(mailSender);
		
		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);
		EasyMock.expect(mailSender.createMimeMessage()).andReturn(mimeMessage);
	
		preparator.prepare(mimeMessage);
		EasyMock.expectLastCall().once();
		
		Map<String, Object> model = new HashMap<String, Object>();
		EasyMock.expect(preparator.getModel()).andReturn(model);
		
		EasyMock.expect(preparator.getTemplate()).andReturn("Template");
		EasyMock.expect(preparator.getEncoding()).andReturn("Encoding");
		
		EasyMock.expect(emailTemplateEngine.process(model, "Template", "Encoding")).andReturn("htmlContent");
		
		MimeMessageHelper message  = ctrl.createMock(MimeMessageHelper.class);
		EasyMock.expect(preparator.getMessageHelper(mimeMessage)).andReturn(message);
		message.setText("htmlContent", true);
		EasyMock.expectLastCall().once();
		
		mailSender.send(mimeMessage);
		EasyMock.expectLastCall().once();		
		
		MailService mailService = new MailService(javaMailSenderFactory, emailTemplateEngine);
		support.replayAll();
		
		mailService.send(preparator, mailSenderAccount);
		
		support.verifyAll();
	}
}
