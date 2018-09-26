package org.fcpe.fantinlatour.app.controller.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.fcpe.fantinlatour.app.controller.MenuController;
import org.fcpe.fantinlatour.app.controller.utils.INodeVisitor;
import org.fcpe.fantinlatour.app.controller.utils.NodeAdapter;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManagerListener;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuValidator implements ConseilLocalEtablissementManagerListener, INodeVisitor {

	static final String MENU_CONSEILLOCAL_OPEN = "MENU_CONSEILLOCAL_OPEN";
	static final String MENUITEM_CONSEILLOCAL_EXPORT = "MENUITEM_CONSEILLOCAL_EXPORT";
	static final String MENUITEM_CONSEILLOCAL_RENAME = "MENUITEM_CONSEILLOCAL_RENAME";
	static final String MENUITEM_CONSEILLOCAL_DELETE = "MENUITEM_CONSEILLOCAL_DELETE";
	static final String MENUITEM_YEAR_CURRENT_IMPORT = "MENUITEM_YEAR_CURRENT_IMPORT";
	static final String MENUITEM_MAILING_LIST_SETTINGS = "MENUITEM_MAILING_LIST_SETTINGS";
	static final String MENUITEM_FINANCIAL_IMPORT = "MENUITEM_FINANCIAL_IMPORT";
	static final String MENUITEM_FINANCIAL_EXPORT = "MENUITEM_FINANCIAL_EXPORT";
	static final String MENUITEM_CONSEILLOCAL_SET_ASDEFAULT = "MENUITEM_CONSEILLOCAL_SET_ASDEFAULT";
	static final String MENUITEM_MAIL_SETTINGS = "MENUITEM_MAIL_SETTINGS";

	private static final List<String> OPENED_CONSEIL_LOCAL_DEPENDANTS = new ArrayList<String>(
			Arrays.asList(MENUITEM_CONSEILLOCAL_EXPORT, MENUITEM_CONSEILLOCAL_RENAME, MENUITEM_CONSEILLOCAL_DELETE,
					MENUITEM_YEAR_CURRENT_IMPORT, MENUITEM_MAILING_LIST_SETTINGS, MENUITEM_FINANCIAL_IMPORT,
					MENUITEM_FINANCIAL_EXPORT,MENUITEM_MAIL_SETTINGS));

	private MenuBar menuBar;
	private ConseilLocalEtablissement selectedConseilLocalEtablissement;
	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	private MenuController menuController;

	public MenuValidator(MenuController menuController, MenuBar menuBar,
			ConseilLocalEtablissementManager conseilLocalEtablissementManager) {
		super();
		this.menuController = menuController;
		this.menuBar = menuBar;
		this.conseilLocalEtablissementManager = conseilLocalEtablissementManager;
	}

	@Override
	public void onSelected(ConseilLocalEtablissement conseilLocalEtablissement) {
		this.selectedConseilLocalEtablissement = conseilLocalEtablissement;
		NodeAdapter menuBarVisitor = NodeAdapter.adapt(menuBar);

		menuBarVisitor.accept(this);

	}

	@Override
	public boolean visit(NodeAdapter nodeAdapter) {

		String selectedConseilLocalEtablissementName = null;
		if (selectedConseilLocalEtablissement != null) {
			selectedConseilLocalEtablissementName = selectedConseilLocalEtablissement.getEtablissement().getNom();
		}
		String nodeID = nodeAdapter.idProperty().getValue();
		if (MENU_CONSEILLOCAL_OPEN.equals(nodeID)) {
			Menu menu = (Menu) nodeAdapter.getFXObject();
			try {
				menu.getItems().clear();
				List<String> existingConseilEtablissements = conseilLocalEtablissementManager
						.getExistingConseilEtablissements();

				for (String conseilLocalEtablissementName : existingConseilEtablissements) {

					MenuItem menuItem = new MenuItem(conseilLocalEtablissementName);
					menuItem.setUserData(conseilLocalEtablissementName);

					menuItem.setOnAction(createOpenHandler());
					if (selectedConseilLocalEtablissement != null) {
						menuItem.setDisable(
								conseilLocalEtablissementName.equals(selectedConseilLocalEtablissementName));
					}
					menu.getItems().add(menuItem);

				}
				menu.setDisable(existingConseilEtablissements.isEmpty());
			} catch (DataException e) {

			}

		} else if (MENUITEM_CONSEILLOCAL_SET_ASDEFAULT.equals(nodeID)) {
			MenuItem menu = (MenuItem) nodeAdapter.getFXObject();
			try {
				menu.setDisable(
						(selectedConseilLocalEtablissement == null || conseilLocalEtablissementManager.isDefault()));
			} catch (DataException e) {
				
			}
		} else if (OPENED_CONSEIL_LOCAL_DEPENDANTS.contains(nodeID)) {
			MenuItem menu = (MenuItem) nodeAdapter.getFXObject();
			menu.setDisable(selectedConseilLocalEtablissement == null);
		}

		return true;
	}

	private EventHandler<ActionEvent> createOpenHandler() {
		return new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				menuController.handleOpen(event);
			}
		};
	}

}
