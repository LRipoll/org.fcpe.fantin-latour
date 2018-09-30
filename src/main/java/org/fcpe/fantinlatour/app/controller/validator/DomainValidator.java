package org.fcpe.fantinlatour.app.controller.validator;

import org.apache.commons.validator.routines.DomainValidator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DomainTextValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private String tooltipText;
	
	public DomainTextValidator(SceneValidator sceneValidator, TextField nameTextField, String validTootipText,
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
		
		if (!DomainValidator.getInstance().isValid(newValue)) {

			tooltipText = invalidTootipText;

		} 

	}
	
	

}
