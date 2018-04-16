package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;

public class SceneValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private Button okButton;

	private SceneValidator sceneValidator;
	
	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		latch.await();

		ctrl = support.createControl();

		okButton = new Button();

		

	}
	
	@Test
	public void testConstructor() {
		sceneValidator = new SceneValidator(okButton);
		assertFalse(sceneValidator.isValid());
		assertTrue(okButton.isDisable());
	}
	
	@Test
	public void testOnStateChange() {
		sceneValidator = new SceneValidator(okButton);
		ControlValidator controlValidator = ctrl.createMock(ControlValidator.class);
		sceneValidator.add(controlValidator);
		EasyMock.expect(controlValidator.isValid()).andReturn(true);
		support.replayAll();
		sceneValidator.onStateChange(controlValidator);
		assertTrue(sceneValidator.isValid());
		assertFalse(okButton.isDisable());
		
		support.verifyAll();
	}
	
	@Test
	public void testOnStateChangeInvalid() {
		sceneValidator = new SceneValidator(okButton,true);
		ControlValidator controlValidator = ctrl.createMock(ControlValidator.class);
		sceneValidator.add(controlValidator);
		ControlValidator controlValidator2 = ctrl.createMock(ControlValidator.class);
		sceneValidator.add(controlValidator2);
		
		EasyMock.expect(controlValidator.isValid()).andReturn(false);
		
		support.replayAll();
		sceneValidator.onStateChange(controlValidator);
		assertFalse(sceneValidator.isValid());
		assertTrue(okButton.isDisable());
		
		support.verifyAll();
	}
	
	

}
