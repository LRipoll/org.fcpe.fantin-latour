package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.CombinedPasswordValidator;
import org.fcpe.fantinlatour.app.controller.validator.MandatoryListListener;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.dao.security.EncryptHelper;
import org.fcpe.fantinlatour.model.TypeEtablissement;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;

public class ConseilLocalEtablissementController extends AbstractConseilLocalController {


	private static final String TYPE_ETABLISSEMENT_UNSELECTED = "org.fcpe.fantinlatour.view.newconseillocal.type.tooltip.unselected";
	private static final String PASSWORD_INVALID = "org.fcpe.fantinlatour.view.newconseillocal.password.tooltip.invalid";

	@FXML 
	private PasswordField passwordTextField;
	@FXML 
	private PasswordField confirmpasswordTextField;
	@FXML
	private ComboBox<TypeEtablissement> typeComboBox;
	@FXML
	private ObservableList<TypeEtablissement> listType;
	@FXML
	private CheckBox defaultCheckBox;

	public ConseilLocalEtablissementController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);
		listType = FXCollections.observableArrayList(TypeEtablissement.values());
		typeComboBox.setItems(listType);

		defaultCheckBox.setSelected(true);
		MandatoryListListener<TypeEtablissement> mandatoryListListener = new MandatoryListListener<TypeEtablissement>(sceneValidator, typeComboBox, "",
				resources.getString(TYPE_ETABLISSEMENT_UNSELECTED));
		typeComboBox.valueProperty().addListener((ChangeListener<? super TypeEtablissement>) mandatoryListListener);
		
		CombinedPasswordValidator combinedPasswordValidator = new CombinedPasswordValidator(sceneValidator, passwordTextField, confirmpasswordTextField, "", resources.getString(PASSWORD_INVALID));
		passwordTextField.textProperty().addListener(combinedPasswordValidator);
		confirmpasswordTextField.textProperty().addListener(combinedPasswordValidator);
	}

	@Override
	protected void execute(ActionEvent event) {
		try {
			
			conseilLocalEtablissementManager.create(nameTextField.getText(), typeComboBox.getValue(),
					defaultCheckBox.isSelected());
			getEncryptHelper().setPassword(passwordTextField.getText());
			super.execute(event);

		} catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
			confirmpasswordTextField.clear();
		}

	}
	
	private EncryptHelper getEncryptHelper() {
		EncryptHelper encryptHelper = SpringFactory.getService(EncryptHelper.ID);
		return encryptHelper;
	}

}
