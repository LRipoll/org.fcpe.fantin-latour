package org.fcpe.fantinlatour.model;

public class MailingListType {

	private String name;
	private MailingListCSVFileFormat csvFileDefinition;
	
	public MailingListType(String name, MailingListCSVFileFormat csvFileDefinition) {
		super();
		this.name = name;
		this.csvFileDefinition = csvFileDefinition;
	}

	public String getName() {
		return name;
	}

	public MailingListCSVFileFormat getCsvFileDefinition() {
		return csvFileDefinition;
	}
	
	
}
