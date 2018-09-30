package org.fcpe.fantinlatour.app.controller.validator;

import org.apache.commons.validator.routines.EmailValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class EmailTextValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private String tooltipText;
	
	public EmailTextValidator(SceneValidator sceneValidator, TextField nameTextField, String validTootipText,
			String invalidTootipText) {
		super(sceneValidator, nameTextField, validTootipText, invalidTootipText);
	
	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		updateTooltipText(newValue);
		updateControl();
		
	}
	
	private void updateTooltipText(String newValue) {
		tooltipText = validTootipText;
		if (!EmailValidator.getInstance().isValid(newValue)) {

			tooltipText = invalidTootipText;

		} 

	}
	
	

}
