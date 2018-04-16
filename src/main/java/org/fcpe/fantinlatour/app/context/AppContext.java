package org.fcpe.fantinlatour.app.context;

import org.fcpe.fantinlatour.model.AnneeScolaire;

public class AppContext {

	public static final String ID = "appContext";
	private AnneeScolaire anneeScolaire;
	
	public AppContext() {
		
	}

	public AnneeScolaire getAnneeScolaire() {
		return anneeScolaire;
	}

	public void setAnneeScolaire(AnneeScolaire anneeScolaire) {
		this.anneeScolaire = anneeScolaire;
	}

}
