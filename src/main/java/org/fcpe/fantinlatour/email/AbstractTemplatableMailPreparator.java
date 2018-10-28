package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.TemplateFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public abstract class AbstractTemplatableMailPreparator implements MimeMessagePreparator, TemplatableMailPreparator {

	
	private String encoding;
	private String template;
	private TemplateFactory templateFactory;
	
	private DateProvider dateProvider;
	private MailService mailService;
	

	public AbstractTemplatableMailPreparator(TemplateFactory templateFactory,MailService mailService, DateProvider dateProvider, String template,
			String encoding) {
		super();
		
		this.templateFactory = templateFactory;
		this.template = template;
		this.encoding = encoding;
		this.dateProvider = dateProvider;
		this.mailService = mailService;
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
	
	@Override
	public MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage) throws MessagingException{
		final MimeMessageHelper message = mailService.createMimeMessageHelper(mimeMessage, encoding);
		message.setBcc(mailService.getConseilLocalEtablissementEmail());
		message.setFrom(mailService.getInternetAddressEmail());
		message.setSentDate(dateProvider.now());
		return message;
	}
	
	
	

}
