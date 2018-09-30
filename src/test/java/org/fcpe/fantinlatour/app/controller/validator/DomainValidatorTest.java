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
import javafx.scene.control.TextField;

public class DomainValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private TextField nameTextField;
	private SceneValidator sceneValidator;
	
	private DomainValidator domainValidator;
	
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
		domainValidator = new DomainValidator(sceneValidator, nameTextField, "okTooltip",
				 "invalidTooltip");
		EasyMock.reset(sceneValidator);

	}
	
	@Test
	public void testWhenDomainIsInvalidShouldSetAccordingToolTipAndStyle() {

		
		String name = "invalid";
		
		support.replayAll();
		nameTextField.setText(name);
		domainValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("invalidTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertFalse(domainValidator.isValid());
		
		
		support.verifyAll();
	}
	
	@Test
	public void testWhenDomainIsValidShouldSetAccordingToolTipAndStyle() {

		
		String name = "valid.domain.com";
		
		sceneValidator.onStateChange(domainValidator);
		EasyMock.expectLastCall().once();
		
		support.replayAll();
		nameTextField.setText(name);
		domainValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("okTooltip",nameTextField.getTooltip().getText());
		assertFalse(nameTextField.getStyleClass().contains(UniqueNameValidator.TEXT_FIELD_ERROR));
		assertTrue(domainValidator.isValid());
		
		support.verifyAll();
	}
}
