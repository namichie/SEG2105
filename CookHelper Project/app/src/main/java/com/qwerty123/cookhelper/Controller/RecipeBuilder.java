package com.qwerty123.cookhelper.Controller;

import com.qwerty123.cookhelper.Model.RecipeBook.CulturalCategory;
import com.qwerty123.cookhelper.Model.RecipeBook.Ingredient;
import com.qwerty123.cookhelper.Model.RecipeBook.MealType;
import com.qwerty123.cookhelper.Model.RecipeBook.PreparationStep;
import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.Model.RecipeBook.RecipeBook;
import com.qwerty123.cookhelper.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * In charge of creating recipes, either with input from the user, or from JSON objects created
 * by reading the JSON file.
 */
public class RecipeBuilder
{
    private static final char INGREDIENT_TOKEN = '#';

    /**
     * Build a recipe using the provided inputs and puts it in the RecipeBook
     * @param recipeBook
     * @param name
     * @param category
     * @param type
     * @param prepTime
     * @param steps
     * @return the new recipe
     */
    public static Recipe buildRecipe(RecipeBook recipeBook, String name, String category, String type, int prepTime, String[] steps)
    {
        CulturalCategory culturalCategory = recipeBook.getCategory(category);
        MealType mealType = recipeBook.getMealType(type);

        PreparationStep[] preparationSteps = new PreparationStep[steps.length];
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        for (int i = 0; i < steps.length; ++i)
        {
            preparationSteps[i] = parseSpecificationString(recipeBook, steps[i], ingredients);
        }

        Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
        ingredients.toArray(ingredientsArray);

        Recipe recipe = new Recipe(recipeBook, name, culturalCategory, mealType, prepTime, ingredientsArray, preparationSteps);

        //recipeBook.addToLookupTables(recipe);

        return recipe;
    }

    /**
     * Parses the string that represents the steps of a recipe. In doing so, obtains a reference
     * to all relevant ingredients used in an individual step, for each step.
     * @param recipeBook
     * @param specificationString
     * @param ingredientsOut
     * @return
     */
    private static PreparationStep parseSpecificationString(RecipeBook recipeBook, String specificationString, ArrayList<Ingredient> ingredientsOut)
    {
        ArrayList<String> ingredientNames = new ArrayList<String>();

        StringBuilder displayString = new StringBuilder();

        if (specificationString != null && !specificationString.isEmpty())
        {
            Scanner scanner = new Scanner(specificationString);

            while (scanner.hasNext())
            {
                String token = scanner.next();

                if (token.length() > 1 && token.charAt(0) == INGREDIENT_TOKEN)
                {
                    token = token.substring(1);
                    ingredientNames.add(token.toLowerCase());
                }

                displayString.append(" ");
                displayString.append(Utils.createDisplayName(token));
            }
        }

        Ingredient[] ingredientsArray = new Ingredient[ingredientNames.size()];

        for (int i = 0; i < ingredientNames.size(); ++i)
        {
            Ingredient ingredient = recipeBook.getIngredient(ingredientNames.get(i));
            ingredientsArray[i] = ingredient;
            ingredientsOut.add(ingredient);
        }

        return new PreparationStep(specificationString, displayString.toString(), ingredientsArray);
    }

    /**
     * Build a recipe from JSON representation and uses categories, types, and ingredients from the
     * RecipeBook.
     * @param recipeBook
     * @param recipe
     * @param jsonObject
     */
    public static void buildFromJson(RecipeBook recipeBook, Recipe recipe, JSONObject jsonObject)
    {
        if(recipeBook != null && recipe != null && jsonObject != null)
        {
            try
            {
                String name = jsonObject.getString("Name");
                CulturalCategory culturalCategory = recipeBook.getCategory(jsonObject.getString("CulturalCategory"));
                MealType mealType = recipeBook.getMealType(jsonObject.getString("MealType"));
                int preparationTime = jsonObject.getInt("PreparationTime");

                ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

                JSONArray prepSteps = jsonObject.getJSONArray("PreparationSteps");

                PreparationStep[] preparationSteps = new PreparationStep[prepSteps.length()];
                for (int i = 0; i < prepSteps.length(); i++)
                {
                    preparationSteps[i] = parseSpecificationString(recipeBook, (String)prepSteps.get(i), ingredients);
                }

                Ingredient[] ingredientsArray = new Ingredient[ingredients.size()];
                ingredients.toArray(ingredientsArray);

                recipe.setInfo(name, culturalCategory, mealType, preparationTime, ingredientsArray, preparationSteps);
            }
            catch (JSONException e)
            {
                recipe.setInfo("ERROR PARSING JSON", new CulturalCategory("ERROR"), new MealType("ERROR"), 0, new Ingredient[0], new PreparationStep[0]);
                e.printStackTrace();
            }
        }
    }
}
