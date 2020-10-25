package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import model.Recipe;
import windowviews.WindowViews;

/**
 * Controller of the mainWindow, acts as an intermediary between mainWindow and
 * model, defines what should happen on user interaction on mainWindow.
 * 
 * @author szt
 *
 */
public class MainWindowController implements Initializable {

	private WindowViews windowsView;
	private DatabaseController databaseController;

	/**
	 * all window GUI items
	 */
	@FXML
	private MenuItem home;
	@FXML
	private MenuItem create;
	@FXML
	private MenuItem search;

	/**
	 * Constructor of the MainWindowController, connect the mainWindow to the
	 * database.
	 */
	public MainWindowController() {
		databaseController = new DatabaseController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Show mainWindow.
	 * 
	 * @param event, user's interaction with GUI
	 */
	public void showMainWindow(ActionEvent event) {
		windowsView.setMainWindow();
	}

	/**
	 * Show searchWindow.
	 */
	public void showSearchWindow() {
		windowsView.setSearchWindow();
	}

	/**
	 * Show createWindow.
	 */
	public void showCreateWindow() {
		windowsView.setCreateWindow();
	}

	/**
	 * Control the window switching into categoryWindow according to the button
	 * clicked.
	 * 
	 * @param event, user's clicking on five category buttons
	 */
	public void actionResponseToMainWindow(ActionEvent event) {
		Button button = (Button) event.getTarget();
		String searchedCategory = button.getText();
		ArrayList<Recipe> stored_recipes = databaseController.searchRecipeList(searchedCategory);
		windowsView.setCategoryWindow(stored_recipes);
	}

	/**
	 * Bind the mainWindowController to the windowViews.
	 * 
	 * @param windowViews, windowViews
	 */
	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
