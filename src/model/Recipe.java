package model;

import java.sql.Blob;
import java.util.ArrayList;

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

}
