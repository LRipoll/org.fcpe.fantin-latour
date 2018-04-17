package org.fcpe.fantinlatour.app.controller.validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;

public class CombinedPasswordValidator extends AbstractValidatorListener implements ChangeListener<String> {

	private PasswordField validatePassword;
	private PasswordField validateConfirmPassword;
	private String tooltipText;

	public CombinedPasswordValidator(SceneValidator sceneValidator, PasswordField validatePassword,
			PasswordField validateConfirmPassword, String validTootipText, String invalidTootipText) {
		super(sceneValidator, validTootipText, invalidTootipText);
		this.validatePassword = validatePassword;
		this.validateConfirmPassword = validateConfirmPassword;
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
		if (validatePassword.getText() == null || validatePassword.getText().trim().length() == 0
				|| !validatePassword.getText().equals(validateConfirmPassword.getText())) {
			tooltipText = invalidTootipText;

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
