package com.qwerty123.cookhelper.Model;

import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import java.util.HashSet;

/**
 * Represents a class which has a table of recipes related to it.
 */
public class RecipeTable
{
    public HashSet<Recipe> recipes;

    public RecipeTable()
    {
        recipes = new HashSet<>();
    }

    public void addRecipe(Recipe recipe)
    {
        recipes.add(recipe);
    }

    public void removeRecipe(Recipe recipe)
    {
        recipes.remove(recipe);
    }
}
