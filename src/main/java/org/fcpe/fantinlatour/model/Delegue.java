package org.fcpe.fantinlatour.model;

public class Delegue {

	private Classe classe;
	private Candidat candidat;
	
	
	
	Delegue(Classe classe, ResponsableLegal responsableLegal, Engagement engagement) {
		super();
		this.classe = classe;
		this.candidat = new Candidat(responsableLegal, engagement);
		
	}
	
	/**
	 * @return the engagement
	 */
	public Engagement getEngagement() {
		return candidat.getEngagement();
	}

	/**
	 * @return the classe
	 */
	public Classe getClasse() {
		return classe;
	}
	/**
	 * @return the responsableLegal
	 */
	public ResponsableLegal getResponsableLegal() {
		return candidat.getResponsableLegal();
	}
	
	

}
