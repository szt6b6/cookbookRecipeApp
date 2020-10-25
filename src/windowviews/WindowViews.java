package windowviews;

import java.util.ArrayList;
import java.util.Optional;

import controller.CategoryWindowController;
import controller.CreateWindowController;
import controller.DetailWindowController;
import controller.MainWindowController;
import controller.SearchWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Recipe;

/**
 * WindowViews class, use corresponding fxml page to show relative window
 * 
 * @author szt s
 */

public class WindowViews extends Application {
	/**
	 * one fxml page for one window controller
	 */
	private Stage primaryStage;
	private MainWindowController mainWindowController;
	private CategoryWindowController categoryWindowController;
	private CreateWindowController createWindowController;
	private DetailWindowController detailWindowController;
	private SearchWindowController searchWindowController;

	/**
	 * initialize the window controllers and set main page
	 */
	@Override
	public void start(Stage primaryStage) {
		this.mainWindowController = new MainWindowController();
		this.categoryWindowController = new CategoryWindowController();
		this.createWindowController = new CreateWindowController();
		this.detailWindowController = new DetailWindowController();
		this.searchWindowController = new SearchWindowController();
		this.primaryStage = primaryStage;
		primaryStage.setTitle("cookbook");
		setMainWindow();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	/**
	 * use mainWindow.fxml file to set main window on primaryStage and show
	 */
	public void setMainWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			mainWindowController = loader.getController();
			mainWindowController.setView(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * use searchWindow.fxml file to set search window on primaryStage and show
	 */
	public void setSearchWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("searchWindow.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			searchWindowController = loader.getController();
			searchWindowController.setView(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * use detailWindow.fxml file to set detail window on primaryStage and show
	 * 
	 * @param searchedRecipe recipe entity whose data will be set in detail window
	 */
	public void setDetailWindow(Recipe searchedRecipe) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("detailWindow.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			detailWindowController = loader.getController();
			detailWindowController.setView(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		detailWindowController.setDataAtDetailWindow(searchedRecipe);
	}

	/**
	 * use categoryWindow.fxml file to set category window on primaryStage and show
	 * 
	 * @param stored_recipes arrayList of recipe entities, included data will be
	 *                       used to set data of category page
	 */
	public void setCategoryWindow(ArrayList<Recipe> stored_recipes) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("categoryWindow.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			categoryWindowController = loader.getController();
			categoryWindowController.setView(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		categoryWindowController.setDataAtCategoryWindow(stored_recipes);
	}

	/**
	 * use createWindow.fxml file to set create window on primaryStage and show
	 */
	public void setCreateWindow() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("createWindow.fxml"));
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			createWindowController = loader.getController();
			createWindowController.setView(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * pop up alert dialog to remind user's wrong input
	 */
	public void alertWindow() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText("Please fill the content correctly");
		alert.showAndWait();
	}

	/**
	 * pop up alert dialog to remind user to confirm their creating recipe action
	 * 
	 * @return
	 */
	public Optional<ButtonType> showCreateConfirmationDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure to create the recipe?");
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	/**
	 * inform user after successfully inserting recipe into database
	 */
	public void showCreateSuccessDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Create");
		alert.setHeaderText(null);
		alert.setContentText("Your recipe has been added successfully!");
		alert.showAndWait();
	}

	/**
	 * inform user while getting error in inserting recipe into database
	 */
	public void showCreateErrorDialog() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText("Insert recipe into database error. Duplicated recipe name is not allowed");
		alert.showAndWait();
	}

	/**
	 * inform user that the searched recipe does not exist in database
	 */
	public void showSearchedResultDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Search");
		alert.setHeaderText(null);
		alert.setContentText("The recipe is not in database!");
		alert.showAndWait();
	}

	/**
	 * pop up alert dialog to remind user to confirm their deleting recipe action
	 *
	 * @return
	 */
	public Optional<ButtonType> showDeleteConfirmationDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure to delete the recipe?");
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	/**
	 * inform user while getting error in deleting recipe into database
	 * 
	 * @param recipeNameToDelete display the recipe name
	 */
	public void showDeleteFailedDialog(String recipeNameToDelete) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("deleteFailed");
		alert.setHeaderText(null);
		alert.setContentText(recipeNameToDelete + " is not in database.");
		alert.showAndWait();
	}

	/**
	 * inform user after successfully deleting recipe in the database
	 * 
	 * @param recipeNameToDelete display deleted recipe name
	 */
	public void showDeleteSuccessfulDialog(String recipeNameToDelete) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("deleteSuccessful");
		alert.setHeaderText(null);
		alert.setContentText(recipeNameToDelete + " has been delete from the database.");
		alert.showAndWait();
	}

	/**
	 * pop up alert dialog to remind user to confirm their updating recipe action
	 * 
	 * @return return user's click action
	 */
	public Optional<ButtonType> showUpdateConfirmationDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure to update the recipe?");
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	/**
	 * inform user after successfully updating recipe
	 */
	public void showUpdateSuccessDialog() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("updateSuccessful");
		alert.setHeaderText(null);
		alert.setContentText("update recipe successfully!");
		alert.showAndWait();
	}

	/**
	 * inform user while getting error in updating recipe
	 */
	public void showUpdateErrorDialog() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("updateFailed");
		alert.setHeaderText(null);
		alert.setContentText("update failed.");
		alert.showAndWait();
	}

	/**
	 * pop up alert dialog to remind user to confirm their cancel create recipe behavior
	 * 
	 * @return return user's click action
	 */
	public Optional<ButtonType> showClearConfirmationDialog() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure to clear the information?");
		Optional<ButtonType> result = alert.showAndWait();
		return result;
	}

	/**
	 * main method, program starts here
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
