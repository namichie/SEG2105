package com.qwerty123.cookhelper.Model.RecipeBook;

import com.qwerty123.cookhelper.Model.RecipeTable;
import com.qwerty123.cookhelper.Utils.Utils;

/**
 * Represents a meal of the day, whenever the recipe is appropriate to use.
 */
public class MealType extends RecipeTable
{
    private String name;
    private String displayName;

    /**
     * @param name without whitespaces. Use underscores. Creates a string for display purposes.
     */
    public MealType(String name)
    {
        if (name.length() == 0)
        {
            throw new IllegalArgumentException("Empty string passed as type name.");
        }

        this.name = name;
        displayName = Utils.createDisplayName(name);
    }

    @Override
    public String toString()
    {
        return name;
    }

    /**
     * @return a string for display.
     */
    public String getDisplayName()
    {
        return displayName;
    }
}
