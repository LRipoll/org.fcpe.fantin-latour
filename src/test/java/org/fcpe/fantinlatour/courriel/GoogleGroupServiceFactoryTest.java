package org.fcpe.fantinlatour.courriel;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

public class GoogleGroupServiceFactoryTest {

	private EasyMockSupport support = new EasyMockSupport();

	private ListingFactory listingFactory;

	private GoogleGroupServiceFactory googleGroupServiceFactory;

	private IMocksControl ctrl;

	@Before
	public void setup() {
		ctrl = support.createControl();
		listingFactory = ctrl.createMock(ListingFactory.class);

		googleGroupServiceFactory = new GoogleGroupServiceFactory(listingFactory);

	}

	@Test
	public void testCreate() {

		String filename = "a.b/c.txt";
		EasyMock.expect(listingFactory.getGoogleGroup(filename)).andReturn("c");

		Set<String> expectedSet = new HashSet<String>(Arrays.asList("a", "b", "c"));
		EasyMock.expect(listingFactory.getCourriels("c")).andReturn(expectedSet);

		support.replayAll();
		GoogleGroupService result = googleGroupServiceFactory.create(filename);
		support.verifyAll();
		assertNotNull(result);

	}

}
