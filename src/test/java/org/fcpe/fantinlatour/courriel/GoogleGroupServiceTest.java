package org.fcpe.fantinlatour.courriel;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.parser.GoogleGroupParser;
import org.junit.Before;
import org.junit.Test;

public class GoogleGroupServiceTest {

	private static final String GOOGLE_GROUP = "GoogleGroup";
	private static String filename = "A/GoogleGroup.csv";
	private EasyMockSupport support = new EasyMockSupport();

	private ListingFactory listingFactory;
	private GoogleGroupParser googleGroupParser;
	private GoogleGroupService googleGroupService;
	private GoogleGroupServiceListener googleGroupServiceListener;

	private IMocksControl ctrl;
	private HashSet<String> nouveauxMembres;

	@Before
	public void setup() {
		ctrl = support.createControl();
		listingFactory = ctrl.createMock(ListingFactory.class);
		googleGroupParser = ctrl.createMock(GoogleGroupParser.class);
		googleGroupServiceListener = ctrl.createMock(GoogleGroupServiceListener.class);
		nouveauxMembres = new HashSet<String>(Arrays.asList("a", "b"));

		googleGroupService = new GoogleGroupService(nouveauxMembres, GOOGLE_GROUP, filename, listingFactory,
				googleGroupParser, googleGroupServiceListener);

	}

	@Test
	public void testRun() throws IOException {

		List<String> anciensMembres = Arrays.asList("b", "c");
		EasyMock.expect(googleGroupParser.parse(filename)).andReturn(anciensMembres);

		Set<String> membresASupprimer = new HashSet<String>(Arrays.asList("c"));
		EasyMock.expect(listingFactory.minus(new HashSet<String>(anciensMembres), new HashSet<String>(nouveauxMembres)))
				.andReturn(membresASupprimer);
		googleGroupServiceListener.courrielsSupprimes(GOOGLE_GROUP, membresASupprimer);
		EasyMock.expectLastCall().once();

		Set<String> membresAAjouter = new HashSet<String>(Arrays.asList("a"));
		EasyMock.expect(listingFactory.minus(new HashSet<String>(nouveauxMembres), new HashSet<String>(anciensMembres)))
				.andReturn(membresAAjouter);
		googleGroupServiceListener.courrielsAjoutes(GOOGLE_GROUP, membresAAjouter);
		EasyMock.expectLastCall().once();

		support.replayAll();
		googleGroupService.run();
		support.verifyAll();

	}
	
	@Test
	public void testRunWhenParserException() throws IOException {

		EasyMock.expect(googleGroupParser.parse(filename)).andThrow(new IOException());

		support.replayAll();
		googleGroupService.run();
		support.verifyAll();

	}
}
