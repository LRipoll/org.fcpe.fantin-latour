package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.DomainValidator;
import org.fcpe.fantinlatour.app.controller.validator.EmailValidator;
import org.fcpe.fantinlatour.app.controller.validator.IntegerValidator;
import org.fcpe.fantinlatour.app.controller.validator.MandatoryListValidator;
import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.model.EmailSenderProtocol;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class ConfigureEmailAccountController extends AbstractController {

	private static final int PORT_MAX = 65536;
	private static final int PORT_MIN = 0;
	static final String EMAIL_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.email.tooltip.invalid";
	static final String EMAIL_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.email.tooltip.valid";
	static final String PASSWORD_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.password.tooltip.invalid";
	static final String PASSWORD_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.password.tooltip.valid";
	static final String PROTOCOL_SELECTED = "org.fcpe.fantinlatour.view.configureemailaccount.protocol.tooltip.valid";
	static final String PROTOCOL_UNSELECTED = "org.fcpe.fantinlatour.view.configureemailaccount.protocol.tooltip.invalid";
	static final String HOST_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.host.tooltip.valid";
	static final String HOST_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.host.tooltip.invalid";
	static final String PORT_VALID = "org.fcpe.fantinlatour.view.configureemailaccount.port.tooltip.valid";
	static final String PORT_INVALID = "org.fcpe.fantinlatour.view.configureemailaccount.port.tooltip.invalid";
	
	protected ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@FXML
	private TextField emailTextField;	
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private ComboBox<EmailSenderProtocol> protocolComboBox;
	@FXML
	private ObservableList<EmailSenderProtocol> listProtocol;
	@FXML
	private TextField hostTextField;
	@FXML
	private TextField portTextField;

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
		MandatoryListValidator<EmailSenderProtocol> mandatoryListValidator = new MandatoryListValidator<EmailSenderProtocol>(
				sceneValidator, protocolComboBox, resources.getString(PROTOCOL_SELECTED), resources.getString(PROTOCOL_UNSELECTED));
		protocolComboBox.valueProperty().addListener((ChangeListener<? super EmailSenderProtocol>) mandatoryListValidator);
		hostTextField.textProperty().addListener(new DomainValidator(sceneValidator, hostTextField,
				resources.getString(HOST_VALID), resources.getString(HOST_INVALID)));
		portTextField.textProperty().addListener(new IntegerValidator(sceneValidator, portTextField,
				resources.getString(PORT_VALID), resources.getString(PORT_INVALID),PORT_MIN,PORT_MAX));

	}
}
