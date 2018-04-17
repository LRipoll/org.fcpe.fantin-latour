package org.fcpe.fantinlatour.app.controller.validator;

import javafx.scene.control.PasswordField;

public class CombinedPasswordValidator extends AbstractValidatorListener {

	private PasswordField validatePassword;
	private PasswordField validateConfirmPassword;
	private String tooltipText;
	
	public CombinedPasswordValidator(SceneValidator sceneValidator, PasswordField validatePassword, PasswordField validateConfirmPassword, String validTootipText, String invalidTootipText) {
		super(sceneValidator, validTootipText, invalidTootipText);
		this.validatePassword = validatePassword;
		this.validateConfirmPassword = validateConfirmPassword;
	}

	@Override
	public boolean isValid() {
		
		return validatePassword.getText()!=null && validatePassword.getText().equals(validateConfirmPassword.getText());
	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	void updateStyle() {
		if (!isValid) {
			validatePassword.getStyleClass().add(TEXT_FIELD_ERROR);
		} else {
			validateConfirmPassword.getStyleClass().remove(TEXT_FIELD_ERROR);
		}
		
	}

}
