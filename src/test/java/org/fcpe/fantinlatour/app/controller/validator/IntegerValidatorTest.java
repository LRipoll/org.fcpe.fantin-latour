package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;

public class IntegerValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private TextField nameTextField;
	private SceneValidator sceneValidator;
	
	private IntegerValidator integerValidator;
	
	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		
		latch.await();
		
		ctrl = support.createControl();
		
		
		nameTextField = new TextField();
		sceneValidator = ctrl.createMock(SceneValidator.class);
		integerValidator = new IntegerValidator(sceneValidator, nameTextField, "okTooltip",
				 "invalidTooltip",-1,1);
		EasyMock.reset(sceneValidator);

	}
	
	@Test
	public void testWhenIntegerIsInvalidShouldSetAccordingToolTipAndStyle() {

		
		String value = "invalid";
		
		support.replayAll();
		nameTextField.setText(value);
		integerValidator.changed(nameTextField.textProperty(), "", value);

		assertEquals("invalidTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertFalse(integerValidator.isValid());
		assertNull(integerValidator.getValue());
		
		
		support.verifyAll();
	}
	
	@Test
	public void testWhenIntegerIsOutRangeShouldSetAccordingToolTipAndStyle() {

		
		String value = "-2";
		
		
		support.replayAll();
		
		nameTextField.setText(value);
		integerValidator.changed(nameTextField.textProperty(), "", value);

		assertEquals("invalidTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertFalse(integerValidator.isValid());
		assertNull(integerValidator.getValue());
		
		support.verifyAll();
	}
	
	@Test
	public void testWhenIntegeIsValidShouldSetAccordingToolTipAndStyle() {

		
		String value = "0";
		
		sceneValidator.onStateChange(integerValidator);
		EasyMock.expectLastCall().once();
		
		support.replayAll();
		nameTextField.setText(value);
		integerValidator.changed(nameTextField.textProperty(), "", value);

		assertEquals("okTooltip",nameTextField.getTooltip().getText());
		assertFalse(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertTrue(integerValidator.isValid());
		assertEquals(0, integerValidator.getValue().intValue());
		support.verifyAll();
	}
}
