package org.fcpe.fantinlatour.model;

import java.util.Properties;

public interface EmailSenderProtocolProperties {
	Properties accept(JavaMailPropertiesFactoryVisitor visitor);
}
