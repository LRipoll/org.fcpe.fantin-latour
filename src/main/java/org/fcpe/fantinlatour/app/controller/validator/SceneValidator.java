package org.fcpe.fantinlatour.app.controller.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.scene.control.Button;

public class SceneValidator {

	private Button okButton;
	private List<ControlValidator> controlValidators;
	private boolean isValid;
	public SceneValidator(Button okButton) {
		this(okButton,false);
		
	}
	
	public SceneValidator(Button okButton, boolean isValidByDefault) {
		this.okButton = okButton;
		controlValidators = new ArrayList<ControlValidator>();
		isValid = isValidByDefault;
		okButton.setDisable(!isValid);
	}
	
	
	public void onStateChange(ControlValidator controlValidator) {
		isValid = true;
		Iterator<ControlValidator> validators = controlValidators.iterator();
		while(validators.hasNext() && isValid) {
			isValid = validators.next().isValid();
		}
		
		okButton.setDisable(!isValid);
	}


	public void add(ControlValidator controlValidator) {
		controlValidators.add(controlValidator);
		
	}

	public boolean isValid() {
		return isValid;
	}

}
