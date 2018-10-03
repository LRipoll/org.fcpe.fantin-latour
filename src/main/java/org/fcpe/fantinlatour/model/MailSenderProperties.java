package org.fcpe.fantinlatour.model;

import org.fcpe.fantinlatour.dao.files.xstream.EncryptedStringConverter;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class MailSenderProperties {
	
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final int DEFAULT_PORT = 25;
	private static final EmailSenderProtocol DEFAULT_PROTOCOL = EmailSenderProtocol.SMTP;
	
	@XStreamAlias("protocole")
	@XStreamAsAttribute
	private EmailSenderProtocol protocol;
	@XStreamConverter(EncryptedStringConverter.class)
	@XStreamAlias("serveur")
	@XStreamAsAttribute
	private String host;
	@XStreamAlias("port")
	@XStreamAsAttribute
	private int port;
	@XStreamConverter(EncryptedStringConverter.class)
	@XStreamAlias("motDePasse")
	@XStreamAsAttribute
	private String password;
	@XStreamAlias("encodageParDefaut")
	@XStreamAsAttribute
	private String defaultEncoding;
	
	@XStreamAlias("Proprietes")
	private IEmailSenderProtocolProperties emailSenderProtocolProperties;

	@XStreamOmitField
	private EmailSenderProtocolPropertiesFactory emailSenderProtocolPropertiesFactory;
	
	public MailSenderProperties(EmailSenderProtocol protocol, String host, int port, String password, String defaultEncoding,  EmailSenderProtocolPropertiesFactory emailSenderProtocolPropertiesFactory) {
		super();
		
		this.host = host;
		this.port = port;
		this.password = password;
		this.defaultEncoding = defaultEncoding;
		this.emailSenderProtocolPropertiesFactory = emailSenderProtocolPropertiesFactory;
		createEmailSenderProperties(protocol);
	
	}

	public MailSenderProperties() {
		this(DEFAULT_PROTOCOL,"",DEFAULT_PORT,"",DEFAULT_ENCODING, new EmailSenderProtocolPropertiesFactory());
	}

	public EmailSenderProtocol getProtocol() {
		return protocol;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getPassword() {
		return password;
	}

	public String getDefaultEncoding() {
		return defaultEncoding;
	}

	public void setProtocol(EmailSenderProtocol protocol) {
		this.protocol = protocol;
		createEmailSenderProperties(protocol);
	}

	

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public IEmailSenderProtocolProperties getEmailSenderProtocolProperties() {
		return emailSenderProtocolProperties;
	}
	
	private void createEmailSenderProperties(EmailSenderProtocol protocol) {
		emailSenderProtocolProperties = emailSenderProtocolPropertiesFactory.create(protocol);
	}
	
	
	
	
	
	
	
}
