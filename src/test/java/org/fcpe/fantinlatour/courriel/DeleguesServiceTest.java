package org.fcpe.fantinlatour.courriel;

import java.io.IOException;
import java.util.Arrays;

import org.apache.velocity.app.VelocityEngine;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.ConseilLocal;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.fcpe.fantinlatour.model.Delegue;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeleguesServiceTest {
	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private AnneeScolaire anneeScolaire;
	private ConseilLocalConfig config;
	private DeleguesService deleguesService;
	private AnneeScolaireServiceListener anneeScolaireServiceListener;

	private ClassPathXmlApplicationContext ctx;

	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		config = ctrl.createMock(ConseilLocalConfig.class);
		anneeScolaireServiceListener = ctrl.createMock(AnneeScolaireServiceListener.class);
		deleguesService = new DeleguesService(anneeScolaire, config, anneeScolaireServiceListener);
		ctx = new ClassPathXmlApplicationContext("classpath:spring/velocity-test.xml");
		VelocityEngine velocityEngine = ctx.getBean("velocityEngine", VelocityEngine.class);
		deleguesService.setVelocityEngine(velocityEngine);
		
		
	}
	
	@After
    public final void tearDown() {
		ctx.close();
	}
	

	@Test
	public void testRun() throws IOException {
		EasyMock.expect(config.getEncodage()).andReturn("UTF-8");

		EasyMock.expect(anneeScolaire.getAnneeScolaire()).andReturn("2017-2018").anyTimes();
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(conseilLocal.getNbMaximumCandidatsDeleguesParClasse()).andReturn(2).anyTimes();
		
		Classe classe1 = ctrl.createMock(Classe.class);
		EasyMock.expect(classe1.getNiveau()).andReturn("6");
		EasyMock.expect(classe1.getNomComplet()).andReturn("6ème2");
	
		Delegue delegue1 = ctrl.createMock(Delegue.class);
		EasyMock.expect(classe1.getDeleguesRetenus()).andReturn(Arrays.asList(delegue1));
		EasyMock.expect(classe1.getNbMinimumCandidatsDelegues()).andReturn(1).anyTimes();
		
		ResponsableLegal responsableLegal1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue1.getResponsableLegal()).andReturn(responsableLegal1).anyTimes();
		EasyMock.expect(responsableLegal1.getNom()).andReturn("Nom1");
		EasyMock.expect(responsableLegal1.getPrenom()).andReturn("Prénom1");
		
		
		Classe classe2 = ctrl.createMock(Classe.class);
		EasyMock.expect(classe2.getNomComplet()).andReturn("5ème5");
		EasyMock.expect(classe2.getNiveau()).andReturn("5");
		EasyMock.expect(anneeScolaire.getClasses()).andReturn(Arrays.asList(classe1, classe2));
		EasyMock.expect(classe2.getNbMinimumCandidatsDelegues()).andReturn(2);
		Delegue delegue2 = ctrl.createMock(Delegue.class);
		ResponsableLegal responsableLegal2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue2.getResponsableLegal()).andReturn(responsableLegal2).anyTimes();;
		EasyMock.expect(responsableLegal2.getNom()).andReturn("Nom2");
		EasyMock.expect(responsableLegal2.getPrenom()).andReturn("Prénom2");
		
		Delegue delegue3 = ctrl.createMock(Delegue.class);
		EasyMock.expect(classe2.getDeleguesRetenus()).andReturn(Arrays.asList(delegue2,delegue3));
		ResponsableLegal responsableLegal3 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(delegue3.getResponsableLegal()).andReturn(responsableLegal3).anyTimes();;
		EasyMock.expect(responsableLegal3.getNom()).andReturn("Nom3");
		EasyMock.expect(responsableLegal3.getPrenom()).andReturn("Prénom3");
		String expectedFileName = "/DeleguesServicesTest.html";
		StringBuffer expectedResult = AnneeScolaireVelocityServiceTest.readFile(expectedFileName);
		anneeScolaireServiceListener.process(expectedResult.toString());
		support.replayAll();
		
		deleguesService.run();
		
		support.verifyAll();
	}

	
}
