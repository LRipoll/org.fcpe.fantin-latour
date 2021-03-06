package org.fcpe.fantinlatour.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.AnneeScolaireTemplateFactory;
import org.springframework.mail.javamail.MimeMessageHelper;

public class DeleguesTemplateMailPreparator extends AbstractTemplatableMailPreparator {

	public DeleguesTemplateMailPreparator(AnneeScolaireTemplateFactory templateFactory, MailService mailService,
			DateProvider dateProvider, String template, String encoding) {
		super(templateFactory, mailService, dateProvider, template, encoding);
	}

	@Override
	public void prepare(MimeMessage mimeMessage) throws MessagingException {
		final MimeMessageHelper message = createMimeMessageHelper(mimeMessage);
		message.setTo("mailinglist@groupe.com");
		message.setSubject("Point sur les délégués de classes du collège Fantin Latour et autres informations");

	}

}
