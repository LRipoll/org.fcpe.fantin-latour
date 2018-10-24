package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.TemplateFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public abstract class AbstractTemplatableMailPreparator implements MimeMessagePreparator, TemplatableMailPreparator {

	private MimeMessageHelperFactory mimeMessageHelperFactory;
	private String encoding;
	private String template;
	private TemplateFactory templateFactory;

	public AbstractTemplatableMailPreparator(MimeMessageHelperFactory mimeMessageHelperFactory, TemplateFactory templateFactory, String template,
			String encoding) {
		super();
		this.mimeMessageHelperFactory = mimeMessageHelperFactory;
		this.templateFactory = templateFactory;
		this.template = template;
		this.encoding = encoding;
	}

	public MimeMessageHelper getMessageHelper(MimeMessage mimeMessage) throws MessagingException {

		return mimeMessageHelperFactory.create(mimeMessage, encoding);
	}

	@Override
	public String getEncoding() {
		return encoding;
	}

	public String getTemplate() {
		return template;
	}

	@Override
	public Map<String, Object> getModel() {
		
		return templateFactory.create();
	}
	
	

}
