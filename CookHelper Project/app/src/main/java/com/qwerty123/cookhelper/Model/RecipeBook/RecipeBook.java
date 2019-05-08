package com.qwerty123.cookhelper.Model.RecipeBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Singleton class. A recipe book, along with some utility member variables. Represents a cook book with a list of
 * recipes, and some tables to look up recipes based on preparation time, category, type and
 * ingredients.
 */
public class RecipeBook
{
    private static RecipeBook recipeBook;

    public Map<String, CulturalCategory> categories;
    public Map<String, MealType> types;
    public Map<String, Ingredient> ingredients;

    private ArrayList<Recipe> recipes;

    /**
     * @return the only instance of RecipeBook.
     */
    public static RecipeBook getInstance()
    {
        if (recipeBook == null)
        {
            recipeBook = new RecipeBook();
        }

        return recipeBook;
    }

    private RecipeBook()
    {
        recipes = new ArrayList<Recipe>(50);

        categories = new HashMap<>();
        types = new HashMap<>();
        ingredients = new HashMap<>();
    }

    /**
     * @return the list of recipes.
     */
    public Recipe[] getRecipeArray()
    {
        Recipe[] recipesArray = new Recipe[recipes.size()];
        return recipes.toArray(recipesArray);
    }

    /**
     * @param index position of the recipe
     * @return the recipe
     */
    public Recipe getRecipe(int index)
    {
        if (index < recipes.size())
        {
            return recipes.get(index);
        }
        else
        {
            return null;
        }
    }

    /**
     * @param name the name of the recipe.
     * @return the recipe.
     */
    public Recipe getRecipe(String name)
    {
        for (int i = 0; i < recipes.size(); i++)
        {
            Recipe recipe = recipes.get(i);
            String recipeName = recipe.getName();
            boolean nameEquals = recipeName.equals(name);

            if (nameEquals)
            {
                return recipe;
            }
        }

        return null;
    }

    /**
     * Returns or creates and returns a category with the matching name.
     * @param categoryName the name of the category expected to be returned.
     * @return
     */
    public CulturalCategory getCategory(String categoryName)
    {
        categoryName = categoryName.toLowerCase();
        CulturalCategory category;

        if (categories.containsKey(categoryName))
        {
            category = categories.get(categoryName);
        }
        else
        {
            category = new CulturalCategory(categoryName);
            categories.put(categoryName, category);
        }

        return category;
    }

    /**
     * Returns or creates and returns a type with the matching name.
     * @param typeName the name of the type expected to be returned.
     * @return
     */
    public MealType getMealType(String typeName)
    {
        typeName = typeName.toLowerCase();
        MealType type;

        if (types.containsKey(typeName))
        {
            type = types.get(typeName);
        }
        else
        {
            type = new MealType(typeName);
            types.put(typeName, type);
        }

        return type;
    }

    /**
     * Returns or creates and returns a ingredient with the matching name.
     * @param ingredientName the name of the ingredient expected to be returned.
     * @return
     */
    public Ingredient getIngredient(String ingredientName)
    {
        ingredientName = ingredientName.toLowerCase();
        Ingredient ingredient;

        if (ingredients.containsKey(ingredientName))
        {
            ingredient = ingredients.get(ingredientName);
        }
        else
        {
            ingredient = new Ingredient(ingredientName);
            ingredients.put(ingredientName, ingredient);
        }

        return ingredient;
    }

    /**
     * @param recipe to be added to the lookup tables.
     */
    public void addToLookupTables(Recipe recipe)
    {
        CulturalCategory category = recipe.getCulturalCategory();
        MealType mealType = recipe.getMealType();
        Ingredient[] ingredients = recipe.getIngredients();

        category.addRecipe(recipe);
        mealType.addRecipe(recipe);

        for (Ingredient ingredient : ingredients)
        {
            ingredient.addRecipe(recipe);
        }
    }

    /**
     * @param recipe to be removed from the lookup tables.
     */
    private void removeFromLookupTables(Recipe recipe)
    {
        CulturalCategory category = recipe.getCulturalCategory();
        MealType mealType = recipe.getMealType();
        Ingredient[] ingredients = recipe.getIngredients();

        category.removeRecipe(recipe);
        mealType.removeRecipe(recipe);

        for (Ingredient ingredient : ingredients)
        {
            ingredient.removeRecipe(recipe);
        }
    }

    /**
     * @param recipe to be added to the RecipeBook.
     */
    public void addNewRecipe(Recipe recipe)
    {
        if (recipe != null)
        {
            recipes.add(recipe);
            addToLookupTables(recipe);
        }
    }

    /**
     * @param index position of the recipe to be deleted.
     */
    public void deleteRecipeAtPosition(int index)
    {
        if (index >= 0 && index < recipes.size())
        {
            Recipe oldRecipe = recipes.get(index);
            removeFromLookupTables(oldRecipe);
            recipes.remove(index);
        }
    }

    /**
     * @param index position of the recipe to be overwritten.
     * @param recipe the new recipe to be saved.
     */
    public void overwriteRecipeAtPosition(int index, Recipe recipe)
    {
        if (index >= 0 && index < recipes.size())
        {
            Recipe oldRecipe = recipes.get(index);
            removeFromLookupTables(oldRecipe);
            recipes.set(index, recipe);
            addToLookupTables(recipe);
        }
    }

    public void setRecipeList(Recipe[] recipeArray)
    {
        this.recipes = new ArrayList<Recipe>(Arrays.asList(recipeArray));
    }

    public boolean hasCategory(String category)
    {
        return categories.containsKey(category);
    }

    public boolean hasType(String type)
    {
        return types.containsKey(type);
    }

    public boolean hasIngredient(String ingredient)
    {
        return ingredients.containsKey(ingredient);
    }

    /**
     * @param prepTime the preparation time desired.
     * @return a set of recipes which have the preparation time specified or lower.
     */
    public HashSet<Recipe> getRecipesPreparedBelow(int prepTime)
    {
        HashSet<Recipe> recipes = new HashSet<>();

        for (Recipe recipe : this.recipes)
        {
            if (recipe.getPreparationTime() <= prepTime)
            {
                recipes.add(recipe);
            }
        }

        return recipes;
    }

    /**
     * @param recipeName
     * @return the index of the recipe.
     */
    public int getRecipeIndex(String recipeName)
    {
        for (int i = 0; i < recipes.size(); ++i)
        {
            if (recipes.get(i).getName().equals(recipeName))
            {
                return i;
            }
        }

        return -1;
    }
}