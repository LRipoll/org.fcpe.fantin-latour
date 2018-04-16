package org.fcpe.fantinlatour.model;

public class ClasseFactory {
	

	public static final String ID = "classeFactory";
	private EleveFactory eleveFactory;

	public ClasseFactory(EleveFactory eleveFactory) {
		super();
		this.eleveFactory = eleveFactory;
		
	}

	public Classe createClasse(AnneeScolaire anneeScolaire, String nom, ConseilLocalConfig config) {
		return new Classe(anneeScolaire,nom,eleveFactory, config);
	}
}
