package org.fcpe.fantinlatour.model;

public class Personne {

	private String nom;
	private String prenom;
	
	public Personne(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}
	
	public boolean isThisPersonne(String nom, String prenom) {
		
		return this.nom.equalsIgnoreCase(nom) && this.prenom.equalsIgnoreCase(prenom);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "nom=" + nom + ", prenom=" + prenom ;
	}
	
	
}
