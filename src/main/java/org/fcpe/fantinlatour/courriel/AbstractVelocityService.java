package org.fcpe.fantinlatour.courriel;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractVelocityService {

	@Autowired
	protected VelocityEngine velocityEngine;

	public AbstractVelocityService() {
		super();
	}

	@Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}