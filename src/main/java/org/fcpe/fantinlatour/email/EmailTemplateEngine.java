package org.fcpe.fantinlatour.email;

import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class EmailTemplateEngine {

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
