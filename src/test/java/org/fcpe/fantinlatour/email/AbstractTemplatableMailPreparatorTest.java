package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertSame;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.template.TemplateFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.MimeMessageHelper;

public class AbstractTemplatableMailPreparatorTest {

	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;


	private String encoding;
	private String template;
	private TemplateFactory modelFactory;
	private MailService mailService;
	private DateProvider dateProvider;

	@Test
	public void testCreate() throws MessagingException {
		ctrl = support.createControl();
	}

	@Before
	public void setup() {
		ctrl = support.createControl();
		
		modelFactory = ctrl.createMock(TemplateFactory.class);
		mailService = ctrl.createMock(MailService.class);
		dateProvider = ctrl.createMock(DateProvider.class);
		encoding = "Encoding";
		template = "Template";
	}

	private AbstractTemplatableMailPreparator create() {
		return new FakeTemplatableMailPreparator( modelFactory, mailService,
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
	public void testGetModel() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();

		Map<String, Object> model = new HashMap<String, Object>();
		EasyMock.expect(modelFactory.create()).andReturn(model);
		support.replayAll();
		assertSame(model, create.getModel());
		support.verifyAll();
	}

	@Test
	public void testCreateMimeMessageHelper() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();

		String email = "email";
		EasyMock.expect(mailService.getConseilLocalEtablissementEmail()).andReturn(email);


		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);

		MimeMessageHelper mimeMessageHelper = ctrl.createMock(MimeMessageHelper.class);
		EasyMock.expect(mailService.createMimeMessageHelper(mimeMessage, encoding)).andReturn(mimeMessageHelper);
		mimeMessageHelper.setBcc(email);
		EasyMock.expectLastCall().once();
		
		
		InternetAddress internetAddress= ctrl.createMock(InternetAddress.class);
		EasyMock.expect(mailService.getInternetAddressEmail()).andReturn(internetAddress);
		
		mimeMessageHelper.setFrom(internetAddress);
		EasyMock.expectLastCall().once();
		
		Date now = ctrl.createMock(Date.class);
		EasyMock.expect(dateProvider.now()).andReturn(now);
		mimeMessageHelper.setSentDate(now);
		EasyMock.expectLastCall().once();
		
		support.replayAll();

		

		assertSame(mimeMessageHelper, create.createMimeMessageHelper(mimeMessage));
		support.verifyAll();
	}

}
