package org.fcpe.fantinlatour.app.controller.validator;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class DomainValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private String tooltipText;
	
	public DomainValidator(SceneValidator sceneValidator, TextField nameTextField, String validTootipText,
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
		
		if (!org.apache.commons.validator.routines.DomainValidator.getInstance().isValid(newValue)) {

			tooltipText = invalidTootipText;

		} 

	}
	
	

}
