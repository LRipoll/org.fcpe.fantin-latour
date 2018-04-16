package org.fcpe.fantinlatour.courriel;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

@Component
public class AdherentsService extends AbstractVelocityService {

	private AnneeScolaire anneeScolaire;
	
	
	public AdherentsService(AnneeScolaire anneeScolaire) {
		super();
		this.anneeScolaire = anneeScolaire;
	}


	public void run() throws FileNotFoundException, UnsupportedEncodingException {
		Map<String, Object> model = new HashMap<String, Object>();


		model.put("conseilLocal", anneeScolaire.getConseilLocal());

		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "velocity/adherents.vm", "UTF-8", model);
		PrintWriter writer = new PrintWriter(
				"/Users/mathieuripoll/eclipse-workspace/AnneeScolaire/target/exports/adherents.csv",
				"UTF-8");
		writer.println(text);
		
		writer.close();

	}
}
