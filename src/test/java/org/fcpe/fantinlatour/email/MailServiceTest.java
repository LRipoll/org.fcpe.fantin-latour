package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.fcpe.fantinlatour.template.TemplateEngine;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailServiceTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	private InternetAddressFactory internetAddressFactory;
	private JavaMailSenderFactory javaMailSenderFactory;
	private TemplateEngine emailTemplateEngine;
	private MimeMessageHelperFactory mimeMessageHelperFactory;
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		
		internetAddressFactory = ctrl.createMock(InternetAddressFactory.class);
		 javaMailSenderFactory = ctrl.createMock(JavaMailSenderFactory.class);
		 emailTemplateEngine = ctrl.createMock(TemplateEngine.class);
		 mimeMessageHelperFactory = ctrl.createMock(MimeMessageHelperFactory.class);
	}
	@Test
	public void testSend() throws Exception {
		ctrl = support.createControl();
		
		TemplatableMailPreparator preparator = ctrl.createMock(TemplatableMailPreparator.class);
		
		MailSenderAccount mailSenderAccount  = ctrl.createMock(MailSenderAccount.class);
		JavaMailSender mailSender  = ctrl.createMock(JavaMailSender.class);
		
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
		EasyMock.expect(preparator.createMimeMessageHelper(mimeMessage)).andReturn(message);
		message.setText("htmlContent", true);
		EasyMock.expectLastCall().once();
		
		mailSender.send(mimeMessage);
		EasyMock.expectLastCall().once();		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		EasyMock.expect(conseilLocalEtablissement.getMailSenderAccount()).andReturn(mailSenderAccount);
		
		
		MailService mailService = create();
		mailService.onSelected(conseilLocalEtablissement);
		support.replayAll();
		
		mailService.send(preparator);
		
		support.verifyAll();
	}
	
	@Test
	public void testGetInternetAddressEmail() throws AddressException {
		

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		MailSenderAccount mailSenderAccount = ctrl.createMock(MailSenderAccount.class);

		EasyMock.expect(conseilLocalEtablissement.getMailSenderAccount()).andReturn(mailSenderAccount);
		String email = "email";
		EasyMock.expect(mailSenderAccount.getUsername()).andReturn(email);

		InternetAddress internetAddress = ctrl.createMock(InternetAddress.class);
		EasyMock.expect(internetAddressFactory.create(email)).andReturn(internetAddress);
		support.replayAll();
		MailService create = create();
		create.onSelected(conseilLocalEtablissement);
		assertEquals(internetAddress, create.getInternetAddressEmail());
		support.verifyAll();

	}
	

	@Test
	public void testGetConseilLocalEtablissementEmail() {
		MailService create = create();

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		MailSenderAccount mailSenderAccount = ctrl.createMock(MailSenderAccount.class);

		EasyMock.expect(conseilLocalEtablissement.getMailSenderAccount()).andReturn(mailSenderAccount);
		String email = "email";
		EasyMock.expect(mailSenderAccount.getUsername()).andReturn(email);

		support.replayAll();
		create.onSelected(conseilLocalEtablissement);
		assertEquals(email, create.getConseilLocalEtablissementEmail());
		support.verifyAll();

	}
	
	@Test
	public void testCreateMimeMessageHelper() throws MessagingException {
		MailService create = create();
		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);
		MimeMessageHelper mimeMessageHelper = ctrl.createMock(MimeMessageHelper.class);
		
		String encoding ="Encoding";
		EasyMock.expect(mimeMessageHelperFactory.create(mimeMessage, encoding )).andReturn(mimeMessageHelper);
		support.replayAll();
		assertSame(mimeMessageHelper, create.createMimeMessageHelper(mimeMessage, encoding));
		support.verifyAll();
	}
	private MailService create() {
		return new MailService(javaMailSenderFactory, emailTemplateEngine, internetAddressFactory, mimeMessageHelperFactory);
	}
}
