package org.fcpe.fantinlatour.model;

public class EleveFactory {

	public EleveFactory() {
		super();

	}

	public Eleve createEleve(Classe classe, String nom, String prenom) {
		return new Eleve(classe, nom, prenom);
	}
}
