package org.fcpe.fantinlatour.email;

import org.fcpe.fantinlatour.model.EmailSenderProtocol;

public class EmailSenderProtocolConverter {

	public static final String SMTP = "smtp";

	public String convert(EmailSenderProtocol protocol) {
		String result = null;
		switch (protocol) {
		case SMTP:
			result = SMTP;
		}
		return result;
	}

}
