package org.fcpe.fantinlatour.model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class MailSenderProperties {
	
	private static final String DEFAULT_ENCODING = "UTF-8";

	private static final int DEFAULT_PORT = 25;

	private static final EmailSenderProtocol DEFAULT_PROTOCOL = EmailSenderProtocol.SMTP;

	@XStreamAlias("protocole")
	@XStreamAsAttribute
	private EmailSenderProtocol protocol;
	@XStreamAlias("serveur")
	@XStreamAsAttribute
	private String host;
	@XStreamAlias("port")
	@XStreamAsAttribute
	private int port;
	@XStreamAlias("motDePasse")
	@XStreamAsAttribute
	private String password;
	@XStreamAlias("encodageParDefaut")
	@XStreamAsAttribute
	private String defaultEncoding;
	
	public MailSenderProperties(EmailSenderProtocol protocol, String host, int port, String password, String defaultEncoding) {
		super();
		this.protocol = protocol;
		this.host = host;
		this.port = port;
		this.password = password;
		this.defaultEncoding = defaultEncoding;
	}

	public MailSenderProperties() {
		this(DEFAULT_PROTOCOL,"",DEFAULT_PORT,"",DEFAULT_ENCODING);
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
	
	
	
	
	
}
