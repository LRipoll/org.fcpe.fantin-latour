package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Date;
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
import org.fcpe.fantinlatour.template.TemplateFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AbstractTemplatableMailPreparatorTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;

	private MimeMessageHelperFactory mimeMessageHelperFactory;
	private String encoding;
	private String template;
	private TemplateFactory modelFactory;
	private InternetAddressFactory internetAddressFactory;
	private DateProvider dateProvider;

	@Test
	public void testCreate() throws MessagingException {
		ctrl = support.createControl();
	}

	@Before
	public void setup() {
		ctrl = support.createControl();
		mimeMessageHelperFactory = ctrl.createMock(MimeMessageHelperFactory.class);
		modelFactory = ctrl.createMock(TemplateFactory.class);
		internetAddressFactory = ctrl.createMock(InternetAddressFactory.class);
		dateProvider = ctrl.createMock(DateProvider.class);
		encoding = "Encoding";
		template = "Template";
	}

	private AbstractTemplatableMailPreparator create() {
		return new FakeTemplatableMailPreparator(mimeMessageHelperFactory, modelFactory, internetAddressFactory,
				dateProvider, template, encoding);
	}

	@Test
	public void testGetTemplate() {
		assertSame(template, create().getTemplate());
	}

	@Test
	public void testGetEncoding() {
		assertSame(encoding, create().getEncoding());
	}

	@Test
	public void testGetMessageHelper() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();
		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);
		MimeMessageHelper mimeMessageHelper = ctrl.createMock(MimeMessageHelper.class);
		EasyMock.expect(mimeMessageHelperFactory.create(mimeMessage, encoding)).andReturn(mimeMessageHelper);
		support.replayAll();
		assertSame(mimeMessageHelper, create.getMessageHelper(mimeMessage));
		support.verifyAll();
	}

	@Test
	public void testGetModel() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();

		Map<String, Object> model = new HashMap<String, Object>();
		EasyMock.expect(modelFactory.create()).andReturn(model);
		support.replayAll();
		assertSame(model, create.getModel());
		support.verifyAll();
	}

	@Test
	public void testGetConseilLocalEtablissementEmail() {
		AbstractTemplatableMailPreparator create = create();

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
	public void testGetInternetAddressEmail() throws AddressException {
		AbstractTemplatableMailPreparator create = create();

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		MailSenderAccount mailSenderAccount = ctrl.createMock(MailSenderAccount.class);

		EasyMock.expect(conseilLocalEtablissement.getMailSenderAccount()).andReturn(mailSenderAccount);
		String email = "email";
		EasyMock.expect(mailSenderAccount.getUsername()).andReturn(email);

		InternetAddress internetAddress = ctrl.createMock(InternetAddress.class);
		EasyMock.expect(internetAddressFactory.create(email)).andReturn(internetAddress);
		support.replayAll();
		create.onSelected(conseilLocalEtablissement);
		assertEquals(internetAddress, create.getInternetAddressEmail());
		support.verifyAll();

	}

	@Test
	public void testCreateMimeMessageHelper() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();

		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);

		MailSenderAccount mailSenderAccount = ctrl.createMock(MailSenderAccount.class);

		EasyMock.expect(conseilLocalEtablissement.getMailSenderAccount()).andReturn(mailSenderAccount).anyTimes();
		String email = "email";
		EasyMock.expect(mailSenderAccount.getUsername()).andReturn(email).anyTimes();

		InternetAddress internetAddress = ctrl.createMock(InternetAddress.class);
		EasyMock.expect(internetAddressFactory.create(email)).andReturn(internetAddress);

		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);

		MimeMessageHelper mimeMessageHelper = ctrl.createMock(MimeMessageHelper.class);
		EasyMock.expect(mimeMessageHelperFactory.create(mimeMessage, encoding)).andReturn(mimeMessageHelper);
		mimeMessageHelper.setBcc(email);
		EasyMock.expectLastCall().once();
		
		mimeMessageHelper.setFrom(internetAddress);
		EasyMock.expectLastCall().once();
		
		Date now = ctrl.createMock(Date.class);
		EasyMock.expect(dateProvider.now()).andReturn(now);
		mimeMessageHelper.setSentDate(now);
		EasyMock.expectLastCall().once();
		
		support.replayAll();

		create.onSelected(conseilLocalEtablissement);

		assertSame(mimeMessageHelper, create.createMimeMessageHelper(mimeMessage));
		support.verifyAll();
	}

}
