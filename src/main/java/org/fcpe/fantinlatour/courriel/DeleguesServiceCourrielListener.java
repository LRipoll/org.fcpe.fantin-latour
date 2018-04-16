package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class DeleguesServiceCourrielListener implements AnneeScolaireServiceListener {
	
	private ConseilLocalConfig config;
	private JavaMailSender mailSender;
	
	public DeleguesServiceCourrielListener(ConseilLocalConfig config) {
		super();
		this.config = config;
	}
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void process(String text) throws FileNotFoundException, UnsupportedEncodingException {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo("fcpe-fantin-info@googlegroups.com");
				// message.setBcc("fcpe.fantinlatour@free.fr");
				message.setFrom(new InternetAddress("fcpe.fantinlatour@free.fr"));
				message.setSubject("Point sur les délégués de classes du collège Fantin Latour et autres informations");
				message.setSentDate(new Date());

				
				message.setText(text, true);

				
			}
		};

		mailSender.send(preparator);
		
	}
	
	
	
	
}
