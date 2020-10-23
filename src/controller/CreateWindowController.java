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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import model.Ingredient;
import model.Recipe;
import windowviews.WindowViews;

/**
 * WindowController class, use for control the switching of windows
 * 
 * @author szt
 *
 */
public class CreateWindowController implements Initializable {

	private WindowViews windowsView;
	private DatabaseController databaseController;
	private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();
	private final ArrayList<String> categires = new ArrayList<>(
			FXCollections.observableArrayList("stired", "boiled", "fried", "stewed", "baked"));


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
	private ChoiceBox<String> categoryChoiceBox = new ChoiceBox<>();
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
	

	public CreateWindowController() {
		databaseController = new DatabaseController();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// createWindow initialize
		categoryChoiceBox.getItems().addAll(categires);
		categoryChoiceBox.setValue("fried");
		name.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("nameIng"));
		description.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("descriptionIng"));
		amount.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("totalAmountIng"));
		unit.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("unitIng"));
		ingredientsTable.setItems(ingredients);
		this.addDelButtonToTable();
	}

	private void addDelButtonToTable() {
		TableColumn<Ingredient, String> colBtn = new TableColumn<>();

		Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>> cellFactory = new Callback<TableColumn<Ingredient, String>, TableCell<Ingredient, String>>() {
			@Override
			public TableCell<Ingredient, String> call(final TableColumn<Ingredient, String> param) {
				final TableCell<Ingredient, String> cell = new TableCell<Ingredient, String>() {
					private final Button btn = new Button("del");
					{
						btn.setOnAction((ActionEvent event) -> {
							Ingredient data = getTableView().getItems().get(getIndex());
							ingredients.remove(data);
							ingredientsTable.refresh();

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
		ingredientsTable.getColumns().add(colBtn);
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
			String stored_category = categoryChoiceBox.getValue();
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
			new Integer(str).toString();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public void setView(WindowViews windowViews) {
		this.windowsView = windowViews;
	}
}
