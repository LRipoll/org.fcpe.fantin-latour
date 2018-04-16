package org.fcpe.fantinlatour.model;

import java.io.IOException;
import java.util.Calendar;

import org.aeonbits.owner.ConfigFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.velocity.app.VelocityEngine;
import org.fcpe.fantinlatour.courriel.AdherentsService;
import org.fcpe.fantinlatour.courriel.ClassesService;
import org.fcpe.fantinlatour.courriel.DeleguesService;
import org.fcpe.fantinlatour.courriel.DeleguesServiceFileListener;
import org.fcpe.fantinlatour.courriel.GoogleGroupService;
import org.fcpe.fantinlatour.courriel.GoogleGroupServiceFactory;
import org.fcpe.fantinlatour.courriel.ListingFactory;
import org.fcpe.fantinlatour.parser.AdhesionsParser;
import org.fcpe.fantinlatour.parser.ElevesParser;
import org.fcpe.fantinlatour.parser.ResponsablesLegauxParser;
import org.fcpe.fantinlatour.parser.ResponsablesLegauxParserListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;

public class Launcher {
	public static void main(String[] args) {

		BasicConfigurator.configure();

		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		
		ConseilLocalConfig config = ConfigFactory.create(ConseilLocalConfig.class);
		AnneeScolaire anneeScolaire = new AnneeScolaire(new ClasseFactory(new EleveFactory()), Calendar.getInstance(),
				config);
		ElevesParser elevesParser = new ElevesParser(anneeScolaire, new ResponsableLegalFactory());

		ResponsablesLegauxParser responsablesLegaux = new ResponsablesLegauxParser(anneeScolaire, new ResponsablesLegauxParserListener());
		AdhesionsParser adhesionParser = new AdhesionsParser(anneeScolaire);

		try {
			elevesParser.parse(args[0]);
			responsablesLegaux.parse(args[1]);
			adhesionParser.parse(args[3]);

			VelocityEngine velocityEngine = context.getBean("velocityEngine", VelocityEngine.class);
			
			ClassesService classeService = new ClassesService(anneeScolaire);
			classeService.setVelocityEngine(velocityEngine);
			classeService.setMailSender(context.getBean("mailSender", JavaMailSender.class));
			classeService.run();
			DeleguesService deleguesService = new DeleguesService(anneeScolaire, config, new DeleguesServiceFileListener(config));
			deleguesService.setVelocityEngine(velocityEngine);
			//deleguesService.setMailSender((JavaMailSender) context.getBean("mailSender"));
			deleguesService.run();
			AdherentsService adherents = new AdherentsService(anneeScolaire);
			adherents.setVelocityEngine(velocityEngine);
			adherents.run();

			GoogleGroupServiceFactory factory = new GoogleGroupServiceFactory(new ListingFactory(anneeScolaire));
			GoogleGroupService googleGroupAdherentsService = factory.create(args[2]);
			googleGroupAdherentsService.run();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
