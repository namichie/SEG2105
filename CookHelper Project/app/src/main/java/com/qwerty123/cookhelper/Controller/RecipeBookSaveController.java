package com.qwerty123.cookhelper.Controller;

import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.Model.RecipeBook.RecipeBook;
import com.qwerty123.cookhelper.R;
import com.qwerty123.cookhelper.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import com.qwerty123.cookhelper.Utils.JSONSerialization.JSONSerializer;

/**
 * In charge of interacting with the JSON serialization classes. It takes care of reading and
 * writing to file.
 */
public class RecipeBookSaveController
{
    /**
     * Gets the list of recipes and all their info from file, and puts them in the RecipeBook.
     * Has the recipe book update the lookup tables.
     * @param recipeBook
     */
   public static void loadRecipesFromFile(RecipeBook recipeBook)
    {
        loadAllRecipesToRecipeBook(recipeBook);

        for (Recipe recipe : recipeBook.getRecipeArray())
        {
            recipeBook.addToLookupTables(recipe);
        }
    }

    /**
     * Creates the list of recipes to be put in the RecipeBook.
     *  @param recipeBook
     */
    public static void loadAllRecipesToRecipeBook(RecipeBook recipeBook)
    {
        JSONArray recipeList = JSONSerializer.readArray(Utils.getApplicationContext(), getJsonRecipeListFilename());

        Recipe[] recipes = null;

        if (recipeList != null)
        {
            recipes = new Recipe[recipeList.length()];

            for (int i = 0; i < recipeList.length(); ++i)
            {
                try
                {
                    recipes[i] = new Recipe(recipeBook, recipeList.getJSONObject(i));
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            recipes = new Recipe[0];
        }

        recipeBook.setRecipeList(recipes);
    }

    /**
     * @return the filename for the recipe JSON file. Obtained from the Android resources.
     */
    private static String getJsonRecipeListFilename()
    {
        return Utils.getApplicationContext().getResources().getString(R.string.recipe_book_filename);
    }

    /**
     * Overwrites the file with the JSON representation of the current RecipeBook's recipe list.
     * @param recipeBook
     */
    public static void saveRecipes(RecipeBook recipeBook)
    {
        Recipe[] recipes = recipeBook.getRecipeArray();

        JSONArray jsonArray = new JSONArray();

        for(Recipe recipe : recipes)
        {
            jsonArray.put(recipe.toJSON());
        }

        JSONSerializer.writeArray(Utils.getApplicationContext(), jsonArray, getJsonRecipeListFilename());
    }
}
