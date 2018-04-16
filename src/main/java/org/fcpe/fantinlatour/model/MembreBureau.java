package org.fcpe.fantinlatour.model;

public class MembreBureau {

	private ResponsableLegal responsableLegal;
	private Titre titre;

	MembreBureau(ResponsableLegal responsableLegal,Titre titre) {
		this.responsableLegal = responsableLegal;
		this.titre = titre;
	}

	public ResponsableLegal getResponsableLegal() {
		return responsableLegal;
	}

	public Titre getTitre() {
		return titre;
	}
	
	

}
