package org.fcpe.fantinlatour.model;

public class MailSenderAccount {

	private String username;
	private MailSenderProperties properties;
	public MailSenderAccount(String username, MailSenderProperties properties) {
		super();
		this.username = username;
		this.properties = properties;
	}
	public MailSenderAccount() {
		this("", new MailSenderProperties());
	}
	public String getUsername() {
		return username;
	}
	public MailSenderProperties getProperties() {
		return properties;
	}
	
	
	
}
