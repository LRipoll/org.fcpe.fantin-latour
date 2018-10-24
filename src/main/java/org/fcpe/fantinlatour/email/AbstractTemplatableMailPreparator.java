package org.fcpe.fantinlatour.email;

import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManagerListener;
import org.fcpe.fantinlatour.template.TemplateFactory;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public abstract class AbstractTemplatableMailPreparator implements MimeMessagePreparator, TemplatableMailPreparator, ConseilLocalEtablissementManagerListener {

	private MimeMessageHelperFactory mimeMessageHelperFactory;
	private String encoding;
	private String template;
	private TemplateFactory templateFactory;
	private ConseilLocalEtablissement conseilLocalEtablissement;
	private InternetAddressFactory internetAddressFactory;
	private DateProvider dateProvider;
	

	public AbstractTemplatableMailPreparator(MimeMessageHelperFactory mimeMessageHelperFactory, TemplateFactory templateFactory, InternetAddressFactory internetAddressFactory, DateProvider dateProvider, String template,
			String encoding) {
		super();
		this.mimeMessageHelperFactory = mimeMessageHelperFactory;
		this.templateFactory = templateFactory;
		this.template = template;
		this.encoding = encoding;
		this.internetAddressFactory = internetAddressFactory;
		this.dateProvider = dateProvider;
		
		
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
	
	@Override
	public void onSelected(ConseilLocalEtablissement conseilLocalEtablissement) {
		this.conseilLocalEtablissement = conseilLocalEtablissement;
		
	}
	
	protected String getConseilLocalEtablissementEmail() {
		return conseilLocalEtablissement.getMailSenderAccount().getUsername();
	}
	
	protected InternetAddress getInternetAddressEmail() throws AddressException {
		return internetAddressFactory.create(getConseilLocalEtablissementEmail());
	}
	
	protected MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage)
			throws MessagingException, AddressException {
		final MimeMessageHelper message = getMessageHelper(mimeMessage);
		message.setBcc(getConseilLocalEtablissementEmail());
		message.setFrom(getInternetAddressEmail());
		message.setSentDate(dateProvider.now());
		return message;
	}
	
	
	

}
