package windowviews;

<<<<<<< Updated upstream
import java.net.URL;
=======
import java.util.ArrayList;
>>>>>>> Stashed changes

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
import javafx.stage.Stage;
import model.Recipe;

/**
 * WindowViews class, use respond fxml page to show relative window
 * 
 * @author szt
 *s
 */

public class WindowViews extends Application {
	private Stage primaryStage;
	private MainWindowController mainWindowController;
	private CategoryWindowController categoryWindowController;
	private CreateWindowController createWindowController;
	private DetailWindowController detailWindowController;
	private SearchWindowController searchWindowController;

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


<<<<<<< Updated upstream
	public void setCategoryWindow() {
		setAndShowWindows("categoryWindow.fxml");
=======
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
		categoryWindowController.setDataAtCateGoryWindow(stored_recipes);
>>>>>>> Stashed changes
	}

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
