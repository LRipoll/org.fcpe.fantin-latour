package org.fcpe.fantinlatour.model;

public class Entite {

	private String nom;
	int priorite;
	

	public Entite(String nom, int priorite) {
		super();
		this.nom = nom;
		this.priorite = priorite;
	}
	
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getPriorite() {
		return priorite;
	}

	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}
	
	

}
