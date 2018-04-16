package org.fcpe.fantinlatour.app.controller.validator;

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

	private static final String MENU_CONSEILLOCAL_OPEN = "MENU_CONSEILLOCAL_OPEN";
	private MenuBar menuBar;
	private ConseilLocalEtablissement selectedConseilLocalEtablissement;
	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;
	private MenuController menuController;

	public MenuValidator(MenuController menuController, MenuBar menuBar, ConseilLocalEtablissementManager conseilLocalEtablissementManager) {
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
		
		
		if (MENU_CONSEILLOCAL_OPEN.equals(nodeAdapter.idProperty().getValue())) {
			Menu menu = (Menu) nodeAdapter.getFXObject();
			try {
				List<String> existingConseilEtablissements = conseilLocalEtablissementManager.getExistingConseilEtablissements();
				for(String conseilLocalEtablissementName : existingConseilEtablissements) {
					
					MenuItem menuItem = new MenuItem(conseilLocalEtablissementName);
					menuItem.setUserData(conseilLocalEtablissementName);
					
					
					menuItem.setOnAction(createOpenHandler());
					menuItem.setDisable(conseilLocalEtablissementName.equals(selectedConseilLocalEtablissement.getEtablissement().getNom()));
					menu.getItems().add(menuItem);
				
				}
				//menu.setDisable(existingConseilEtablissements.isEmpty());
			} catch (DataException e) {

			}
			
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
