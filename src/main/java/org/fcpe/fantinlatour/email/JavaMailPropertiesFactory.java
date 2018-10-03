package org.fcpe.fantinlatour.email;

import java.util.Properties;

import org.fcpe.fantinlatour.model.EmailSenderProtocolProperties;
import org.fcpe.fantinlatour.model.JavaMailPropertiesFactoryVisitor;
import org.fcpe.fantinlatour.model.SMTPProperties;

public class JavaMailPropertiesFactory implements JavaMailPropertiesFactoryVisitor {

	public Properties create(EmailSenderProtocolProperties emailSenderProtocolProperties) {
		return emailSenderProtocolProperties.accept(this);
	}

	@Override
	public Properties visit(SMTPProperties properties) {
		Properties result = new Properties();
		result.setProperty("mail.transport.protocol", EmailSenderProtocolConverter.SMTP);
		result.setProperty("mail.smtp.auth", String.valueOf((properties.isAuth())));
		result.setProperty("mail.smtp.starttls.enable", String.valueOf((properties.isStarttls())));
		return result;
		
		
	}

	

}
