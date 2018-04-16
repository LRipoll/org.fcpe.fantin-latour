package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.springframework.ui.velocity.VelocityEngineUtils;

public abstract class AbstractAnneeScolaireVelocityService extends AbstractVelocityService {

	protected AnneeScolaire anneeScolaire;
	protected AnneeScolaireServiceListener anneeScolaireServiceListener;
	protected ConseilLocalConfig config;
	public AbstractAnneeScolaireVelocityService(AnneeScolaire anneeScolaire, AnneeScolaireServiceListener anneeScolaireServiceListener,
			ConseilLocalConfig config) {
		super();
		this.anneeScolaire = anneeScolaire;
		this.anneeScolaireServiceListener = anneeScolaireServiceListener;
		this.config = config;
	}

	public void run() throws FileNotFoundException, UnsupportedEncodingException {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("anneeScolaire", anneeScolaire);
		model.put(Integer.class.getSimpleName(), Integer.class);

		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, getTemplateName(),
				config.getEncodage(), model);

		anneeScolaireServiceListener.process(text);

	}

	protected abstract String getTemplateName();

}