package com.qwerty123.cookhelper.Controller.Searching;

import java.util.ArrayList;

/**
 * A IngredientQueryGroup represents a collection of ingredients that are required, optional
 * or to be excluded by the search performed on the recipes. These ingredients are represented
 * by their names as Strings, which come from the user.
 */
public class IngredientQueryGroup
{
    ArrayList<String> required;
    ArrayList<String> optional;
    ArrayList<String> exclude;

    public IngredientQueryGroup()
    {
        required = new ArrayList<>();
        optional = new ArrayList<>();
        exclude = new ArrayList<>();
    }

    /**
     * Adds an ingredient name as required.
     * @param ingredientName
     */
    public void addIngredientRequired(String ingredientName)
    {
        required.add(ingredientName);
    }

    /**
     * Adds an ingredient name as optional.
     *  @param ingredientName
     */
    public void addIngredientOptional(String ingredientName)
    {
        optional.add(ingredientName);
    }

    /**
     * Adds an ingredient name to be excluded.
     * @param ingredientName
     */
    public void addIngredientExclude(String ingredientName)
    {
        exclude.add(ingredientName);
    }

    /**
     * Returns whether or not the underlying lists are empty.
     * @return a boolean value representing whether or not the underlying lists are empty.
     */
    public boolean isEmpty()
    {
        return required.isEmpty() && optional.isEmpty() && exclude.isEmpty();
    }
}
