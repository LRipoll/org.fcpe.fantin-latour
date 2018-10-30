package org.fcpe.fantinlatour.template;

import java.util.HashMap;
import java.util.Map;

public class TemplateFactory {

	public Map<String, Object> create() {
		HashMap<String, Object> model = new HashMap<String, Object>();
		model.put(Integer.class.getSimpleName(), Integer.class);
		return model;
	}
	
}
