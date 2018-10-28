package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.TemplateFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public abstract class AbstractTemplatableMailPreparator implements MimeMessagePreparator, TemplatableMailPreparator {

	private MimeMessageHelperFactory mimeMessageHelperFactory;
	private String encoding;
	private String template;
	private TemplateFactory templateFactory;
	
	private DateProvider dateProvider;
	private MailService mailService;
	

	public AbstractTemplatableMailPreparator(MimeMessageHelperFactory mimeMessageHelperFactory, TemplateFactory templateFactory,MailService mailService, DateProvider dateProvider, String template,
			String encoding) {
		super();
		this.mimeMessageHelperFactory = mimeMessageHelperFactory;
		this.templateFactory = templateFactory;
		this.template = template;
		this.encoding = encoding;
		this.dateProvider = dateProvider;
		this.mailService = mailService;
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
	
	protected MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage)
			throws MessagingException, AddressException {
		final MimeMessageHelper message = getMessageHelper(mimeMessage);
		message.setBcc(mailService.getConseilLocalEtablissementEmail());
		message.setFrom(mailService.getInternetAddressEmail());
		message.setSentDate(dateProvider.now());
		return message;
	}
	
	
	

}
