package entity;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Recipe {
	String name;
	String prepTime;
	String cookTime;
	Blob pic;
	String instruction;
	String category;
	ArrayList<Ingredient> ingredients;

	public Recipe(String name, String prepTime, String cookTime, Blob pic, String instruction, String category) {
		this.name = name;
		this.prepTime = prepTime;
		this.cookTime = cookTime;
		this.pic = pic;
		this.instruction = instruction;
		this.category = category;

	}

	public String getName() {
		return name;
	}

	public String getPrepTime() {
		return prepTime;
	}

	public String getCookTime() {
		return cookTime;
	}

	public Blob getPic() {
		return pic;
	}

	public String getInstruction() {
		return instruction;
	}

	public String getCategory() {
		return category;
	}

	public ArrayList<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	
	//this method is for display pictures in catagoryWindow
	public ImageView getRecipeImage() throws SQLException {
		ImageView recipeImage = new ImageView(new Image(pic.getBinaryStream()));
		recipeImage.setFitHeight(200);
		recipeImage.setFitWidth(300);
		return recipeImage;
	}

}
