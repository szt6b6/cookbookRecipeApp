package controller;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Ingredient;
import model.Recipe;

/**
 * DatabaseController class, its job is to deliver data. Window controllers use
 * database controller to get data from database or change data in the database
 * 
 * @author szt
 *
 */
public class DatabaseController {

	private Connection connection;

	/**
	 * constructor, initialize the connection to the database
	 */
	public DatabaseController() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/cookbookrecipes?serverTimezone=UTC&characterEncoding=UTF-8", "root",
					"123456");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get connection method, used to create Blob type to store image object
	 * 
	 * @return connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * create recipe method, create recipe and its ingredients in database
	 * 
	 * @param recipe recipe's entity
	 * @return true or false of creating result
	 */
	public boolean createRecipe(Recipe recipe) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(
					"insert into recipes (recipeName, prepTime, cookTime, picture, instruction, category) values (?, ?, ?, ?, ?, ?)");
			statement.setString(1, recipe.getName());
			statement.setString(2, recipe.getPrepTime());
			statement.setString(3, recipe.getCookTime());
			statement.setBlob(4, recipe.getPic());
			statement.setString(5, recipe.getInstruction());
			statement.setString(6, recipe.getCategory());

			for (Ingredient ingredient : recipe.getIngredients()) {
				String sql_createIngredients = "insert into ingredients (recipeName, ingredientName, description, amount, unit) values (\""
						+ recipe.getName() + "\", \"" + ingredient.getNameIng() + "\", \""
						+ ingredient.getDescriptionIng() + "\", \"" + ingredient.getAmountIng() + "\", \""
						+ ingredient.getUnitIng() + "\");";
				statement.execute(sql_createIngredients);
			}
			statement.execute();

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * update recipe method, to update recipe in the database according to received
	 * parameters
	 * 
	 * @param recipe                              recipe data used to update recipe
	 * @param recipeNameToBeUpdate                indicate which recipe is going to
	 *                                            be updated
	 * @param neededToDelIngredientInDetailWindow deleted ingredients in table
	 * @param neededToAddIngredientInDetailWindow added ingredients in table
	 * @return true or false of updating result
	 */
	public boolean updateRecipe(Recipe recipe, String recipeNameToBeUpdate,
			ArrayList<Ingredient> neededToDelIngredientInDetailWindow,
			ArrayList<Ingredient> neededToAddIngredientInDetailWindow) {
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(
					"update recipes set recipeName = ?, prepTime = ?, cookTime = ?, picture =?, instruction = ?, category = ? where recipeName = ?");
			statement.setString(1, recipe.getName());
			statement.setString(2, recipe.getPrepTime());
			statement.setString(3, recipe.getCookTime());
			statement.setBlob(4, recipe.getPic());
			statement.setString(5, recipe.getInstruction());
			statement.setString(6, recipe.getCategory());
			statement.setString(7, recipeNameToBeUpdate);

			// delete ingredients first, if user deletes the original ingredient in
			// detailWindow
			for (Ingredient ingredient : neededToDelIngredientInDetailWindow) {
				String sql_deleteIngredientsFirst = "delete from ingredients where ingredientName = \""
						+ ingredient.getNameIng() + "\" and description = \"" + ingredient.getDescriptionIng()
						+ "\" and amount = \"" + ingredient.getAmountIng() + "\" and unit = \""
						+ ingredient.getUnitIng() + "\";";
				statement.execute(sql_deleteIngredientsFirst);
			}

			for (Ingredient ingredient : neededToAddIngredientInDetailWindow) {
				String sql_createIngredients = "insert into ingredients (recipeName, ingredientName, description, amount, unit) values (\""
						+ recipe.getName() + "\", \"" + ingredient.getNameIng() + "\", \""
						+ ingredient.getDescriptionIng() + "\", \"" + ingredient.getAmountIng() + "\", \""
						+ ingredient.getUnitIng() + "\");";
				statement.execute(sql_createIngredients);
			}
			statement.execute();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * delete recipe method according to recipeName parameter
	 * 
	 * @param recipeName indicates which recipe is needed to be deleted
	 * @return true or false of the deleting result
	 */
	public boolean deleteRecipe(String recipeName) {
		Statement statement;
		try {
			statement = connection.createStatement();
			String sql_deleteRecipe = "delete from recipes where recipeName = \"" + recipeName + "\";";
			String sql_deleteIngredients = "delete from ingredients where recipeName = \"" + recipeName + "\";";
			statement.execute(sql_deleteRecipe);
			statement.execute(sql_deleteIngredients);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * search recipe method according to given parameter recipeName
	 * 
	 * @param recipeName the string value of recipe name used to search recipe in
	 *                   database
	 * @return searched recipe from database
	 */
	public Recipe searchRecipe(String recipeName) {
		Statement statement;
		try {
			statement = connection.createStatement();
			String sql_searchRecipe = "select recipeName, prepTime, cookTime, picture, instruction, category from recipes where recipeName = \""
					+ recipeName + "\"" + ";";

			ResultSet recipeResult = statement.executeQuery(sql_searchRecipe);
			while (recipeResult.next()) {
				String prepTime = recipeResult.getString("prepTime");
				String cookTime = recipeResult.getString("cookTime");
//				Image picture = new Image(recipeResult.getBlob("picture").getBinaryStream()); //将Blob图片转化为javafx Image
				Blob picture = recipeResult.getBlob("picture");
				String instruction = recipeResult.getString("instruction");
				String category = recipeResult.getString("category");
				Recipe searchedRecipe = new Recipe(recipeName, prepTime, cookTime, picture, instruction, category);

				String sql_searchIngredients = "SELECT ingredientName, description, amount, unit from ingredients where recipeName = \""
						+ recipeName + "\";";
				ResultSet ingredientsResult = statement.executeQuery(sql_searchIngredients);
				ArrayList<Ingredient> ingredients = new ArrayList<>();
				while (ingredientsResult.next()) {
					String ingredientName = ingredientsResult.getString("ingredientName");
					String description = ingredientsResult.getString("description");
					String amount = ingredientsResult.getString("amount");
					String unit = ingredientsResult.getString("unit");
					Ingredient ingredient = new Ingredient(ingredientName, description, amount, unit, 1);
					ingredients.add(ingredient);
				}
				searchedRecipe.setIngredients(ingredients);
				statement.close();
				return searchedRecipe;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * search recipe category method, used to get a list of recipes belong to given
	 * category searchedCategory
	 * 
	 * @param searchedCategory used to search recipes belong to this category
	 * @return return the ArrayList result of recipes of given category
	 */
	public ArrayList<Recipe> searchRecipeList(String searchedCategory) {
		Statement statement;
		ArrayList<Recipe> stored_recipes = new ArrayList<>();
		try {
			statement = connection.createStatement();
			String sql_searchRecipe = "select recipeName, prepTime, cookTime, picture, instruction, category from recipes where category = \""
					+ searchedCategory + "\"" + ";";
			ResultSet recipeResult = statement.executeQuery(sql_searchRecipe);
			while (recipeResult.next()) {
				String recipeName = recipeResult.getString("recipeName");
				String prepTime = recipeResult.getString("prepTime");
				String cookTime = recipeResult.getString("cookTime");
				Blob picture = recipeResult.getBlob("picture");
				String instruction = recipeResult.getString("instruction");
				String category = recipeResult.getString("category");
				Recipe stored_recipe = new Recipe(recipeName, prepTime, cookTime, picture, instruction, category);
				stored_recipes.add(stored_recipe);
			}

			for (Recipe recipe : stored_recipes) {
				String recipeName = recipe.getName();
				String sql_searchIngredients = "SELECT ingredientName, description, amount, unit from ingredients where recipeName = \""
						+ recipeName + "\";";
				ResultSet ingredientsResult = statement.executeQuery(sql_searchIngredients);
				ArrayList<Ingredient> ingredients = new ArrayList<>();
				while (ingredientsResult.next()) {
					String ingredientName = ingredientsResult.getString("ingredientName");
					String description = ingredientsResult.getString("description");
					String amount = ingredientsResult.getString("amount");
					String unit = ingredientsResult.getString("unit");
					Ingredient ingredient = new Ingredient(ingredientName, description, amount, unit, 1);
					ingredients.add(ingredient);
				}
				recipe.setIngredients(ingredients);
			}

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stored_recipes;
	}
}
