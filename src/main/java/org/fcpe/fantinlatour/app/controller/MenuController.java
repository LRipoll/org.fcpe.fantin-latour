package org.fcpe.fantinlatour.app.controller;

import java.util.Optional;
import java.util.ResourceBundle;

import org.fcpe.fantinlatour.app.context.AppContext;
import org.fcpe.fantinlatour.app.controller.validator.MenuValidator;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManagerListener;
import org.fcpe.fantinlatour.service.SpringFactory;
import org.fcpe.fantinlatour.view.AlertDialog;
import org.fcpe.fantinlatour.view.ConfirmationAlertDialog;
import org.fcpe.fantinlatour.view.ExceptionAlertDialog;
import org.fcpe.fantinlatour.view.ViewFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController implements Initializable, ConseilLocalEtablissementManagerListener {

	private static final String DELETECONSEILLOCAL_HEADER_TEXT = "org.fcpe.fantinlatour.view.deleteconseillocal.headerText";
	private static final String DELETECONSEILLOCAL_TEXT = "org.fcpe.fantinlatour.view.deleteconseillocal.text";

	private static final String SET_DEFAULT_CONSEILLOCAL_HEADER_TEXT = "org.fcpe.fantinlatour.view.setdefaultconseillocal.headerText";
	private static final String SET_DEFAULT_CONSEILLOCAL_TEXT = "org.fcpe.fantinlatour.view.setdefaultconseillocal.text";

	private static final String APP_PROJECT_TIMESTAMP = "org.fcpe.fantinlatour.app.project.timestamp";
	private static final String APP_PROJECT_GROUP_ID = "org.fcpe.fantinlatour.app.project.groupId";
	private static final String ABOUT_VERSION_TEXT = "org.fcpe.fantinlatour.view.about.versionText";
	private static final String MAIN_DEFINED_TITLE = "org.fcpe.fantinlatour.view.main.defined.title";
	private static final String MAIN_UNDEFINED_TITLE = "org.fcpe.fantinlatour.view.main.title";
	private static final String ABOUT_TITLE = "about";
	private static final String APP_PROJECT_VERSION = "org.fcpe.fantinlatour.app.project.version";
	private static final String ABOUT_HEADER_TEXT = "org.fcpe.fantinlatour.view.about.headerText";

	private static final String UNIMPLEMENTED_FUNCTION_TITLE = "unimplemented";
	private static final String UNIMPLEMENTED_HEADER_TEXT = "org.fcpe.fantinlatour.view.unimplemented.headerText";
	private static final String UNIMPLEMENTED_TEXT = "org.fcpe.fantinlatour.view.unimplemented.text";

	@FXML
	private MenuBar menuBar;
	private ViewFactory viewFactory;

	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	@FXML
	private void handleNew(final ActionEvent event) {

		viewFactory.createStage("newconseillocal", Modality.APPLICATION_MODAL).show();

	}

	public void handleOpen(final ActionEvent event) {
		MenuItem source = (MenuItem) event.getSource();
		String conseiLocalName = (String) source.getUserData();
		provideOpenFunctionnality(conseiLocalName);
	}

	/**
	 * Handle action related to "Sortir" menu item.
	 * 
	 * @param event
	 *            Event on "Sortir" menu item.
	 */
	@FXML
	private void handleQuit(final ActionEvent event) {
		System.exit(0);

	}

	/**
	 * Handle action related to "About" menu item.
	 * 
	 * @param event
	 *            Event on "About" menu item.
	 */
	@FXML
	private void handleAboutAction(final ActionEvent event) {
		provideAboutFunctionality();
	}

	@FXML
	private void handleRenameConseilLocal(final ActionEvent event) {

		viewFactory.createStage("renameconseillocal", Modality.APPLICATION_MODAL).show();
	}

	@FXML
	private void handleDeleteConseilLocal(final ActionEvent event) {

		ConfirmationAlertDialog confirmationAlertDialog = new ConfirmationAlertDialog(new Alert(AlertType.CONFIRMATION),
				SpringFactory.getMessage(DELETECONSEILLOCAL_HEADER_TEXT),
				SpringFactory.getMessage(DELETECONSEILLOCAL_TEXT,
						new Object[] {
								conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement()
										.getEtablissement().getTypeEtablissement(),
								conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement()
										.getEtablissement().getNom() }));

		Optional<ButtonType> result = confirmationAlertDialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				conseilLocalEtablissementManager.delete();
			} catch (DataException e) {
				showExceptionAlertDialog(e);
			}
		}
	}

	@FXML
	private void handleSetDefaultConseilLocal(final ActionEvent event) {
		ConfirmationAlertDialog confirmationAlertDialog = new ConfirmationAlertDialog(new Alert(AlertType.CONFIRMATION),
				SpringFactory.getMessage(SET_DEFAULT_CONSEILLOCAL_HEADER_TEXT),
				SpringFactory.getMessage(SET_DEFAULT_CONSEILLOCAL_TEXT,
						new Object[] {
								conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement()
										.getEtablissement().getTypeEtablissement(),
								conseilLocalEtablissementManager.getCurrentConseilLocalEtablissement()
										.getEtablissement().getNom() }));

		Optional<ButtonType> result = confirmationAlertDialog.showAndWait();
		if (result.get() == ButtonType.OK) {
			try {
				conseilLocalEtablissementManager.setAsDefault();
			} catch (DataException e) {
				showExceptionAlertDialog(e);
			}
		}
	}

	@FXML
	private void handleManageMailinglist(final ActionEvent event) {
		provideUnsupportedFunction();
	}

	@FXML
	private void handleImportConseilLocal(final ActionEvent event) {
		provideUnsupportedFunction();
	}

	@FXML
	private void handleExportConseilLocal(final ActionEvent event) {
		provideUnsupportedFunction();
	}

	@FXML
	private void handleSetMailinglist(final ActionEvent event) {
		provideUnsupportedFunction();
	}

	private void provideUnsupportedFunction() {
		AlertDialog alert = new AlertDialog(new Alert(AlertType.ERROR), UNIMPLEMENTED_FUNCTION_TITLE,
				UNIMPLEMENTED_HEADER_TEXT, SpringFactory.getMessage(UNIMPLEMENTED_TEXT));

		alert.showAndWait();

	}

	/**
	 * Handle action related to input (in this case specifically only responds to
	 * keyboard event CTRL-A).
	 * 
	 * @param event
	 *            Input event.
	 */
	@FXML
	private void handleKeyInput(final InputEvent event) {
		if (event instanceof KeyEvent) {
			final KeyEvent keyEvent = (KeyEvent) event;
			if (keyEvent.isControlDown() && keyEvent.getCode() == KeyCode.A) {
				provideAboutFunctionality();
			}
		}
	}

	/**
	 * Perform functionality associated with "About" menu selection or CTRL-A.
	 */
	private void provideAboutFunctionality() {

		AlertDialog alert = new AlertDialog(new Alert(AlertType.INFORMATION), ABOUT_TITLE, ABOUT_HEADER_TEXT,
				SpringFactory.getMessage(ABOUT_VERSION_TEXT,
						new String[] { SpringFactory.getMessage(APP_PROJECT_VERSION),
								SpringFactory.getMessage(APP_PROJECT_TIMESTAMP),
								SpringFactory.getMessage(APP_PROJECT_GROUP_ID) }));

		alert.showAndWait();
	}

	@Override
	public void initialize(java.net.URL arg0, ResourceBundle arg1) {
		viewFactory = SpringFactory.getService(ViewFactory.ID);
		conseilLocalEtablissementManager = SpringFactory.getService(ConseilLocalEtablissementManager.ID);

		MenuValidator menuValidator = new MenuValidator(this, menuBar, conseilLocalEtablissementManager);
		conseilLocalEtablissementManager.addListener(menuValidator);

		conseilLocalEtablissementManager.addListener(this);
		menuBar.setFocusTraversable(true);

	}

	protected Stage getStage() {

		return (Stage) menuBar.getScene().getWindow();
	}

	@Override
	public void onSelected(ConseilLocalEtablissement conseilLocalEtablissement) {
		String title = SpringFactory.getMessage(MAIN_UNDEFINED_TITLE);
		if (conseilLocalEtablissement != null) {
			title = SpringFactory.getMessage(MAIN_DEFINED_TITLE,
					new String[] { conseilLocalEtablissement.getEtablissement().getTypeEtablissement().toString(),
							conseilLocalEtablissement.getEtablissement().getNom() });
			getStage().setTitle(title);
		} else
			try {
				String defaultConseilLocal = conseilLocalEtablissementManager.getDefault();
				if (defaultConseilLocal != null) {
					provideOpenFunctionnality(defaultConseilLocal);
				}
			} catch (DataException e) {
				showExceptionAlertDialog(e);
			}

	}

	protected void showExceptionAlertDialog(DataException exception) {
		ExceptionAlertDialog exceptionAlertDialog = new ExceptionAlertDialog(new Alert(AlertType.ERROR), exception);
		exceptionAlertDialog.showAndWait();
	}

	private void provideOpenFunctionnality(String conseiLocalName) {
		AppContext appContext = SpringFactory.getService(AppContext.ID);
		appContext.setConseiLocalToBeOpened(conseiLocalName);
		
		viewFactory.createStage("openconseillocal",new  Object[] {conseiLocalName}, Modality.APPLICATION_MODAL).show();
			
	
	}

}