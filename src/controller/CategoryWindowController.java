package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import model.Recipe;
import windowviews.WindowViews;

/**
 * CategoryWindowController class, used for control the action from category page
 * 
 * @author szt
 *
 */
public class CategoryWindowController implements Initializable {

	private WindowViews windowsView;
	private DatabaseController databaseController;
	/**
	 * store items appears in the recipe list table
	 */
	private ObservableList<Recipe> recipesList = FXCollections.observableArrayList();

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
	 * categoryWindow GUI Items
	 */
	@FXML
	private TableView<Recipe> recipeListTable_in_categoty = new TableView<>();
	@FXML
	private TableColumn<Recipe, String> recipeNameColumn_in_category = new TableColumn<>();
	@FXML
	private TableColumn<Recipe, ImageView> recipePictureColumn_in_category = new TableColumn<Recipe, ImageView>();

	public CategoryWindowController() {
		databaseController = new DatabaseController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// categoryWindow initialize
		recipeNameColumn_in_category.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
		recipePictureColumn_in_category.setCellValueFactory(new PropertyValueFactory<Recipe, ImageView>("recipeImage"));// response
																														// to
																														// getRecipeImage
																														// method
		recipeListTable_in_categoty.setItems(recipesList);
		//add see detail button to the table, so that user can see detail of the recipe
		this.addSeeMoreButtonToTable();
	}


	/**
	 * Realization of add see detail button and add action listener to it
	 */
	private void addSeeMoreButtonToTable() {
		TableColumn<Recipe, String> colBtn = new TableColumn<>();

		Callback<TableColumn<Recipe, String>, TableCell<Recipe, String>> cellFactory = new Callback<TableColumn<Recipe, String>, TableCell<Recipe, String>>() {
			@Override
			public TableCell<Recipe, String> call(final TableColumn<Recipe, String> param) {
				final TableCell<Recipe, String> cell = new TableCell<Recipe, String>() {
					private final Button btn = new Button("detail");
					{
						btn.setOnAction((ActionEvent event) -> {
							Recipe data = getTableView().getItems().get(getIndex());
							windowsView.setDetailWindow(data);
						});
					}

					@Override
					public void updateItem(String item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		colBtn.setCellFactory(cellFactory);
		recipeListTable_in_categoty.getColumns().add(colBtn);
	}

	/**
	 * show main window
	 */
	public void showMainWindow() {
		windowsView.setMainWindow();
	}

	/**
	 * show search window
	 */
	public void showSearchWindow() {
		windowsView.setSearchWindow();
	}

	/**
	 * show create window
	 */
	public void showCreateWindow() {
		windowsView.setCreateWindow();
	}

	/**
	 * set recipe list data in this page
	 * @param stored_recipes recipe list of given category
	 */
	public void setDataAtCategoryWindow(ArrayList<Recipe> stored_recipes) {
		recipesList.addAll(stored_recipes);
		recipeListTable_in_categoty.refresh();
	}
	
	/**
	 * action response to switching category
	 * @param event
	 */
	public void actionResponseToCategoryWindow(ActionEvent event) {
		Button button = (Button) event.getTarget();
		String searchedCategory = button.getText();
		ArrayList<Recipe> stored_recipes = databaseController.searchRecipeList(searchedCategory);
		windowsView.setCategoryWindow(stored_recipes);
	}


	/**
	 * set pointer to view class
	 * @param windowViews aimed view class
	 */
	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
