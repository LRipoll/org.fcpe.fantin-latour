package org.fcpe.fantinlatour.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.MimeMessageHelper;

public class MimeMessageHelperFactory {

	private MimeMessageHelper message;
	
	public MimeMessageHelper create(MimeMessage mimeMessage, String encoding) throws MessagingException {
		if (message == null) {
			message = new MimeMessageHelper(mimeMessage, true, encoding); 
		}
		return message;
	}
}
