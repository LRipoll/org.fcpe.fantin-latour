package org.fcpe.fantinlatour.courriel;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class GoogleGroupServiceConsoleListenerTest {
	@Test
	public void testCourrielsSupprimes() {
		GoogleGroupServiceConsoleListener googleGroupServiceConsoleListener = new GoogleGroupServiceConsoleListener();
		googleGroupServiceConsoleListener.courrielsSupprimes("test", new HashSet<String>(Arrays.asList("a@1", "a@2","a@3", "a@4","a@5", "a@6","a@7", "a@8","a@9", "a@0")));
	}
	
	@Test
	public void testCourrielsAjoutes() {
		GoogleGroupServiceConsoleListener googleGroupServiceConsoleListener = new GoogleGroupServiceConsoleListener();
		googleGroupServiceConsoleListener.courrielsAjoutes("test", new HashSet<String>(Arrays.asList("a@1", "a@2","a@3", "a@4","a@5", "a@6","a@7", "a@8","a@9", "a@0")));
	}
}
