package org.fcpe.fantinlatour.email;

import javax.mail.internet.MimeMessage;

import org.fcpe.fantinlatour.template.TemplateFactory;

public class FakeTemplatableMailPreparator extends AbstractTemplatableMailPreparator {

	public FakeTemplatableMailPreparator(MimeMessageHelperFactory mimeMessageHelperFactory, TemplateFactory modelFactory, InternetAddressFactory internetAddressFactory, DateProvider dateProvider, String template, String encoding) {
		super(mimeMessageHelperFactory, modelFactory, internetAddressFactory, dateProvider, template, encoding);
		
	}

	@Override
	public void prepare(MimeMessage mimeMessage)  {
		// TODO Auto-generated method stub
		
	}

	



	


	

}
