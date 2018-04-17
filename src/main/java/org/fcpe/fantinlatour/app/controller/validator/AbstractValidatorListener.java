package org.fcpe.fantinlatour.app.controller.validator;

public abstract class AbstractValidatorListener implements ControlValidator {

	protected static final String TEXT_FIELD_ERROR = "textFieldError";
	protected String validTootipText;
	protected String invalidTootipText;
	protected boolean isValid;
	protected SceneValidator sceneValidator;

	public AbstractValidatorListener(SceneValidator sceneValidator, String validTootipText, String invalidTootipText) {
		super();
		this.validTootipText = validTootipText;
		this.invalidTootipText = invalidTootipText;
		this.sceneValidator = sceneValidator;
		sceneValidator.add(this);
		isValid = false;
	}

	protected abstract String getTooltipText();

	protected void updateControl() {
		updateTooltipText();
		updateStyle();
	}

	@Override
	public boolean isValid() {
		return isValid;
	}
	
	abstract void updateStyle() ;
	

	String updateTooltipText() {
		String tooltipText = getTooltipText();

		boolean wasValid = isValid;
		
		isValid = tooltipText.equals(validTootipText);
		if (isValid!=wasValid) {
			sceneValidator.onStateChange(this);
		}
		
		return tooltipText;
	}

}