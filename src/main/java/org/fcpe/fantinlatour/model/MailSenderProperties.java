package org.fcpe.fantinlatour.model;


public class MailSenderProperties {
	
	private static final String DEFAULT_ENCODING = "UTF-8";

	private static final int DEFAULT_PORT = 25;

	private static final String DEFAULT_PROTOCOL = "smpt";

	private String protocol;

	private String host;

	private int port;
	
	private String password;
	
	private String defaultEncoding;
	
	public MailSenderProperties(String protocol, String host, int port, String password, String defaultEncoding) {
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

	public String getProtocol() {
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
	
	
	
}
