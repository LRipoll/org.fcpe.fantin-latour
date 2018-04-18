package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.PasswordField;

public class CombinedPasswordValidatorTest {
	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private EncryptHelper encryptHelper;
	private PasswordField passwordField;
	private PasswordField confirmpasswordField;
	private SceneValidator sceneValidator;

	private CombinedPasswordValidator combinedPasswordValidator;

	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		latch.await();

		ctrl = support.createControl();
		encryptHelper = ctrl.createMock(EncryptHelper.class);

		passwordField = new PasswordField();
		confirmpasswordField = new PasswordField();
		sceneValidator = ctrl.createMock(SceneValidator.class);
		combinedPasswordValidator = new CombinedPasswordValidator(sceneValidator, encryptHelper, passwordField,
				confirmpasswordField, "okTooltip", "invalidTooltip", "incoherentTooltip");
		EasyMock.reset(sceneValidator);

	}

	@Test
	public void testWhenPasswordIsInvalidShouldSetAccordingToolTipAndStyle() {

		String password = "password";

		EasyMock.expect(encryptHelper.isValid(password)).andReturn(false);

		support.replayAll();

		passwordField.setText(password);
		combinedPasswordValidator.changed(passwordField.textProperty(), "", password);

		assertEquals("invalidTooltip", passwordField.getTooltip().getText());
		assertTrue(passwordField.getStyleClass().contains(CombinedPasswordValidator.TEXT_FIELD_ERROR));
		assertFalse(combinedPasswordValidator.isValid());

		support.verifyAll();
	}

	@Test
	public void testWhenPasswordIsIValidShouldSetAccordingToolTipAndStyle() {

		String password = "password";

		EasyMock.expect(encryptHelper.isValid(password)).andReturn(true).anyTimes();
		sceneValidator.onStateChange(combinedPasswordValidator);
		EasyMock.expectLastCall().once();

		support.replayAll();

		passwordField.setText(password);
		confirmpasswordField.setText(password);

		combinedPasswordValidator.changed(passwordField.textProperty(), "", password);
		combinedPasswordValidator.changed(confirmpasswordField.textProperty(), "", password);

		assertEquals("okTooltip", passwordField.getTooltip().getText());
		assertFalse(passwordField.getStyleClass().contains(CombinedPasswordValidator.TEXT_FIELD_ERROR));
		assertEquals("okTooltip", confirmpasswordField.getTooltip().getText());
		assertFalse(confirmpasswordField.getStyleClass().contains(CombinedPasswordValidator.TEXT_FIELD_ERROR));
		assertTrue(combinedPasswordValidator.isValid());

		support.verifyAll();
	}

	@Test
	public void testWhenPasswordAreIncoherentShouldSetAccordingToolTipAndStyle() {

		String password = "password";
		String password2 = "password2";

		EasyMock.expect(encryptHelper.isValid(password)).andReturn(true);
		EasyMock.expect(encryptHelper.isValid(password2)).andReturn(true);

		support.replayAll();

		passwordField.setText(password);
		confirmpasswordField.setText(password2);

		combinedPasswordValidator.changed(passwordField.textProperty(), "", password);
		combinedPasswordValidator.changed(confirmpasswordField.textProperty(), "", password2);

		assertEquals("incoherentTooltip", passwordField.getTooltip().getText());
		assertTrue(passwordField.getStyleClass().contains(CombinedPasswordValidator.TEXT_FIELD_ERROR));
		assertEquals("incoherentTooltip", confirmpasswordField.getTooltip().getText());
		assertTrue(confirmpasswordField.getStyleClass().contains(CombinedPasswordValidator.TEXT_FIELD_ERROR));
		assertFalse(combinedPasswordValidator.isValid());

		support.verifyAll();
	}

}
