package org.fcpe.fantinlatour.email;

import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.TemplateFactory;

public class FakeTemplatableMailPreparator extends AbstractTemplatableMailPreparator {

	public FakeTemplatableMailPreparator( TemplateFactory modelFactory, MailService mailService, DateProvider dateProvider, String template, String encoding) {
		super(modelFactory, mailService, dateProvider, template, encoding);
		
	}

	@Override
	public void prepare(MimeMessage mimeMessage)  {
		// TODO Auto-generated method stub
		
	}

	



	


	

}
