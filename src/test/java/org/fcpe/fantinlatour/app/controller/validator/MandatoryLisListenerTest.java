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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.ComboBox;

public class MandatoryLisListenerTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private ComboBox<String> comboBox;
	private SceneValidator sceneValidator;
	private ObservableList<String> listObjects;
	private MandatoryListListener<String> mandatoryListListener;

	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		latch.await();

		ctrl = support.createControl();

		comboBox = new ComboBox<String>();

		sceneValidator = ctrl.createMock(SceneValidator.class);

		mandatoryListListener = new MandatoryListListener<String>(sceneValidator, comboBox, "okTooltip",
				"unselectedTooltip");
		listObjects = FXCollections.observableArrayList( "A", "B",null);

		comboBox.valueProperty().addListener(mandatoryListListener);
		EasyMock.reset(sceneValidator);

	}


	@Test
	public void testWhenNameIsValidShouldSetAccordingToolTipAndStyle() {
	
		comboBox.setValue("A");
		EasyMock.reset(sceneValidator);
		support.replayAll();

		assertEquals("okTooltip", comboBox.getTooltip().getText());
		assertFalse(comboBox.getStyleClass().contains(MandatoryListListener.TEXT_FIELD_ERROR));
		assertTrue(mandatoryListListener.isValid());

		support.verifyAll();
	}
	
	@Test
	public void testWhenNameIsInvalidShouldSetAccordingToolTipAndStyle() {
	
		comboBox.setValue(null);
		EasyMock.reset(sceneValidator);
		support.replayAll();

		assertEquals("okTooltip", comboBox.getTooltip().getText());
		assertFalse(comboBox.getStyleClass().contains(MandatoryListListener.TEXT_FIELD_ERROR));
		assertTrue(mandatoryListListener.isValid());

		support.verifyAll();
	}

}
