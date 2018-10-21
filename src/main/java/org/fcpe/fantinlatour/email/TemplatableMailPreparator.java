package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

public interface TemplatableMailPreparator {

	public void prepare(MimeMessage mimeMessage);

	String getTemplate();

	MimeMessageHelper getMessageHelper(MimeMessage mimeMessage) throws MessagingException;

	Map<String, Object> getModel();

	String getEncoding();

}
