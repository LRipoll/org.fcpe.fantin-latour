package org.fcpe.fantinlatour.app.controller.validator;

import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class ImportedArchiveNameValidator extends AbstractControlValidatorListener implements ChangeListener<String> {

	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	private String alreadyExistTootipText;
	private String tooltipText;
	private String invalidFilenameTootipText;

	public ImportedArchiveNameValidator(SceneValidator sceneValidator,
			ConseilLocalEtablissementManager conseilLocalEtablissementManager, TextField nameTextField,
			String validTootipText, String alreadyExistTootipText, String invalidTootipText , String invalidFilenameTootipText) {
		super(sceneValidator, nameTextField, validTootipText, invalidTootipText);

		this.conseilLocalEtablissementManager = conseilLocalEtablissementManager;
		this.alreadyExistTootipText = alreadyExistTootipText;
		this.invalidFilenameTootipText = invalidFilenameTootipText;

	}

	@Override
	public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {

		updateTooltipText(newValue);
		updateControl();
	}

	private void updateTooltipText(String filename) {
		tooltipText = validTootipText;
		
		if (!conseilLocalEtablissementManager.isValidArchiveFilename(filename)) {
			tooltipText = invalidFilenameTootipText;
		}
		else if (conseilLocalEtablissementManager.existsFromArchiveFilename(filename)) {

			tooltipText = alreadyExistTootipText;
		} else if (!conseilLocalEtablissementManager.isValidFromArchiveFilename(filename)) {

			tooltipText = invalidTootipText;
		}
		// TODO vérifier qu'il s'agit bien d'une archive bien formée

	}

	@Override
	protected String getTooltipText() {

		return tooltipText;
	}
	
}
