package org.fcpe.fantinlatour.model;

import java.util.Properties;

import org.fcpe.fantinlatour.dao.files.xstream.EncryptedStringConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;

public class SMTPProperties implements EmailSenderProtocolProperties {

	@XStreamConverter(EncryptedStringConverter.class)
	@XStreamAlias("AUTH")
	@XStreamAsAttribute
	private boolean auth;
	@XStreamConverter(EncryptedStringConverter.class)
	@XStreamAlias("STARTTLS")
	@XStreamAsAttribute
	private boolean starttls;
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public boolean isStarttls() {
		return starttls;
	}
	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}
	@Override
	public Properties accept(JavaMailPropertiesFactoryVisitor visitor) {
		return visitor.visit(this);
		
	}
	
	
}
