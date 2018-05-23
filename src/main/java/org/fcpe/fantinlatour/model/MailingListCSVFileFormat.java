package org.fcpe.fantinlatour.model;

public class MailingListCSVFileFormat {

	private String[] columns;
	private String mailColumn;
	private String delimiter;
	private boolean isSkipHeaderRecord;
	
	
	public String[] getColumns() {
		return columns;
	}
	public String getMailColumn() {
		return mailColumn;
	}
	public String getDelimiter() {
		return delimiter;
	}
	public boolean isSkipHeaderRecord() {
		return isSkipHeaderRecord;
	}
	
	
}
