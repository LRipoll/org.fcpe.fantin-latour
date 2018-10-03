package org.fcpe.fantinlatour.email;

import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.fcpe.fantinlatour.model.MailSenderProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class JavaMailSenderFactory {

	
	private EmailSenderProtocolConverter protocolConverter;
	private JavaMailPropertiesFactory javaMailPropertiesFactory;

	
	public JavaMailSenderFactory(EmailSenderProtocolConverter protocolConverter,
			JavaMailPropertiesFactory javaMailPropertiesFactory) {
		super();
		this.protocolConverter = protocolConverter;
		this.javaMailPropertiesFactory = javaMailPropertiesFactory;
	}

	public JavaMailSender create(MailSenderAccount account) {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setUsername(account.getUsername());
		init(sender,account.getProperties());
		return sender;
	}

	private void init(JavaMailSenderImpl sender, MailSenderProperties properties) {
		sender.setDefaultEncoding(properties.getDefaultEncoding());
		sender.setHost(properties.getHost());
		sender.setPassword(properties.getPassword());
		sender.setPort(properties.getPort());
		sender.setProtocol(protocolConverter.convert(properties.getProtocol()));
		sender.setJavaMailProperties(javaMailPropertiesFactory.create(properties.getEmailSenderProtocolProperties()));
		
	}
}
