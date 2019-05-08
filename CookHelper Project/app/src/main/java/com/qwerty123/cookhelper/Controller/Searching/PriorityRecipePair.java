package com.qwerty123.cookhelper.Controller.Searching;

import android.support.v4.util.Pair;

import com.qwerty123.cookhelper.Model.RecipeBook.Recipe;

/**
 * Represents a comparable pair of a recipe (the value) and it's priority (the key).
 * Intended to be used to sort the results of a search based on relevance.
 */
public class PriorityRecipePair extends Pair<Integer, Recipe> implements Comparable<Pair<Integer, Recipe>>
{

    /**
     * @param first the priority
     * @param second the recipe
     */
    public PriorityRecipePair(Integer first, Recipe second)
    {
        super(first, second);
    }

    /**
     * Compares two PriorityRecipePairs
     * @param other the PriorityRecipePair to be compared to
     * @return return and integer representing if this instance is bigger than, equal to,
     * or smaller than the other. Follows the convention set by the compareTo of built-in types.
     */
    @Override
    public int compareTo(Pair<Integer, Recipe>other )
    {
        return first.compareTo(other.first);
    }
}
