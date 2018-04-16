package org.fcpe.fantinlatour.model;

public class Candidat {

	private ResponsableLegal responsableLegal;
	private Engagement engagement;
	/**
	 * @return the responsableLegal
	 */
	public ResponsableLegal getResponsableLegal() {
		return responsableLegal;
	}
	/**
	 * @return the engagement
	 */
	public Engagement getEngagement() {
		return engagement;
	}
	
	Candidat(ResponsableLegal responsableLegal, Engagement engagement) {
		super();
		this.responsableLegal = responsableLegal;
		this.engagement = engagement;
	}

	
	
}
