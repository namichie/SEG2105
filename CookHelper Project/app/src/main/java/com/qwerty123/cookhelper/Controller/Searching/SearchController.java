package com.qwerty123.cookhelper.Controller.Searching;

import com.qwerty123.cookhelper.Model.RecipeBook.CulturalCategory;
import com.qwerty123.cookhelper.Model.RecipeBook.Ingredient;
import com.qwerty123.cookhelper.Model.RecipeBook.MealType;
import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;
import com.qwerty123.cookhelper.Model.RecipeBook.RecipeBook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;

public class SearchController
{
    static private ArrayList<Recipe> lastResult = null;

    /**
     * @param query the query which specifies how the list of recipes should be filtered
     * @return a list of recipes that matched the criteria. If there were optional ingredients
     * in the query, the recipes are sorted in terms of relevance. Also stores the results locally
     * to be retrievable by the associated view.
     */
    public static ArrayList<Recipe> performSearch(Query query)
    {
        lastResult = search(query);
        return lastResult;
    }

    private static ArrayList<Recipe> search(Query query)
    {
        ArrayList<Recipe> results = new ArrayList<>();

        RecipeBook recipeBook = RecipeBook.getInstance();

        if (query.hasNameCriteria())
        {
            Recipe recipe = recipeBook.getRecipe(query.getName());
            results.add(recipe);
            return results;
        }

        HashSet<Recipe> partialResults = new HashSet<>();

        if (query.hasCategoryCriteria())
        {
            if (recipeBook.hasCategory(query.getCategoryName()))
            {
                CulturalCategory category = recipeBook.getCategory(query.getCategoryName());

                if (partialResults.isEmpty())
                {
                    partialResults.addAll(category.recipes);
                }
                else
                {
                    partialResults.retainAll(category.recipes);
                }
            }
        }

        if (query.hasTypeCriteria())
        {
            if (recipeBook.hasType(query.getTypeName()))
            {
                MealType type = recipeBook.getMealType(query.getTypeName());

                if (partialResults.isEmpty())
                {
                    partialResults.addAll(type.recipes);
                }
                else
                {
                    partialResults.retainAll(type.recipes);
                }
            }
        }

        if (query.hasPrepTimeCriteria())
        {
            HashSet<Recipe> prepTimeRecipes = recipeBook.getRecipesPreparedBelow(query.getPrepTime());

            if (partialResults.isEmpty())
            {
                partialResults.addAll(prepTimeRecipes);
            }
            else
            {
                partialResults.retainAll(prepTimeRecipes);
            }
        }

        if (query.hasIngredientCriteria())
        {
            //Handle required ingredients first
            if (query.hasRequiredIngredients())
            {
                ArrayList<String> ingredientNames = query.getRequiredIngredients();

                for (String ingredientName : ingredientNames)
                {
                    if (recipeBook.hasIngredient(ingredientName))
                    {
                        if (partialResults.isEmpty())
                        {
                            partialResults.addAll(recipeBook.getIngredient(ingredientName).recipes);
                        }
                        else
                        {
                            partialResults.retainAll(recipeBook.getIngredient(ingredientName).recipes);
                        }
                    }
                }
            }

            //Exclusion of ingredients
            if (query.hasExcludedIngredients())
            {
                ArrayList<String> ingredientNames = query.getExcludedIngredients();

                for (String ingredientName : ingredientNames)
                {
                    if (recipeBook.hasIngredient(ingredientName))
                    {
                        if (!partialResults.isEmpty())
                        {
                            partialResults.removeAll(recipeBook.getIngredient(ingredientName).recipes);
                        }
                    }
                }
            }

            // At this point, we have grabbed all recipes with required ingredients and excluded as necessary.
            // Now we sort according to relevance, placing those with additional ingredients higher.

            if (query.hasOptionalIngredients())
            {
                PriorityQueue<PriorityRecipePair> recipeQueue = new PriorityQueue<>();

                ArrayList<String> ingredientNames = query.getOptionalIngredients();

                ArrayList<Ingredient> optionalIngredients = new ArrayList<>();

                for (String ingredientName : ingredientNames)
                {
                    if (recipeBook.hasIngredient(ingredientName))
                    {
                        optionalIngredients.add(recipeBook.getIngredient(ingredientName));
                    }
                }

                for (Recipe recipe : partialResults)
                {
                    int priority = 0;

                    Ingredient[] ingredients = recipe.getIngredients();

                    for (Ingredient ingredient : ingredients)
                    {
                        if (optionalIngredients.contains(ingredient))
                        {
                            ++priority;
                        }
                    }

                    recipeQueue.add(new PriorityRecipePair(priority, recipe));
                }

                while(!recipeQueue.isEmpty())
                {
                    results.add(0, recipeQueue.remove().second);
                }

                return results;
            }
        }

        if(!partialResults.isEmpty())
        {
            for(Recipe recipe : partialResults)
            {
                results.add(recipe);
            }
        }

        return results;
    }

    /**
     * @return the results of the last search.
     */
    public static ArrayList<Recipe> getLastResults()
    {
        return lastResult;
    }
}
