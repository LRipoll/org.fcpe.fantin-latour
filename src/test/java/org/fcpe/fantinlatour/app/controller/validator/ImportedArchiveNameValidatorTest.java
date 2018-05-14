package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.junit.Before;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;

public class ImportedArchiveNameValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;

	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	private TextField nameTextField;
	private SceneValidator sceneValidator;
	
	private ImportedArchiveNameValidator importedArchiveNameValidator;

	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		
		latch.await();
		
		ctrl = support.createControl();
		conseilLocalEtablissementManager = ctrl.createMock(ConseilLocalEtablissementManager.class);
		
		nameTextField = new TextField();
		sceneValidator = ctrl.createMock(SceneValidator.class);
		importedArchiveNameValidator = new ImportedArchiveNameValidator(sceneValidator, conseilLocalEtablissementManager, nameTextField, "okTooltip",
				"existTooltip", "invalidTooltip" ,"invalidFilenameTooltip", "archiveFileDoesNotExistTootip",
				"invalidArchiveFileTootip", "unencryptedArchiveFileTootip","incompleteArchiveFileTootip");
		EasyMock.reset(sceneValidator);

	}

	@Test
	public void testWhenNameExistShouldSetAccordingToolTipAndStyle() {

		
		String name = "existname";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsFromArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isEncryptedArchiveFile(name)).andReturn(true);
		support.replayAll();
		
		
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);
		
		assertEquals("existTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());

		support.verifyAll();
	}
	
	@Test
	public void testWhenFileNotExistShouldSetAccordingToolTipAndStyle() {

		
		String name = "existname";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(false);
		support.replayAll();
		
		
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);
		
		assertEquals("archiveFileDoesNotExistTootip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());

		support.verifyAll();
	}
	
	@Test
	public void testWhenArchiveFileIsInvalidSetAccordingToolTipAndStyle() {

		
		String name = "existname";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(false);
		support.replayAll();
		
		
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);
		
		assertEquals("invalidArchiveFileTootip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());

		support.verifyAll();
	}
	
	@Test
	public void testWhenArchiveFileIsNotEncryptedSetAccordingToolTipAndStyle() {

		
		String name = "existname";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isEncryptedArchiveFile(name)).andReturn(false);
		
		support.replayAll();
		
		
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);
		
		assertEquals("unencryptedArchiveFileTootip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());

		support.verifyAll();
	}
	@Test
	public void testWhenNameIsInvalidShouldSetAccordingToolTipAndStyle() {

		
		String name = "invalid";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsFromArchiveFilename(name)).andReturn(false);
		EasyMock.expect(conseilLocalEtablissementManager.isValidFromArchiveFilename(name)).andReturn(false);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isEncryptedArchiveFile(name)).andReturn(true);
		
		support.replayAll();
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("invalidTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());
		
		
		support.verifyAll();
	}

	
	@Test
	public void testWhenFilenameIsInvalidShouldSetAccordingToolTipAndStyle() {

		
		String name = "invalid";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(false);
		
		
		support.replayAll();
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("invalidFilenameTooltip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());
		

		support.verifyAll();
	}

	@Test
	public void testWhenNameIsValidShouldSetAccordingToolTipAndStyle() {

		
		String name = "valid";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsFromArchiveFilename(name)).andReturn(false);
		EasyMock.expect(conseilLocalEtablissementManager.isValidFromArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isEncryptedArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.containsExpectedArchives(name)).andReturn(true);
		sceneValidator.onStateChange(importedArchiveNameValidator);
		EasyMock.expectLastCall().once();
		
		support.replayAll();
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("okTooltip",nameTextField.getTooltip().getText());
		assertFalse(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertTrue(importedArchiveNameValidator.isValid());
		
		support.verifyAll();
	}

	@Test
	public void testWhenArchiveIsIncompleteShouldSetAccordingToolTipAndStyle() {

		
		String name = "valid";
		
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsFromArchiveFilename(name)).andReturn(false);
		EasyMock.expect(conseilLocalEtablissementManager.isValidFromArchiveFilename(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.existsArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isValidArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.isEncryptedArchiveFile(name)).andReturn(true);
		EasyMock.expect(conseilLocalEtablissementManager.containsExpectedArchives(name)).andReturn(false);
	
		
		support.replayAll();
		nameTextField.setText(name);
		importedArchiveNameValidator.changed(nameTextField.textProperty(), "", name);

		assertEquals("incompleteArchiveFileTootip",nameTextField.getTooltip().getText());
		assertTrue(nameTextField.getStyleClass().contains(ImportedArchiveNameValidator.TEXT_FIELD_ERROR));
		assertFalse(importedArchiveNameValidator.isValid());
		
		support.verifyAll();
	}
}
