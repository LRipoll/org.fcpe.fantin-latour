package org.fcpe.fantinlatour.app.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.controller.validator.DomainValidator;
import org.fcpe.fantinlatour.app.controller.validator.EmailValidator;
import org.fcpe.fantinlatour.app.controller.validator.IntegerValidator;
import org.fcpe.fantinlatour.app.controller.validator.MandatoryListValidator;
import org.fcpe.fantinlatour.app.controller.validator.PasswordValidator;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.EmailSenderProtocol;
import org.fcpe.fantinlatour.model.MailSenderAccount;
import org.fcpe.fantinlatour.model.MailSenderProperties;
import org.fcpe.fantinlatour.model.SMTPProperties;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


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
	private IntegerValidator portValidator;
	
	@FXML
	private CheckBox authCheckBox;
	@FXML
	private CheckBox starttlsEnabledCheckBox;

	public ConfigureEmailAccountController() {
		super();
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		
		emailTextField.textProperty().addListener(new EmailValidator(sceneValidator, emailTextField,
				resources.getString(EMAIL_VALID), resources.getString(EMAIL_INVALID)));
		emailTextField.setText(getMailSenderAccount().getUsername());
		
		MailSenderProperties properties = getMailSenderAccount().getProperties();
		
		passwordTextField.textProperty().addListener(new PasswordValidator(sceneValidator, passwordTextField,
				resources.getString(PASSWORD_VALID), resources.getString(PASSWORD_INVALID)));
		passwordTextField.setText(properties.getPassword());
		
		listProtocol = FXCollections.observableArrayList(EmailSenderProtocol.values());
		protocolComboBox.setItems(listProtocol);
		MandatoryListValidator<EmailSenderProtocol> mandatoryListValidator = new MandatoryListValidator<EmailSenderProtocol>(
				sceneValidator, protocolComboBox, resources.getString(PROTOCOL_SELECTED), resources.getString(PROTOCOL_UNSELECTED));
		
		
		protocolComboBox.valueProperty().addListener((ChangeListener<? super EmailSenderProtocol>) mandatoryListValidator);
		protocolComboBox.getSelectionModel().select(properties.getProtocol());
		
		
		
		hostTextField.textProperty().addListener(new DomainValidator(sceneValidator, hostTextField,
				resources.getString(HOST_VALID), resources.getString(HOST_INVALID)));
		hostTextField.setText(properties.getHost());
		
		
		portValidator = new IntegerValidator(sceneValidator, portTextField,
				resources.getString(PORT_VALID), resources.getString(PORT_INVALID),PORT_MIN,PORT_MAX);
		portTextField.textProperty().addListener(portValidator);
		portTextField.setText(""+properties.getPort());

		SMTPProperties smtpProperties = (SMTPProperties)properties.getEmailSenderProtocolProperties();
		authCheckBox.setSelected(smtpProperties!=null && smtpProperties.isAuth());
		starttlsEnabledCheckBox.setSelected(smtpProperties!=null && smtpProperties.isStarttls());
	}

	@Override
	protected void execute(ActionEvent event) {
		try {

			
			getMailSenderAccount().setUserName(emailTextField.getText());
			MailSenderProperties properties = getMailSenderAccount().getProperties();
			properties.setPassword(passwordTextField.getText());
			properties.setHost(hostTextField.getText());
			properties.setPort(portValidator.getValue());
			properties.setProtocol(protocolComboBox.getValue());
			SMTPProperties smtpProperties = (SMTPProperties)properties.getEmailSenderProtocolProperties();
			smtpProperties.setAuth(authCheckBox.isSelected());
			smtpProperties.setStarttls(starttlsEnabledCheckBox.isSelected());
			conseilLocalEtablissementManager.store();
			super.execute(event);

		} catch (DataException e) {

			ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), e);
			exceptionAlertDialog.showAndWait();

		} finally {
			passwordTextField.clear();
			
		}

	}
	private MailSenderAccount getMailSenderAccount() {
		
		return conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement().getMailSenderAccount();
	}
}
