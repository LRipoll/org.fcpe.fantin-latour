package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.ImportedArchiveNameValidator;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;

public class ImportConseilLocalEtablissementController extends AbstractSecureController {

	static final String NAME_INVALID = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.invalid";
	static final String NAME_INVALID_FILENAME = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.invalidFilename";
	static final String NAME_ALREADY_EXIST = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.alreadyExist";
	static final String NAME_VALID = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.valid";
	static final String NOT_EXISTING_FILE = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.not_existing";
	static final String INVALID_ARCHIVE = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.invalid_archive";
	static final String NOT_ENCRYPTED = "org.fcpe.fantinlatour.view.importconseillocal.file.tooltip.not_encrypted";
	

	@FXML
	private CheckBox defaultCheckBox;
	@FXML
	private TextField fileTextField;
	private ImportedArchiveNameValidator importedArchiveNameValidator;

	public ImportConseilLocalEtablissementController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		defaultCheckBox.setSelected(true);

		importedArchiveNameValidator = new ImportedArchiveNameValidator(sceneValidator,
				conseilLocalEtablissementManager, fileTextField, resources.getString(NAME_VALID),
				resources.getString(NAME_ALREADY_EXIST), resources.getString(NAME_INVALID), String.format(
						NAME_INVALID_FILENAME, conseilLocalEtablissementManager.getExportFilenameWildcardMatcher()), 
				resources.getString(NOT_EXISTING_FILE), resources.getString(INVALID_ARCHIVE), resources.getString(NOT_ENCRYPTED));
		fileTextField.textProperty().addListener(importedArchiveNameValidator);
	}

	@Override
	protected void execute(ActionEvent event) {
		try {

			getEncryptHelper().setPassword(passwordTextField.getText());
			conseilLocalEtablissementManager.importArchive(fileTextField.getText(), defaultCheckBox.isSelected(),
					passwordTextField.getText());
			super.execute(event);

		} catch (PasswordException e) {
			passwordTextField.setText(null);
		} catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
		}

	}

}
