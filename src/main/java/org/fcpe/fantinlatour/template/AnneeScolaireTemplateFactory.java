package org.fcpe.fantinlatour.template;

import java.util.Map;

import org.fcpe.fantinlatour.model.AnneeScolaire;

public class AnneeScolaireTemplateFactory extends TemplateFactory {

	private AnneeScolaire anneeScolaire;
	
	
	public AnneeScolaireTemplateFactory(AnneeScolaire anneeScolaire) {
		super();
		this.anneeScolaire = anneeScolaire;
	}


	@Override
	public Map<String, Object> create() {
		
		Map<String, Object> model = super.create();
		model.put("anneeScolaire", anneeScolaire);
		
		
		return model;
		
	}

}
