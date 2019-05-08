package com.qwerty123.cookhelper.Model.RecipeBook;

import com.qwerty123.cookhelper.Model.RecipeTable;
import com.qwerty123.cookhelper.Utils.Utils;

/**
 * Represents a given culture. Food is often characterized by the culture it originates from.
 */
public class CulturalCategory extends RecipeTable
{
    private String name;
    private String displayName;

    /**
     * Receives the name of the category, no whitespaces, use underscores. Will create a string
     * without underscores for display purposes.
     * @param name the name of the cultural category (with no whitespaces). EX: chinese, north_american.
     */
    public CulturalCategory(String name)
    {
        if (name.length() == 0)
        {
            throw new IllegalArgumentException("Empty string passed as category name.");
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
     * @return a name for display purposes.
     */
    public String getDisplayName()
    {
        return displayName;
    }
}
