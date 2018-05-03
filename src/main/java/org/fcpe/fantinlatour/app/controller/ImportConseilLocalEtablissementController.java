package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.PasswordException;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ImportConseilLocalEtablissementController extends AbstractSecureController {

	@FXML
	private CheckBox defaultCheckBox;
	@FXML
	private TextField fileTextField;
	
	public ImportConseilLocalEtablissementController() {
		
	}
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);
		defaultCheckBox.setSelected(true);
	}



	@Override
	protected void execute(ActionEvent event) {
		try {
			
			getEncryptHelper().setPassword(passwordTextField.getText());
			conseilLocalEtablissementManager.importArchive(fileTextField.getText(),defaultCheckBox.isSelected(), passwordTextField.getText());
			super.execute(event);

		} catch (PasswordException e) {
			passwordTextField.setText(null);
		} 
		catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
		}

	}

}
