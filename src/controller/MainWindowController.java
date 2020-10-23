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
 * WindowController class, use for control the switching of windows
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


	public MainWindowController() {
		databaseController = new DatabaseController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void showMainWindow(ActionEvent event) {
		windowsView.setMainWindow();
	}

	public void showSearchWindow() {
		windowsView.setSearchWindow();
	}

	public void showCreateWindow() {
		windowsView.setCreateWindow();
	}
	
	public void actionResponseToMainWindow(ActionEvent event) {
		Button button = (Button) event.getTarget();
		String searchedCategory = button.getText();
		ArrayList<Recipe> stored_recipes = databaseController.searchRecipeList(searchedCategory);
		windowsView.setCategoryWindow(stored_recipes);
	}

	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
