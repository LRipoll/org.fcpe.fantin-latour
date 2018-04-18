package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.controller.UniqueNameManager;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;

public class UniqueNameValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private UniqueNameManager uniqueNameManager;
	private TextField nameTextField;
	private SceneValidator sceneValidator;
	
	private UniqueNameValidator uniqueNameValidator;

	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		
		latch.await();
		
		ctrl = support.createControl();
		uniqueNameManager = ctrl.createMock(UniqueNameManager.class);
		
		nameTextField = new TextField();
		sceneValidator = ctrl.createMock(SceneValidator.class);
		uniqueNameValidator = new UniqueNameValidator(sceneValidator, uniqueNameManager, nameTextField, "okTooltip",
				"existTooltip", "invalidTooltip");
		EasyMock.reset(sceneValidator);

	}

	@Test
	public void testWhenNameExistShouldSetAccordingToolTipAndStyle() {

		
		String name = "existname";
		
		EasyMock.expect(uniqueNameManager.exists(name)).andReturn(true);
		
		support.replayAll();
		
		
		nameTextField.setText(name);
		uniqueNameValidator.changed(nameTextField.textProperty(), "", name);
		
		assertEquals("existTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertFalse(uniqueNameValidator.isValid());

		support.verifyAll();
	}
	
	@Test
	public void testWhenNameIsInvalidShouldSetAccordingToolTipAndStyle() {

		
		String name = "invalid";
		
		EasyMock.expect(uniqueNameManager.exists(name)).andReturn(false);
		EasyMock.expect(uniqueNameManager.isValidName(name)).andReturn(false);
		
		
		support.replayAll();
		nameTextField.setText(name);
		uniqueNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("invalidTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertFalse(uniqueNameValidator.isValid());
		
		
		support.verifyAll();
	}
	
	@Test
	public void testWhenNameIsValidShouldSetAccordingToolTipAndStyle() {

		
		String name = "valid";
		
		EasyMock.expect(uniqueNameManager.exists(name)).andReturn(false);
		EasyMock.expect(uniqueNameManager.isValidName(name)).andReturn(true);
		
		sceneValidator.onStateChange(uniqueNameValidator);
		EasyMock.expectLastCall().once();
		
		support.replayAll();
		nameTextField.setText(name);
		uniqueNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("okTooltip",nameTextField.getTooltip().getText());
		assertFalse(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertTrue(uniqueNameValidator.isValid());
		
		support.verifyAll();
	}

}
