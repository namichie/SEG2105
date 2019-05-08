package com.qwerty123.cookhelper.Controller;

import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.Model.RecipeBook.RecipeBook;

/**
 * A class designed to facilitate interactions with the model.
 */
public class RecipeBookController
{
    private static RecipeBook recipeBook = null;

    /**
     * @return the single instance of RecipeBook. If it hasn't been instantiated yet, it does so
     * and populates it with data from the JSON file.
     */
    public static RecipeBook getRecipeBook()
    {
        if(recipeBook == null)
        {
            recipeBook = RecipeBook.getInstance();

            RecipeBookSaveController.loadRecipesFromFile(recipeBook);
        }

        return recipeBook;
    }

    /**
     * @param index the index in the RecipeBook where the desired recipe is stored.
     * @return the recipe at that index
     */
    public static Recipe getRecipe(int index)
    {
        return recipeBook.getRecipe(index);
    }

    /**
     * @param position the index of the recipe to be deleted.
     */
    public static void deleteRecipe(int position)
    {
        recipeBook.deleteRecipeAtPosition(position);
        RecipeBookSaveController.saveRecipes(recipeBook);
    }

    /**
     * Creates a recipe using the RecipeBuilder, then adds it to the RecipeBook and saves the
     * recipes to file.
     * @param name
     * @param category
     * @param type
     * @param prepTime
     * @param steps
     */
    public static void addNewRecipe(String name, String category, String type, int prepTime, String[] steps)
    {
        Recipe recipe = RecipeBuilder.buildRecipe(recipeBook, name, category, type, prepTime, steps);
        recipeBook.addNewRecipe(recipe);
        RecipeBookSaveController.saveRecipes(recipeBook);
    }

    /**
     * Creates a recipe using the RecipeBuilder, then overwrites and existing recipe
     * and saves the recipes to file.
     * @param index
     * @param name
     * @param category
     * @param type
     * @param prepTime
     * @param steps
     */
    public static void overwriteRecipe(int index, String name, String category, String type, int prepTime, String[] steps)
    {
        Recipe recipe = RecipeBuilder.buildRecipe(recipeBook, name, category, type, prepTime, steps);
        recipeBook.overwriteRecipeAtPosition(index, recipe);
        RecipeBookSaveController.saveRecipes(recipeBook);
    }

    /**
     * finds the index of a recipe in the recipe book by using it's name.
     * @param recipeName the name of the recipe whose index is to be retrieved
     * @return the index
     */
    public static int getRecipeIndex(String recipeName)
    {
        return recipeBook.getRecipeIndex(recipeName);
    }
}
