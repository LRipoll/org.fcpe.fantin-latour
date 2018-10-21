/**
 * 
 */
package org.fcpe.fantinlatour.app;

import java.io.IOException;
import java.util.Calendar;

import org.aeonbits.owner.ConfigFactory;
import org.apache.velocity.app.VelocityEngine;
import org.fcpe.fantinlatour.courriel.AdherentsService;
import org.fcpe.fantinlatour.courriel.ClassesService;
import org.fcpe.fantinlatour.courriel.DeleguesService;
import org.fcpe.fantinlatour.courriel.DeleguesServiceCourrielListener;
import org.fcpe.fantinlatour.courriel.GoogleGroupService;
import org.fcpe.fantinlatour.courriel.GoogleGroupServiceFactory;
import org.fcpe.fantinlatour.courriel.ListingFactory;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ClasseFactory;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.fcpe.fantinlatour.model.EleveFactory;
import org.fcpe.fantinlatour.model.ResponsableLegalFactory;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.parser.ElevesParser;
import org.fcpe.fantinlatour.parser.ResponsablesLegauxParser;
import org.fcpe.fantinlatour.parser.ResponsablesLegauxParserListener;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ViewFactory;
import org.springframework.mail.javamail.JavaMailSender;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author mathieuripoll
 *
 */
public class ConseilLocalApp extends Application {

	public static final String NOUVEAU_CONSEIL_LOCAL = "NOUVEAU_CONSEIL_LOCAL";
	private ViewFactory viewFactory;

	/**
	 * 
	 */
	public ConseilLocalApp() {

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		SpringFactory.setResource("spring/applicationContext.xml");
	
		
		
		ConseilLocalConfig config = ConfigFactory.create(ConseilLocalConfig.class);
		AnneeScolaire anneeScolaire = new AnneeScolaire(new ClasseFactory(new EleveFactory()), Calendar.getInstance(),
				config);
		ElevesParser elevesParser = new ElevesParser(anneeScolaire, new ResponsableLegalFactory());

		ResponsablesLegauxParser responsablesLegaux = new ResponsablesLegauxParser(anneeScolaire, new ResponsablesLegauxParserListener());
		

		try {
			elevesParser.parse(args[0]);
			responsablesLegaux.parse(args[1]);
			
			VelocityEngine velocityEngine = SpringFactory.getService("velocityEngine");
			
			ClassesService classeService = new ClassesService(anneeScolaire);
			classeService.setVelocityEngine(velocityEngine);
			JavaMailSender mailSender = SpringFactory.getService("mailSender");
			
			classeService.setMailSender(mailSender);
			classeService.run();
			DeleguesServiceCourrielListener anneeScolaireServiceListener = new DeleguesServiceCourrielListener(config);
			anneeScolaireServiceListener.setMailSender(mailSender);
			DeleguesService deleguesService = new DeleguesService(anneeScolaire, config, anneeScolaireServiceListener);
			deleguesService.setVelocityEngine(velocityEngine);
		
			deleguesService.run();
			AdherentsService adherents = new AdherentsService(anneeScolaire);
			adherents.setVelocityEngine(velocityEngine);
			adherents.run();

			GoogleGroupServiceFactory factory = new GoogleGroupServiceFactory(new ListingFactory(anneeScolaire));
			GoogleGroupService googleGroupAdherentsService = factory.create(args[2]);
			googleGroupAdherentsService.run();
			
			GoogleGroupService googleGroupFCPEService = factory.create(args[3]);
			googleGroupFCPEService.run();
			
			GoogleGroupService googleGroupCommeissionEducativeService = factory.create(args[4]);
			googleGroupCommeissionEducativeService.run();

		} catch (IOException e) {
			e.printStackTrace();
		}

		launch(args);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage stage) throws Exception {

		viewFactory = SpringFactory.getService(ViewFactory.ID);
		viewFactory.createScene(stage, "main");
		ConseilLocalEtablissementManager conseilLocalEtablissementManager = SpringFactory
				.getService(ConseilLocalEtablissementManager.ID);	
		stage.show();
		conseilLocalEtablissementManager.init();
		
	}

}
