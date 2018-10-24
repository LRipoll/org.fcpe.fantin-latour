package org.fcpe.fantinlatour.template;

import java.util.Map;

import org.fcpe.fantinlatour.model.Classe;

public class ClasseTemplateFactory extends TemplateFactory {

	private Classe classe;

	public ClasseTemplateFactory(Classe classe) {
			super();
			this.classe = classe;
		}

	@Override
	public Map<String, Object> create() {

		Map<String, Object> model = super.create();
		model.put("classe", classe);

		return model;

	}
}
