package org.fcpe.fantinlatour.app.controller.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.easymock.IMocksControl;
import org.fcpe.fantinlatour.app.controller.MenuController;
import org.fcpe.fantinlatour.app.controller.utils.JavaFXThreadingRule;
import org.fcpe.fantinlatour.app.controller.utils.NodeAdapter;
import org.fcpe.fantinlatour.dao.DataException;
import org.fcpe.fantinlatour.model.ConseilLocalEtablissement;
import org.fcpe.fantinlatour.model.Etablissement;
import org.fcpe.fantinlatour.model.controller.ConseilLocalEtablissementManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;

public class MenuValidatorTest {

	private EasyMockSupport support = new EasyMockSupport();

	private IMocksControl ctrl;
	
	private MenuController menuController;

	private ConseilLocalEtablissementManager conseilLocalEtablissementManager;

	private MenuValidator menuValidator;


	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	

	private MenuBar menuBar;

	private Menu menuOpen;

	private MenuItem menuItemConseilLocalExportMenuItem;
	@Before
	public void setup() throws InterruptedException {

		final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(() -> {
			new JFXPanel();
			latch.countDown();
		});

		
		latch.await();
		
		ctrl = support.createControl();

		Pair<? extends Node> root = createGraph();
		new NodeAdapter(root.node);
		menuController = ctrl.createMock(MenuController.class);
		conseilLocalEtablissementManager = ctrl.createMock(ConseilLocalEtablissementManager.class);
		menuValidator = new MenuValidator(menuController, menuBar, conseilLocalEtablissementManager);
	
	}
	
	@Test
	public void testMenuValidationWithNoConseilLocal() throws DataException {
		
		List<String> list = new ArrayList<String>();
		
		EasyMock.expect(conseilLocalEtablissementManager.getExistingConseilEtablissements()).andReturn(list);
		support.replayAll();
		
		menuValidator.onSelected(null);
		assertTrue(menuOpen.isDisable());
		assertTrue(menuItemConseilLocalExportMenuItem.isDisable());
		assertEquals(0,menuOpen.getItems().size());
		support.verifyAll();
		
	}
	@Test
	public void testMenuValidationAfterEmptySelectionButWithOneConseilLocal() throws DataException {
		
		List<String> list = new ArrayList<String>();
		list.add("ConseilLocal1");
		EasyMock.expect(conseilLocalEtablissementManager.getExistingConseilEtablissements()).andReturn(list);
		support.replayAll();
		menuValidator.onSelected(null);
		assertFalse(menuOpen.isDisable());
		assertEquals("ConseilLocal1",menuOpen.getItems().get(0).getUserData());
		
		assertFalse(menuOpen.isDisable());
		support.verifyAll();
		
	}
	
	@Test
	public void testMenuValidationAfterSelectedConseil() throws DataException {
		
		List<String> list = new ArrayList<String>();
		list.add("ConseilLocal1");
		list.add("ConseilLocal2");
		EasyMock.expect(conseilLocalEtablissementManager.getExistingConseilEtablissements()).andReturn(list);
		
		ConseilLocalEtablissement conseilLocalEtablissement = ctrl.createMock(ConseilLocalEtablissement.class);
		Etablissement etablissement = ctrl.createMock(Etablissement.class);
		
		EasyMock.expect(conseilLocalEtablissement.getEtablissement()).andReturn(etablissement);
		EasyMock.expect(etablissement.getNom()).andReturn("ConseilLocal2");
		support.replayAll();
		
		
		menuValidator.onSelected(conseilLocalEtablissement);
		assertFalse(menuOpen.isDisable());
		assertEquals("ConseilLocal1",menuOpen.getItems().get(0).getUserData());
		assertFalse(menuOpen.getItems().get(0).isDisable());
		assertEquals("ConseilLocal2",menuOpen.getItems().get(1).getUserData());
		assertTrue(menuOpen.getItems().get(1).isDisable());
		assertFalse(menuItemConseilLocalExportMenuItem.isDisable());
		support.verifyAll();
		
	}

	private Pair<? extends Node> createGraph() {
		
		BorderPane root = new BorderPane();
		TestModelElement<BorderPane> rootElement = new TestModelElement<>(root);
		
		Pair<? extends Node> menuBar = createMenuBar();
		rootElement.children.add(menuBar.element);
		root.setTop(menuBar.node);
		
		return new Pair<BorderPane>(root, rootElement);
	}

	private Pair<? extends Node> createMenuBar() {
		menuBar = new MenuBar();
		TestModelElement<MenuBar> menuBarElement = new TestModelElement<>(menuBar);
		
		menuOpen = new Menu("Menu 01");
		menuOpen.setId(MenuValidator.MENU_CONSEILLOCAL_OPEN);
		TestModelElement<Menu> menuElement01 = new TestModelElement<>(menuOpen);
		menuBar.getMenus().add(menuOpen);
		menuBarElement.children.add(menuElement01);
		populateMenu(menuOpen, menuElement01);
		
		Menu menu02 = new Menu("Menu 01");
		TestModelElement<Menu> menuElement02 = new TestModelElement<>(menu02);
		menuBar.getMenus().add(menu02);
		menuBarElement.children.add(menuElement02);
		populateMenu(menu02, menuElement02);
		
		return new Pair<MenuBar>(menuBar, menuBarElement);
	}
	
	private void populateMenu(Menu menu, TestModelElement<Menu> menuElement) {
		Menu subMenu = new Menu("SubMenu");
		TestModelElement<Menu> subMenuElement = new TestModelElement<>(subMenu);
		menu.getItems().add(subMenu);
		menuElement.children.add(subMenuElement);
		populateSubMenu(subMenu, subMenuElement);
		
		 menuItemConseilLocalExportMenuItem = new MenuItem("MenuItem 01"); 
		menuItemConseilLocalExportMenuItem.setId(MenuValidator.MENUITEM_CONSEILLOCAL_EXPORT);
		
		menu.getItems().add(menuItemConseilLocalExportMenuItem);
		menuElement.children.add(new TestModelElement<>(menuItemConseilLocalExportMenuItem));
		
		MenuItem item02 = new MenuItem("MenuItem 02"); 
		menu.getItems().add(item02);
		menuElement.children.add(new TestModelElement<>(item02));
		
		SeparatorMenuItem separator = new SeparatorMenuItem();
		menu.getItems().add(separator);
		menuElement.children.add(new TestModelElement<>(separator));
		
		MenuItem item03 = new MenuItem("MenuItem 03"); 
		menu.getItems().add(item03);
		menuElement.children.add(new TestModelElement<>(item03));
	}

	private void populateSubMenu(Menu menu, TestModelElement<Menu> menuElement) {
		MenuItem item01 = new MenuItem("SubMenuItem 01"); 
		TestModelElement<MenuItem> item01Element = new TestModelElement<>(item01);
		menu.getItems().add(item01);
		menuElement.children.add(item01Element);
		
		MenuItem item02 = new MenuItem("SubMenuItem 02"); 
		TestModelElement<MenuItem> item02Element = new TestModelElement<>(item02);
		menu.getItems().add(item02);
		menuElement.children.add(item02Element);
	}
	
}

class Pair<T> {
	T node;
	TestModelElement<T> element;
	
	public Pair(T node, TestModelElement<T> element) {
		this.node = node;
		this.element = element;
	}
}

class TestModelElement<T> {
	T fxObject;
	List<TestModelElement<?>> children = new ArrayList<>();
	
	public TestModelElement(T fxObject) {
		this.fxObject = fxObject;
	}
}