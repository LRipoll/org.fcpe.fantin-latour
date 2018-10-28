package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManagerListener;
import org.fcpe.fantinlatour.template.TemplateEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService implements ConseilLocalEtablissementManagerListener {

	private JavaMailSenderFactory javaMailSenderFactory = null;

	private TemplateEngine emailTemplateEngine = null;

	private ConseilLocalEtablissement conseilLocalEtablissement;

	private InternetAddressFactory internetAddressFactory;

	public MailService(JavaMailSenderFactory javaMailSenderFactory, TemplateEngine emailTemplateEngine, InternetAddressFactory internetAddressFactory) {
		super();
		this.javaMailSenderFactory = javaMailSenderFactory;
		this.emailTemplateEngine = emailTemplateEngine;
		this.internetAddressFactory = internetAddressFactory;
	}

	public void send(TemplatableMailPreparator preparator) throws MessagingException {
		JavaMailSender mailSender = javaMailSenderFactory.create(conseilLocalEtablissement.getMailSenderAccount());
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

	@Override
	public void onSelected(ConseilLocalEtablissement conseilLocalEtablissement) {
		this.conseilLocalEtablissement = conseilLocalEtablissement;

	}
	
	String getConseilLocalEtablissementEmail() {
		return conseilLocalEtablissement.getMailSenderAccount().getUsername();
	}
	
	InternetAddress getInternetAddressEmail() throws AddressException {
		return internetAddressFactory.create(getConseilLocalEtablissementEmail());
	}

}
