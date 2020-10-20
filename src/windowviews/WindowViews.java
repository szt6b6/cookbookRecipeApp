package windowviews;

import java.net.URL;
import java.util.ArrayList;

import controller.WindowsController;
import entity.Recipe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * WindowViews class, use respond fxml page to show relative window
 * 
 * @author szt
 *s
 */

public class WindowViews extends Application {
	private Stage primaryStage;
	private WindowsController windowController;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("cookbook");
		setMainWindow();
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public void setMainWindow() {
		setAndShowWindows("mainWindow.fxml");
	}

	public void setSearchWindow() {
		setAndShowWindows("searchWindow.fxml");
	}

	public void setDetailWindow(Recipe searchedRecipe) {
		setAndShowWindows("detailWindow.fxml");
		windowController.setDataAtDetailWindow(searchedRecipe);
	}

	public void setRecipesPane() {
		setAndShowWindows("recipeWindow.fxml");
	}

	public void setCategoryWindow(ArrayList<Recipe> stored_recipes) {
		setAndShowWindows("categoryWindow.fxml");
		windowController.setDataAtCateGoryWindow(stored_recipes);
	}

	public void setCreateWindow() {
		setAndShowWindows("createWindow.fxml");
	}

	public void setAndShowWindows(String window) {
		try {
			URL url = getClass().getResource(window);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(url);
			Parent root = loader.load();
			primaryStage.setScene(new Scene(root));
			windowController = loader.getController();
			windowController.setView(this);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void alertWindow() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning");
		alert.setHeaderText(null);
		alert.setContentText("Please fill the content correctly");
		alert.showAndWait();
	}
	
	public void showCreateSuccessDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Create");
		alert.setHeaderText(null);
		alert.setContentText("Your recipe has been added successfully!");
		alert.showAndWait();
	}
	
	public void showCreateErrorDialog() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText("Insert recipe into database error. Duplicated recipe name is not allowed");
		alert.showAndWait();
	}
	
	public void showSearchedResultDialog() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Search");
		alert.setHeaderText(null);
		alert.setContentText("The recipe is not in database!");
		alert.showAndWait();
	}

	public void showDeleteFailedDialog(String recipeNameToDelete) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("deleteFailed");
		alert.setHeaderText(null);
		alert.setContentText(recipeNameToDelete + " is not in database.");
		alert.showAndWait();
	}

	public void showDeleteSuccessfulDialog(String recipeNameToDelete) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("deleteSuccessful");
		alert.setHeaderText(null);
		alert.setContentText(recipeNameToDelete + " has been delete from the database.");
		alert.showAndWait();
	}
	
	public void showUpdataSuccessDialog() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("updateSuccessful");
		alert.setHeaderText(null);
		alert.setContentText("update recipe successfully!");
		alert.showAndWait();
	}

	public void showUpdateErrorDialog() {
		// TODO Auto-generated method stub
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("updateFailed");
		alert.setHeaderText(null);
		alert.setContentText("update failed.");
		alert.showAndWait();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
