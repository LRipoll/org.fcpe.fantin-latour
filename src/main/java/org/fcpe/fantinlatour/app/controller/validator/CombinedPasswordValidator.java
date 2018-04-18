package org.fcpe.fantinlatour.app.controller.validator;

import org.fcpe.fantinlatour.dao.security.EncryptHelper;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;

public class CombinedPasswordValidator extends AbstractValidatorListener implements ChangeListener<String> {

	private PasswordField validatePassword;
	private PasswordField validateConfirmPassword;
	private String tooltipText;
	private String incoherentTooltipTest;
	private EncryptHelper encryptHelper;

	public CombinedPasswordValidator(SceneValidator sceneValidator, EncryptHelper encryptHelper, PasswordField validatePassword,
			PasswordField validateConfirmPassword, String validTooltipText, String invalidTooltipText, String incoherentTooltipTest) {
		super(sceneValidator, validTooltipText, invalidTooltipText);
		this.validatePassword = validatePassword;
		this.validateConfirmPassword = validateConfirmPassword;
		this.incoherentTooltipTest = incoherentTooltipTest;
		this.encryptHelper = encryptHelper;
	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	void updateStyle() {
		if (!isValid) {
			validatePassword.getStyleClass().add(TEXT_FIELD_ERROR);
			validateConfirmPassword.getStyleClass().add(TEXT_FIELD_ERROR);
		} else {
			validatePassword.getStyleClass().remove(TEXT_FIELD_ERROR);
			validateConfirmPassword.getStyleClass().remove(TEXT_FIELD_ERROR);
		}

	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		updateTooltipText(newValue);
		updateControl();

	}

	
	private void updateTooltipText(String newValue) {
		tooltipText = validTootipText;
		if (!encryptHelper.isValid(newValue)) {
			tooltipText = invalidTootipText;
		} else if (!validatePassword.getText().equals(validateConfirmPassword.getText())) {
			tooltipText = incoherentTooltipTest;
		}

	}
	
	@Override
	String updateTooltipText() {
		String tooltipText = super.updateTooltipText();
		validatePassword.setTooltip(new Tooltip(tooltipText));
		validateConfirmPassword.setTooltip(new Tooltip(tooltipText));
		return tooltipText;
	}

}
