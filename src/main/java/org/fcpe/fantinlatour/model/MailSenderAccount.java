package org.fcpe.fantinlatour.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class MailSenderAccount {

	@XStreamAlias("adresse")
	@XStreamAsAttribute
	private String username;
	@XStreamAlias("Parametres")
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
	public void setUserName(String username) {
		this.username = username;
		
	}
	
	
	
}
