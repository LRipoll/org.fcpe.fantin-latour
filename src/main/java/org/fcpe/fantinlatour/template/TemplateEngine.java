package org.fcpe.fantinlatour.template;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class TemplateEngine {

	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}
	
	public String process(Map<String, Object> model, String templateName, String encodage) {
		String result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
				templateName, encodage, model);
		return result;
	}
}
