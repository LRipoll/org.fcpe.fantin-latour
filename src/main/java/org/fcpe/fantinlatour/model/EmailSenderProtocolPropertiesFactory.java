package org.fcpe.fantinlatour.model;

public class EmailSenderProtocolPropertiesFactory {

	public EmailSenderProtocolProperties create(EmailSenderProtocol protocol) {
		EmailSenderProtocolProperties result = null;
		switch (protocol) {
		case SMTP:
			result = new SMTPProperties();
			break;

		}
		return result;
	}
}
