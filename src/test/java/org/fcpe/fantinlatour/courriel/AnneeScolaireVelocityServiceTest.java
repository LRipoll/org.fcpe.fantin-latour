package org.fcpe.fantinlatour.courriel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.velocity.app.VelocityEngine;
import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.Classe;
import org.fcpe.fantinlatour.model.AnneeScolaire;
import org.fcpe.fantinlatour.model.ConseilLocalConfig;
import org.fcpe.fantinlatour.model.ConseilLocal;
import org.fcpe.fantinlatour.model.Delegue;
import org.fcpe.fantinlatour.model.ResponsableLegal;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnneeScolaireVelocityServiceTest {
	
	private EasyMockSupport support = new EasyMockSupport();
	private IMocksControl ctrl;
	
	private AnneeScolaire anneeScolaire;
	private ConseilLocalConfig config;
	private AbstractAnneeScolaireVelocityService anneeScolaireService;
	private AnneeScolaireServiceListener anneeScolaireServiceListener;
	
	private ClassPathXmlApplicationContext ctx;
	private VelocityEngine velocityEngine;

	static StringBuffer readFile(String expectedFileName) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(AnneeScolaireVelocityServiceTest.class.getResourceAsStream(expectedFileName)));
		StringBuffer expectedResult = new StringBuffer();
		Iterator<String> lines = br.lines().iterator();
		while (lines.hasNext()) {
			if (expectedResult.length()!=0) {
				expectedResult.append("\n");
			}
			expectedResult.append(lines.next());
		}
		br.close();
		return expectedResult;
	}
	
	@Before
	public void setup() {
		ctrl = support.createControl();
		anneeScolaire = ctrl.createMock(AnneeScolaire.class);
		config = ctrl.createMock(ConseilLocalConfig.class);
		anneeScolaireServiceListener = ctrl.createMock(AnneeScolaireServiceListener.class);
		ctx = new ClassPathXmlApplicationContext("classpath:spring/velocity-test.xml");
		velocityEngine = ctx.getBean("velocityEngine", VelocityEngine.class);
	}
	
	@After
    public final void tearDown() {
		ctx.close();
	}
	
	@Test
	public void testTableauMembresBureau() throws IOException {
		anneeScolaireService = new AnneeScolaireVelocityTestService(anneeScolaire, anneeScolaireServiceListener, config,"velocity/tableau-membres-bureau.vm");
		
		anneeScolaireService.setVelocityEngine(velocityEngine);
		
		EasyMock.expect(config.getEncodage()).andReturn("UTF-8");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		
		ResponsableLegal president = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(conseilLocal.getPresident()).andReturn(president).anyTimes();
		EasyMock.expect(president.getNom()).andReturn("Moi");
		EasyMock.expect(president.getPrenom()).andReturn("Président");
		
		ResponsableLegal tresorier = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(conseilLocal.getTresoriers()).andReturn(Arrays.asList(tresorier));
		EasyMock.expect(tresorier.getNom()).andReturn("Trésor");
		EasyMock.expect(tresorier.getPrenom()).andReturn("Yest");
		
		ResponsableLegal secretaire = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(conseilLocal.getSecretaires()).andReturn(Arrays.asList(secretaire));
		EasyMock.expect(secretaire.getNom()).andReturn("Secret");
		EasyMock.expect(secretaire.getPrenom()).andReturn("Air");
		
		ResponsableLegal membre1 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(membre1.getNom()).andReturn("Membre");
		EasyMock.expect(membre1.getPrenom()).andReturn("Premier");
		
		ResponsableLegal membre2 = ctrl.createMock(ResponsableLegal.class);
		EasyMock.expect(membre2.getNom()).andReturn("Membre");
		EasyMock.expect(membre2.getPrenom()).andReturn("Second");
		
		
		EasyMock.expect(conseilLocal.getMembresBureau()).andReturn(Arrays.asList(membre1,membre2));
		
		String expectedFileName = "/TableauMembresBureauTest.html";
		StringBuffer expectedResult = AnneeScolaireVelocityServiceTest.readFile(expectedFileName);
		anneeScolaireServiceListener.process(expectedResult.toString());
		
		support.replayAll();
		
		anneeScolaireService.run();
		
		support.verifyAll();
	}
	
	@Test
	public void testTableauDelegues() throws IOException {
		anneeScolaireService = new AnneeScolaireVelocityTestService(anneeScolaire, anneeScolaireServiceListener, config,"velocity/tableau-delegues.vm");
		
		anneeScolaireService.setVelocityEngine(velocityEngine);
		
		EasyMock.expect(config.getEncodage()).andReturn("UTF-8");
		
		ConseilLocal conseilLocal = ctrl.createMock(ConseilLocal.class);
		EasyMock.expect(anneeScolaire.getConseilLocal()).andReturn(conseilLocal).anyTimes();
		EasyMock.expect(conseilLocal.getNbMaximumCandidatsDeleguesParClasse()).andReturn(2).anyTimes();
		
		Classe classe1 = ctrl.createMock(Classe.class);
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
		
		String expectedFileName = "/TableauDeleguesTest.html";
		StringBuffer expectedResult = AnneeScolaireVelocityServiceTest.readFile(expectedFileName);
		anneeScolaireServiceListener.process(expectedResult.toString());
		
		support.replayAll();
		
		anneeScolaireService.run();
		
		support.verifyAll();
	}
		
}
