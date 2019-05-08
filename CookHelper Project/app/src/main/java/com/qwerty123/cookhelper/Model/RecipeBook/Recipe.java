package com.qwerty123.cookhelper.Model.RecipeBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qwerty123.cookhelper.Controller.RecipeBuilder;
import com.qwerty123.cookhelper.Utils.JSONSerialization.JSONSerializable;

/**
 * Represents a recipe, with a name, a cultural category, a meal type, a preparation time,
 * a list of ingredients, and a list of steps.
 */
public class Recipe implements JSONSerializable
{
    private RecipeBook recipeBook;
    private String name;
    private CulturalCategory culturalCategory;
    private MealType mealType;
    private int preparationTime;

    private Ingredient[] ingredients;
    private PreparationStep[] preparationSteps;


    public Recipe(RecipeBook recipeBook, String name, CulturalCategory culturalCategory, MealType mealType, int preparationTime,
                  Ingredient[] ingredients, PreparationStep[] preparationSteps)
    {
        this.recipeBook = recipeBook;
        setInfo(name, culturalCategory, mealType, preparationTime, ingredients, preparationSteps);
    }

    /**
     * Creates a recipe from a JSON object.
     * @param recipeBook the book to be added to, and from which to get categories and ingredients
     * @param jsonRecipe a JSON object representing the recipe.
     */
    public Recipe(RecipeBook recipeBook, JSONObject jsonRecipe)
    {
        this.recipeBook = recipeBook;
        initializeFromJSON(jsonRecipe);
    }

    public String getName()
    {
        return name;
    }

    public CulturalCategory getCulturalCategory()
    {
        return culturalCategory;
    }

    public MealType getMealType()
    {
        return mealType;
    }

    public int getPreparationTime()
    {
        return preparationTime;
    }

    /**
     * @return a neat string to enumerate the ingredients used.
     */
    public String getIngredientsEnumeration()
    {
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < ingredients.length; ++i)
        {
            buffer.append(ingredients[i].getDisplayName());

            if (i != ingredients.length - 1)
            {
                buffer.append(", ");
            }
        }

        return buffer.toString();
    }

    /**
     * @return a neat string to enumerate the steps.
     */
    public String getPreparationStepEnumeration()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < preparationSteps.length; ++i)
        {
            builder.append(preparationSteps[i].getDisplayString());

            if (i != preparationSteps.length - 1)
            {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    public int getNumberSteps()
    {
        return preparationSteps.length;
    }

    public PreparationStep[] getPreparationSteps()
    {
        return preparationSteps;
    }

    /**
     * @return serializes a recipe in a JSON object.
     */
    @Override
    public JSONObject toJSON()
    {
        JSONObject jsonObject = new JSONObject();
        JSONArray prepArray = new JSONArray();

        try
        {
            jsonObject.put("Name", name);
            jsonObject.put("CulturalCategory", culturalCategory.toString());
            jsonObject.put("MealType", mealType.toString());
            jsonObject.put("PreparationTime", preparationTime);

            for (PreparationStep prepStep : preparationSteps)
            {
                prepArray.put(prepStep.getSpecificationString());
            }

            jsonObject.put("PreparationSteps", prepArray);
        }
        catch (JSONException e)
        {
            System.err.println("Error while converting Recipe details to JsonObject");
            e.printStackTrace();
        }

        return jsonObject;
    }

    /**
     * @param jsonObject a JSON object from which to obtain member variable values.
     */
    @Override
    public void initializeFromJSON(JSONObject jsonObject)
    {
        if (jsonObject != null)
        {
            RecipeBuilder.buildFromJson(recipeBook, this, jsonObject);
        }
        else
        {
            throw new NullPointerException("JSON Object is null. Cannot Initialize");
        }
    }

    public boolean equals(Recipe other)
    {
        boolean equals = true;
        equals = equals && this.name == other.name;
        equals = equals && culturalCategory.equals(other.culturalCategory);
        equals = equals && mealType.equals(other.mealType);
        equals = equals && this.preparationTime == other.preparationTime;
        equals = equals && this.getIngredientsEnumeration().equals(other.getIngredientsEnumeration());
        equals = equals && this.getPreparationStepEnumeration().equals(other.getIngredientsEnumeration());

        return equals;
    }

    public Ingredient[] getIngredients()
    {
        return ingredients;
    }

    public void setInfo(String name, CulturalCategory culturalCategory, MealType mealType, int preparationTime, Ingredient[] ingredients, PreparationStep[] preparationSteps)
    {
        this.name = name;
        this.culturalCategory = culturalCategory;
        this.mealType = mealType;
        this.preparationTime = preparationTime;
        this.ingredients = ingredients;
        this.preparationSteps = preparationSteps;
    }
}
