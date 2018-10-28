package org.fcpe.fantinlatour.email;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public interface TemplatableMailPreparator extends MimeMessagePreparator {

    
	String getTemplate();
	
	public MimeMessageHelper createMimeMessageHelper(MimeMessage mimeMessage) throws MessagingException;
	
	Map<String, Object> getModel();

	String getEncoding();

	

}
