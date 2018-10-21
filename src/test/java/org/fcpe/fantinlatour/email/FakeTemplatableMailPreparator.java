package org.fcpe.fantinlatour.email;

import javax.mail.internet.MimeMessage;

public class FakeTemplatableMailPreparator extends AbstractTemplatableMailPreparator {

	public FakeTemplatableMailPreparator(MimeMessageHelperFactory mimeMessageHelperFactory, ModelFactory modelFactory, String template, String encoding) {
		super(mimeMessageHelperFactory, modelFactory, template, encoding);
		
	}

	@Override
	public void prepare(MimeMessage mimeMessage)  {
		// TODO Auto-generated method stub
		
	}

	



	


	

}
