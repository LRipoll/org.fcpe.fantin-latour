package org.fcpe.fantinlatour.email;

import static org.junit.Assert.assertSame;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
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
	
	private MimeMessageHelperFactory mimeMessageHelperFactory;
	private String encoding;
	private String template;
	private TemplateFactory modelFactory;
	
	
	@Test
	public void testCreate() throws MessagingException {
		ctrl = support.createControl();
	}
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		mimeMessageHelperFactory = ctrl.createMock(MimeMessageHelperFactory.class);
		modelFactory = ctrl.createMock(TemplateFactory.class);
		encoding = "Encoding";
		template = "Template";
	}
	
	private AbstractTemplatableMailPreparator create() {
		return new FakeTemplatableMailPreparator(mimeMessageHelperFactory, modelFactory, template, encoding);
	}
	
	@Test
	public void testGetTemplate() {
		assertSame(template,create().getTemplate());
	}
	
	@Test
	public void testGetEncoding() {
		assertSame(encoding,create().getEncoding());
	}
	
	@Test
	public void testGetMessageHelper() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();
		MimeMessage mimeMessage = ctrl.createMock(MimeMessage.class);
		MimeMessageHelper mimeMessageHelper= ctrl.createMock(MimeMessageHelper.class);
		EasyMock.expect(mimeMessageHelperFactory.create(mimeMessage, encoding)).andReturn(mimeMessageHelper);
		support.replayAll();
		assertSame(mimeMessageHelper,create.getMessageHelper(mimeMessage ));
		support.verifyAll();
	}
	
	@Test
	public void testGetModel() throws MessagingException {
		AbstractTemplatableMailPreparator create = create();
		
		Map<String, Object> model = new HashMap<String, Object>();
		EasyMock.expect(modelFactory.create()).andReturn(model);
		support.replayAll();
		assertSame(model,create.getModel());
		support.verifyAll();
	}
	
}
