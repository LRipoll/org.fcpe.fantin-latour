package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.EmailValidator;
import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.model.EmailSenderProtocol;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ConfigureEmailAccountController extends AbstractController {

	static final String EMAIL_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.email.tooltip.invalid";
	static final String EMAIL_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.email.tooltip.valid";
	static final String PASSWORD_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.password.tooltip.invalid";
	static final String PASSWORD_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.password.tooltip.valid";
	
	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@FXML
	private TextField emailTextField;	
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private ComboBox<EmailSenderProtocol> protocolComboBox;
	@FXML
	private ObservableList<EmailSenderProtocol> listProtocol;

	public ConfigureEmailAccountController() {
		super();
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);


		emailTextField.textProperty().addListener(new EmailValidator(sceneValidator, emailTextField,
				resources.getString(EMAIL_VALID), resources.getString(EMAIL_INVALID)));
		passwordTextField.textProperty().addListener(new PasswordValidator(sceneValidator, passwordTextField,
				resources.getString(PASSWORD_VALID), resources.getString(PASSWORD_INVALID)));
		listProtocol = FXCollections.observableArrayList(EmailSenderProtocol.values());
		protocolComboBox.setItems(listProtocol);

	}
}
