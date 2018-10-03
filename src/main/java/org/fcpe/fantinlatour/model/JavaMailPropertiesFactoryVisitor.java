package org.fcpe.fantinlatour.model;

import java.util.Properties;

public interface JavaMailPropertiesFactoryVisitor {

	Properties visit(SMTPProperties properties);
}
