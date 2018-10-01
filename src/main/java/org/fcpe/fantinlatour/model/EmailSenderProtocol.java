package org.fcpe.fantinlatour.model;

import org.fcpe.fantinlatour.service.SpringFactory;

public enum EmailSenderProtocol {

	SMTP(SpringFactory.getMessage("org.fcpe.fantinlatour.email.protocol.smtp"));

	private String label;

	EmailSenderProtocol(String label) {
		this.label = label;
	}

	public String toString() {
		return label;
	}
}
