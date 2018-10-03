package org.fcpe.fantinlatour.model;

public class EmailSenderProtocolPropertiesFactory {

	public IEmailSenderProtocolProperties create(EmailSenderProtocol protocol) {
		IEmailSenderProtocolProperties result = null;
		switch (protocol) {
		case SMTP:
			result = new SMTPProperties();
			break;

		}
		return result;
	}
}
