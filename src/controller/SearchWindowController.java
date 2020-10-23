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
 * WindowController class, use for control the switching of windows
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

	public SearchWindowController() {
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

	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
