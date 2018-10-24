package org.fcpe.fantinlatour.template;

import java.util.Map;

import org.fcpe.fantinlatour.model.ConseilLocal;

public class ConseilLocalTemplateFactory extends TemplateFactory {

	private ConseilLocal conseilLocal;
	
	
	public ConseilLocalTemplateFactory(ConseilLocal conseilLocal) {
		super();
		this.conseilLocal = conseilLocal;
	}


	@Override
	public Map<String, Object> create() {
		
		Map<String, Object> model = super.create();
		model.put("conseilLocal", conseilLocal);
		model.put(Integer.class.getSimpleName(), Integer.class);
		
		return model;
		
	}

}
