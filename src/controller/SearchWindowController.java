package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.Recipe;
import windowviews.WindowViews;

/**
 * Controller of the searchWindow, acts as an intermediary between searchWindow
 * and model, defines what should happen on user interaction on searchWindow.
 * 
 * @author szt
 *
 */
public class SearchWindowController implements Initializable {

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
	 * searchWindow GUI items
	 */
	@FXML
	private TextField searchRecipeTextField;
	@FXML
	private Button searchRecipeButton;

	/**
	 * Constructor of the searchWindowController, connect the searchWindow to the
	 * database.
	 */
	public SearchWindowController() {
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
	 * Control the window switching into detailWindow according to the butten
	 * clicked.
	 * 
	 * @param event, user's clicking on five category buttons
	 */
	public void actionResponseToSearchWindow(ActionEvent event) {
		Recipe searchedRecipe = null;
		if (searchRecipeTextField.getText() == "")
			windowsView.alertWindow();
		else {
			searchedRecipe = databaseController.searchRecipe(searchRecipeTextField.getText());
		}
		if (searchedRecipe == null) {
			windowsView.showSearchedResultDialog();
			return;
		}
		windowsView.setDetailWindow(searchedRecipe);
	}

	/**
	 * Bind the searchWindowController to the windowViews.
	 * 
	 * @param windowViews, windowViews
	 */
	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
