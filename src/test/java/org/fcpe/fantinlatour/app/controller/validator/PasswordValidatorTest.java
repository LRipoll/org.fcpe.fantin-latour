package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
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
import javafx.scene.control.PasswordField;

public class PasswordValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private PasswordField passwordField;
	private SceneValidator sceneValidator;

	private PasswordValidator passwordValidator;

	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		latch.await();

		ctrl = support.createControl();

		passwordField = new PasswordField();
		sceneValidator = ctrl.createMock(SceneValidator.class);
		passwordValidator = new PasswordValidator(sceneValidator, passwordField, "okTooltip", "invalidTooltip");
		EasyMock.reset(sceneValidator);

	}

	@Test
	public void testWhenPasswordIsNullExistShouldSetAccordingToolTipAndStyle() {

		String name = "existname";

		support.replayAll();

		passwordField.setText(name);
		passwordValidator.changed(passwordField.textProperty(), null, name);

		assertEquals("invalidTooltip", passwordField.getTooltip().getText());
		assertTrue(passwordField.getStyleClass().contains(PasswordValidator.TEXT_FIELD_ERROR));
		assertFalse(passwordValidator.isValid());

		support.verifyAll();
	}

	
	@Test
	public void testWhenNameIsValidShouldSetAccordingToolTipAndStyle() {

		String name = "valid";

		sceneValidator.onStateChange(passwordValidator);
		EasyMock.expectLastCall().once();

		support.replayAll();
		passwordField.setText(name);
		passwordValidator.changed(passwordField.textProperty(), "", name);

		assertEquals("okTooltip", passwordField.getTooltip().getText());
		assertFalse(passwordField.getStyleClass().contains(PasswordValidator.TEXT_FIELD_ERROR));
		assertTrue(passwordValidator.isValid());

		support.verifyAll();
	}

}
