package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import entity.Ingredient;
import entity.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import model.Model;
import windowviews.WindowViews;

/**
 * WindowController class, use for control the switching of windows
 * 
 * @author szt
 *
 */
public class WindowsController implements Initializable {

	private WindowViews windowsView;
	private Model model;
	private DatabaseController databaseController;
	private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
	private ObservableList<Ingredient> ingredients_in_detail = FXCollections.observableArrayList();
	private ObservableList<Recipe> recipesList = FXCollections.observableArrayList();
	private final ArrayList<String> categires = new ArrayList<>(
			FXCollections.observableArrayList("stired", "boiled", "fried", "stewed", "baked"));
	private String recipeNameToBeUpdate = ""; // used for looking for recipe and update it
	private ArrayList<Ingredient> neededToDelIngredientInDetailWindow = new ArrayList<>(); // used to store the deleted
																							// ingredient by clinking
																							// del in detailWindow

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
	 * createWindow GUI items
	 */
	@FXML
	private TextField recipeName;
	@FXML
	private TextField prepTime;
	@FXML
	private TextField cookTime;
	@FXML
	private TextField category;
	@FXML
	private Button picChooser;
	@FXML
	private ImageView picture;
	@FXML
	private TextArea instruction;
	@FXML
	private Button createConfirm;
	@FXML
	private Button createCancle;
	@FXML
	private TextField nameOfIngredient;
	@FXML
	private TextField descriptionOfingredient;
	@FXML
	private TextField amountOfIngredient;
	@FXML
	private TextField unitOfIngredient;
	@FXML
	private Button addIngredientButton;
	@FXML
	private TableView<Ingredient> ingredientsTable = new TableView<Ingredient>();
	@FXML
	private TableColumn<Ingredient, String> name = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> description = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> amount = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> unit = new TableColumn<Ingredient, String>();

	/**
	 * detailWindow GUI items
	 */
	@FXML
	private TextField serveAmount;
	@FXML
	private Button setServeAmountButton;
	@FXML
	private TextField recipeName_in_detail;
	@FXML
	private TextField prepTime_in_detail;
	@FXML
	private TextField cookTime_in_detail;
	@FXML
	private TextField category_in_detail;
	@FXML
	private Button picChooser_in_detail;
	@FXML
	private ImageView picture_in_detail;
	@FXML
	private TextArea instruction_in_detail;
	@FXML
	private Button updateConfirm;
	@FXML
	private Button deleteConfirm;
	@FXML
	private TextField nameOfIngredient_in_detail;
	@FXML
	private TextField descriptionOfingredient_in_detail;
	@FXML
	private TextField amountOfIngredient_in_detail;
	@FXML
	private TextField unitOfIngredient_in_detail;
	@FXML
	private Button addIngredientButton_in_detail;
	@FXML
	private TableView<Ingredient> ingredientsTable_in_detail = new TableView<Ingredient>();
	@FXML
	private TableColumn<Ingredient, String> name_in_detail = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> description_in_detail = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> amount_in_detail = new TableColumn<Ingredient, String>();
	@FXML
	private TableColumn<Ingredient, String> unit_in_detail = new TableColumn<Ingredient, String>();

	/**
	 * searchWindow GUI items
	 */
	@FXML
	private TextField searchRecipeTextField;
	@FXML
	private Button searchRecipeButton;

	/**
	 * cateGoryWindow GUI Items
	 */
	@FXML
	private TableView<Recipe> recipeListTable_in_categoty = new TableView<>();
	@FXML
	private TableColumn<Recipe, String> recipeNameColumn_in_category = new TableColumn<>();
	@FXML
	private TableColumn<Recipe, ImageView> recipePictureColumn_in_category = new TableColumn<Recipe, ImageView>();

	public WindowsController() {
		databaseController = new DatabaseController();
		model = new Model();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// createWindow initialize
		name.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("nameIng"));
		description.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("descriptionIng"));
		amount.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("totalAmountIng"));
		unit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unitIng"));
		ingredientsTable.setItems(ingredients);
		this.addDelButtonToTable("createWindow");

		// detailWindow initialize
		name_in_detail.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("nameIng"));
		description_in_detail.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("descriptionIng"));
		amount_in_detail.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("totalAmountIng"));
		unit_in_detail.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unitIng"));
		ingredientsTable_in_detail.setItems(ingredients_in_detail);
		this.addDelButtonToTable("detailWindow");

		// categoryWindow initialize
		recipeNameColumn_in_category.setCellValueFactory(new PropertyValueFactory<Recipe, String>("name"));
		recipePictureColumn_in_category.setCellValueFactory(new PropertyValueFactory<Recipe, ImageView>("recipeImage"));// response
																														// to
																														// getRecipeImage
																														// method
		recipeListTable_in_categoty.setItems(recipesList);
		this.addSeeMoreButtonToTable();
	}

	private void addDelButtonToTable(String fromWhichWindow) {
		TableColumn<Ingredient, String> colBtn = new TableColumn<>();

		Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> cellFactory = new Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>>() {
			@Override
			public TableCell<Ingredient, String> call(final TableColumn<Ingredient, String> param) {
				final TableCell<Ingredient, String> cell = new TableCell<Ingredient, String>() {
					private final Button btn = new Button("del");
					{
						btn.setOnAction((ActionEvent event) -> {
							Ingredient data = getTableView().getItems().get(getIndex());
							if (fromWhichWindow == "detailWindow") {
								neededToDelIngredientInDetailWindow.add(data);// used to store deleted ingredients by
																				// clicking del button to update
								ingredients_in_detail.remove(data);
								ingredientsTable_in_detail.refresh();
							} else {
								ingredients.remove(data);
								ingredientsTable.refresh();
							}

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
		if (fromWhichWindow == "detailWindow") {
			ingredientsTable_in_detail.getColumns().add(colBtn);
		} else {
			ingredientsTable.getColumns().add(colBtn);
		}
	}

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

	public void showMainWindow(ActionEvent event) {
		windowsView.setMainWindow();
	}

	public void actionResponseToMainWindow(ActionEvent event) {
		Button button = (Button) event.getTarget();
		String searchedCategory = button.getText();
		ArrayList<Recipe> stored_recipes = databaseController.searchRecipeList(searchedCategory);
		windowsView.setCategoryWindow(stored_recipes);
	};;

	public void showSearchWindow() {
		windowsView.setSearchWindow();
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

	public void setDataAtCateGoryWindow(ArrayList<Recipe> stored_recipes) {
		recipesList.addAll(stored_recipes);
		recipeListTable_in_categoty.refresh();
	}

	public void setDataAtDetailWindow(Recipe searchedRecipe) {
		if (searchedRecipe == null) {
			return;
		}
		// set transform recipe's data to detail window items
		String searchedRecipeName = searchedRecipe.getName();
		recipeNameToBeUpdate = searchedRecipeName;
		String searchedRecipePrepTime = searchedRecipe.getPrepTime();
		String searchedRecipeCookTime = searchedRecipe.getCookTime();
		String searchedRecipeInstruction = searchedRecipe.getInstruction();
		String searchedRecipeCategory = searchedRecipe.getCategory();
		Blob searchedRecipePic = searchedRecipe.getPic();
		ArrayList<Ingredient> searchedRecipeIngredients = searchedRecipe.getIngredients();

		recipeName_in_detail.setText(searchedRecipeName);
		prepTime_in_detail.setText(searchedRecipePrepTime);
		cookTime_in_detail.setText(searchedRecipeCookTime);
		category_in_detail.setText(searchedRecipeCategory);
		instruction_in_detail.setText(searchedRecipeInstruction);
		try {
			picture_in_detail.setImage(new Image(searchedRecipePic.getBinaryStream()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ingredients_in_detail.addAll(searchedRecipeIngredients);
	}

	// update and delete action at this function
	public void actionResponseToDetailWindow(ActionEvent event) {
		Button pressedButton = (Button) event.getTarget();
		if (pressedButton == picChooser_in_detail) {
			FileChooser pictureChooser = new FileChooser();
			pictureChooser.setTitle("choose recipe's picture");
			pictureChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
			try {
				File pic = pictureChooser.showOpenDialog(windowsView.getPrimaryStage());
				Image image = new Image("file:" + pic.getAbsolutePath());
				picture_in_detail.setImage(image);
			} catch (NullPointerException e) {
				// cancle select picture锛� do nothing
			}

		}

		if (pressedButton == addIngredientButton_in_detail) {
			String stored_ingredientName = nameOfIngredient_in_detail.getText();
			String stored_description = descriptionOfingredient_in_detail.getText();
			String stored_amount = amountOfIngredient_in_detail.getText();
			String stored_unit = unitOfIngredient_in_detail.getText();
			int stored_serveAmount = Integer.valueOf(serveAmount.getText());
			if (stored_amount.length() != 0 && stored_description.length() != 0 && stored_unit.length() != 0
					&& stored_ingredientName.length() != 0 && isInteger(stored_amount) && stored_serveAmount > 0) {
				ingredients_in_detail.add(new Ingredient(stored_ingredientName, stored_description, stored_amount,
						stored_unit, stored_serveAmount));
			} else {
				windowsView.alertWindow();
			}
		}

		if (pressedButton == updateConfirm) {
			// delete the ingredients deleted first from database
			String stored_recipeName = recipeName_in_detail.getText();
			String stored_prepareTime = prepTime_in_detail.getText();
			String stored_cookTime = cookTime_in_detail.getText();
			String stored_category = category_in_detail.getText();
			String stored_instruction = instruction_in_detail.getText();
			if (stored_category == "" || stored_cookTime == "" || stored_instruction == "" || stored_prepareTime == ""
					|| stored_recipeName == "" || !isInteger(stored_cookTime) || !isInteger(stored_prepareTime)
					|| !categires.contains(stored_category)) {
				windowsView.alertWindow();
				return;
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage bImage = SwingFXUtils.fromFXImage(picture_in_detail.getImage(), null);
			Recipe stored_recipe;
			try {
				ImageIO.write(bImage, "png", baos);
				Blob stored_picture = databaseController.getConnection().createBlob();
				stored_picture.setBytes(1, baos.toByteArray());
				stored_recipe = new Recipe(stored_recipeName, stored_prepareTime, stored_cookTime, stored_picture,
						stored_instruction, stored_category);
				stored_recipe.setIngredients(new ArrayList<Ingredient>(ingredientsTable_in_detail.getItems()));
				if (databaseController.updateRecipe(stored_recipe, recipeNameToBeUpdate,
						neededToDelIngredientInDetailWindow)) {
					ingredients_in_detail.clear();
					windowsView.showUpdataSuccessDialog();
				} else {
					windowsView.showUpdateErrorDialog();
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}
			neededToDelIngredientInDetailWindow.clear();
		}

		if (pressedButton == deleteConfirm) {
			String recipeNameToDelete = recipeName_in_detail.getText();
			if (databaseController.deleteRecipe(recipeNameToDelete)) {
				neededToDelIngredientInDetailWindow.clear();
				windowsView.showDeleteSuccessfulDialog(recipeNameToDelete);
				windowsView.setDetailWindow(null);
			}

			else
				windowsView.showDeleteFailedDialog(recipeNameToDelete);
		}

		if (pressedButton == setServeAmountButton) {
			String stored_ServeAmount = serveAmount.getText();
			if (isInteger(stored_ServeAmount) && Integer.valueOf(stored_ServeAmount) > 0) {
				for (Ingredient ingredient : ingredients_in_detail) {
					ingredient.setServerAmount(Integer.valueOf(stored_ServeAmount));
					ingredient.setTotalAmountIng(
							String.valueOf(Integer.valueOf(ingredient.getAmountIng()) * ingredient.getServerAmount()));
				}
				ingredientsTable_in_detail.refresh();
			} else {
				windowsView.alertWindow();
			}
		}
	}

	public void showCreateWindow() {
		windowsView.setCreateWindow();
	}

	public void actionResponseToCreateWindow(ActionEvent event) {
		Button pressedButton = (Button) event.getTarget();
		if (pressedButton == picChooser) {
			FileChooser pictureChooser = new FileChooser();
			pictureChooser.setTitle("choose recipe's picture");
//			pictureChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", "*.jpg"),
//					new FileChooser.ExtensionFilter("BMP", "*.bmp"), new FileChooser.ExtensionFilter("PNG", "*.png"));
			pictureChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"));
			try {
				File pic = pictureChooser.showOpenDialog(windowsView.getPrimaryStage());
				Image image = new Image("file:" + pic.getAbsolutePath());
				picture.setImage(image);
			} catch (NullPointerException e) {
				// cancle select picture锛� do nothing
			}

		}

		if (pressedButton == addIngredientButton) {
			String stored_ingredientName = nameOfIngredient.getText();
			String stored_description = descriptionOfingredient.getText();
			String stored_amount = amountOfIngredient.getText();
			String stored_unit = unitOfIngredient.getText();
			if (stored_amount.length() != 0 && stored_description.length() != 0 && stored_unit.length() != 0
					&& stored_ingredientName.length() != 0 && isInteger(stored_amount)) {
				ingredients
						.add(new Ingredient(stored_ingredientName, stored_description, stored_amount, stored_unit, 1));
			} else {
				windowsView.alertWindow();
			}
		}

		if (pressedButton == createConfirm) {
			String stored_recipeName = recipeName.getText();
			String stored_prepareTime = prepTime.getText();
			String stored_cookTime = cookTime.getText();
			String stored_category = category.getText();
			String stored_instruction = instruction.getText();
			if (stored_category == "" || stored_cookTime == "" || stored_instruction == "" || stored_prepareTime == ""
					|| stored_recipeName == "" || !isInteger(stored_cookTime) || !isInteger(stored_prepareTime)
					|| !categires.contains(stored_category)) {
				ingredients.clear();
				windowsView.alertWindow();
				return;
			}
//			Image picture = new Image(recipeResult.getBlob("picture").getBinaryStream()); //灏咮lob鍥剧墖杞寲涓簀avafx Image
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedImage bImage = SwingFXUtils.fromFXImage(picture.getImage(), null);
			Recipe stored_recipe;
			try {
				ImageIO.write(bImage, "png", baos);
				Blob stored_picture = databaseController.getConnection().createBlob();
				stored_picture.setBytes(1, baos.toByteArray());
				stored_recipe = new Recipe(stored_recipeName, stored_prepareTime, stored_cookTime, stored_picture,
						stored_instruction, stored_category);
				stored_recipe.setIngredients(new ArrayList<Ingredient>(ingredientsTable.getItems()));
				ingredients.removeAll(ingredients);
				if (databaseController.createRecipe(stored_recipe)) {
					ingredients.clear();
					windowsView.showCreateSuccessDialog();
				} else {
					windowsView.showCreateErrorDialog();
				}
			} catch (IOException | SQLException e) {
				e.printStackTrace();
			}

		}

		if (pressedButton == createCancle) {
			try {
				ingredients.clear();
				this.showCreateWindow();
			} catch (NullPointerException e) {
				// cancle select picture and do nothing
			}

		}
	}

	public static boolean isInteger(String str) {
		try {
			@SuppressWarnings("unused")
			String bigStr = new Integer(str).toString();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
