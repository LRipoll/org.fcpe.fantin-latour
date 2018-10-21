package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {

	private JavaMailSenderFactory javaMailSenderFactory = null;

	private EmailTemplateEngine emailTemplateEngine = null;

	public MailService(JavaMailSenderFactory javaMailSenderFactory, EmailTemplateEngine emailTemplateEngine) {
		super();
		this.javaMailSenderFactory = javaMailSenderFactory;
		this.emailTemplateEngine = emailTemplateEngine;
	}

	public void send(TemplatableMailPreparator preparator, MailSenderAccount mailSenderAccount)
			throws MessagingException {
		JavaMailSender mailSender = javaMailSenderFactory.create(mailSenderAccount);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		preparator.prepare(mimeMessage);

		Map<String, Object> model = preparator.getModel();

		String template = preparator.getTemplate();
		String encoding = preparator.getEncoding();
		String htmlContent = emailTemplateEngine.process(model, template, encoding);

		MimeMessageHelper message = preparator.getMessageHelper(mimeMessage);
		message.setText(htmlContent, true);
		mailSender.send(mimeMessage);

	}

}
