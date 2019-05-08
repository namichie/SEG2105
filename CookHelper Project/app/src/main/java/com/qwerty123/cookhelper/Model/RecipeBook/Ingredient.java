package com.qwerty123.cookhelper.Model.RecipeBook;

import com.qwerty123.cookhelper.Model.RecipeTable;
import com.qwerty123.cookhelper.Utils.Utils;

/**
 * Represents a given ingredient. Can be food or spices.
 */
public class Ingredient extends RecipeTable
{
    private String name;
    private String displayName;

    /**
     * @param name containing no whitespaces. Use underscores to speparate words. Creates a string
     *             for display purposes.
     */
    public Ingredient(String name)
    {
        if (name.length() == 0)
        {
            throw new IllegalArgumentException("Empty string passed as ingredient name.");
        }

        this.name = name;
        displayName = Utils.createDisplayName(name);
    }

    /**
     * @return the name with underscores.
     */
    public String getName()
    {
        return name;
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
