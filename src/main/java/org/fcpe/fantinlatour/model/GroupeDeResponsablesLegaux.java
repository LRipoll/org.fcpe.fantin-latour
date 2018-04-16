package org.fcpe.fantinlatour.model;

public class GroupeDeResponsablesLegaux extends Entite{

	private String chemin;

	public GroupeDeResponsablesLegaux(String nom, int priorite) {
		super(nom, priorite);
	}

	public String getChemin() {
		return chemin;
	}

	public void setChemin(String chemin) {
		this.chemin = chemin;
	}
	
	
}
